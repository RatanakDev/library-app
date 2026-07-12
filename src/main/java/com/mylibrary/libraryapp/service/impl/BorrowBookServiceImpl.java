package com.mylibrary.libraryapp.service.impl;

import com.mylibrary.libraryapp.dto.requests.BorrowsBookRequest;
import com.mylibrary.libraryapp.dto.requests.BorrowsBookStatusRequest;
import com.mylibrary.libraryapp.dto.response.BorrowsBooksResponse;
import com.mylibrary.libraryapp.entities.BookEntity;
import com.mylibrary.libraryapp.entities.BorrowsBookEntity;
import com.mylibrary.libraryapp.entities.BorrowsEntity;
import com.mylibrary.libraryapp.entities.Enum.BookStatusEnum;
import com.mylibrary.libraryapp.entities.Enum.RequestStatusEnum;
import com.mylibrary.libraryapp.entities.UserEntity;
import com.mylibrary.libraryapp.exceptions.ApiException;
import com.mylibrary.libraryapp.mappers.BorrowBookMapper;
import com.mylibrary.libraryapp.repositories.BookRepository;
import com.mylibrary.libraryapp.repositories.BorrowsBookRepository;
import com.mylibrary.libraryapp.repositories.BorrowsRepository;
import com.mylibrary.libraryapp.repositories.UserRepository;
import com.mylibrary.libraryapp.service.BorrowBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BorrowBookServiceImpl implements BorrowBookService {

    private final BorrowsRepository borrowsRepository;
    private final BorrowsBookRepository borrowsBookRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BorrowBookMapper borrowBookMapper;

    @Override
    @Transactional
    public BorrowsBooksResponse createBorrow(BorrowsBookRequest request) {
        UserEntity user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ApiException(404, "Fail!", "User not found: " + request.getUserId()));

        List<Long> bookIds = request.getBookIds().stream().distinct().toList();

        List<BookEntity> books = bookRepository.findAllById(bookIds);
        if (books.size() != bookIds.size()) {
            throw new ApiException(404, "Fail!", "One or more books were not found");
        }

        for (BookEntity book : books) {
            if (book.getAvailableQty() == null || book.getAvailableQty() <= 0) {
                throw new ApiException(400, "Fail!", "Book is not available: " + book.getTitle());
            }
        }

        BorrowsEntity borrow = BorrowsEntity.builder()
                .user(user)
                .borrowDate(request.getBorrowDate() != null ? request.getBorrowDate() : new Date())
                .returnDate(request.getReturnDate())
                .requestStatus(RequestStatusEnum.PENDING)
                .build();

        List<BorrowsBookEntity> borrowsBooks = new ArrayList<>();
        for (BookEntity book : books) {
            book.setAvailableQty(book.getAvailableQty() - 1);
            borrowsBooks.add(BorrowsBookEntity.builder()
                    .borrows(borrow)
                    .book(book)
                    .status(BookStatusEnum.BORROWED)
                    .build());
        }
        borrow.setBorrowsBooks(borrowsBooks);

        bookRepository.saveAll(books);
        BorrowsEntity saved = borrowsRepository.save(borrow);
        return borrowBookMapper.toResponse(saved);
    }

    @Override
    public List<BorrowsBooksResponse> findAllBorrows() {
        return borrowBookMapper.toResponseList(borrowsRepository.findAll());
    }

    @Override
    public BorrowsBooksResponse findBorrowById(Long id) {
        return borrowBookMapper.toResponse(getBorrowOrThrow(id));
    }

    @Override
    public List<BorrowsBooksResponse> findBorrowsByUser(String userId) {
        if (!userRepository.existsById(userId)) {
            throw new ApiException(404, "Fail!", "User not found: " + userId);
        }
        return borrowBookMapper.toResponseList(borrowsRepository.findByUser_Id(userId));
    }

    @Override
    public List<BorrowsBooksResponse> findBorrowsByStatus(RequestStatusEnum status) {
        return borrowBookMapper.toResponseList(borrowsRepository.findByRequestStatus(status));
    }

    @Override
    @Transactional
    public BorrowsBooksResponse approveBorrow(Long id) {
        BorrowsEntity borrow = getBorrowOrThrow(id);

        if (borrow.getRequestStatus() != RequestStatusEnum.PENDING) {
            throw new ApiException(400, "Fail!", "Only pending borrow requests can be approved");
        }

        borrow.setRequestStatus(RequestStatusEnum.APPROVED);
        return borrowBookMapper.toResponse(borrowsRepository.save(borrow));
    }

    @Override
    @Transactional
    public BorrowsBooksResponse rejectBorrow(Long id) {
        BorrowsEntity borrow = getBorrowOrThrow(id);

        if (borrow.getRequestStatus() != RequestStatusEnum.PENDING) {
            throw new ApiException(400, "Fail!", "Only pending borrow requests can be rejected");
        }

        restockBooks(borrow.getBorrowsBooks());
        borrow.setRequestStatus(RequestStatusEnum.REJECTED);
        return borrowBookMapper.toResponse(borrowsRepository.save(borrow));
    }

    @Override
    @Transactional
    public BorrowsBooksResponse updateBookStatus(Long borrowId, Long bookId, BorrowsBookStatusRequest request) {
        if (request.getStatus() != BookStatusEnum.RETURNED && request.getStatus() != BookStatusEnum.LOST) {
            throw new ApiException(400, "Fail!", "Status must be RETURNED or LOST");
        }

        if (request.getStatus() == BookStatusEnum.LOST
                && (request.getReason() == null || request.getReason().isBlank())) {
            throw new ApiException(400, "Fail!", "Reason is required when a book is marked as lost");
        }

        BorrowsEntity borrow = getBorrowOrThrow(borrowId);

        if (borrow.getRequestStatus() != RequestStatusEnum.APPROVED) {
            throw new ApiException(400, "Fail!", "Only approved borrows can be returned");
        }

        BorrowsBookEntity borrowsBook = borrow.getBorrowsBooks().stream()
                .filter(bb -> bb.getBook().getId().equals(bookId))
                .findFirst()
                .orElseThrow(() -> new ApiException(404, "Fail!", "Book is not part of this borrow: " + bookId));

        if (borrowsBook.getStatus() != BookStatusEnum.BORROWED) {
            throw new ApiException(400, "Fail!",
                    "Book status has already been finalized: " + borrowsBook.getStatus());
        }

        borrowsBook.setStatus(request.getStatus());
        borrowsBook.setReturnDate(new Date());
        borrowsBook.setReason(request.getStatus() == BookStatusEnum.LOST ? request.getReason() : null);
        borrowsBookRepository.save(borrowsBook);

        BookEntity book = borrowsBook.getBook();
        if (request.getStatus() == BookStatusEnum.RETURNED) {
            book.setAvailableQty(book.getAvailableQty() + 1);
        } else {
            // Lost copies are removed from circulation entirely, not just made unavailable
            book.setBookQty(book.getBookQty() - 1);
        }
        bookRepository.save(book);

        boolean allClosed = borrow.getBorrowsBooks().stream()
                .noneMatch(bb -> bb.getStatus() == BookStatusEnum.BORROWED);
        if (allClosed) {
            borrow.setReturnDate(new Date());
        }

        return borrowBookMapper.toResponse(borrowsRepository.save(borrow));
    }

    @Override
    @Transactional
    public void deleteBorrow(Long id) {
        BorrowsEntity borrow = getBorrowOrThrow(id);

        if (borrow.getRequestStatus() == RequestStatusEnum.PENDING
                || borrow.getRequestStatus() == RequestStatusEnum.APPROVED) {
            restockBooks(borrow.getBorrowsBooks().stream()
                    .filter(bb -> bb.getStatus() == BookStatusEnum.BORROWED)
                    .toList());
        }

        borrowsRepository.delete(borrow);
    }

    private void restockBooks(List<BorrowsBookEntity> borrowsBooks) {
        for (BorrowsBookEntity borrowsBook : borrowsBooks) {
            BookEntity book = borrowsBook.getBook();
            book.setAvailableQty(book.getAvailableQty() + 1);
            bookRepository.save(book);
        }
    }

    private BorrowsEntity getBorrowOrThrow(Long id) {
        return borrowsRepository.findById(id)
                .orElseThrow(() -> new ApiException(404, "Fail!", "Borrow not found: " + id));
    }
}