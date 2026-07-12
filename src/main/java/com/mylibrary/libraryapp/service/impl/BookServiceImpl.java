package com.mylibrary.libraryapp.service.impl;

import com.mylibrary.libraryapp.dto.requests.BookRequest;
import com.mylibrary.libraryapp.dto.response.BookResponse;
import com.mylibrary.libraryapp.entities.BookEntity;
import com.mylibrary.libraryapp.entities.CategoryEntity;
import com.mylibrary.libraryapp.exceptions.ApiException;
import com.mylibrary.libraryapp.exceptions.DuplicateResourceException;
import com.mylibrary.libraryapp.mappers.BookMapper;
import com.mylibrary.libraryapp.repositories.BookRepository;
import com.mylibrary.libraryapp.repositories.CategoryRepository;
import com.mylibrary.libraryapp.service.BookService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final BookMapper bookMapper;

    @Override
    public List<BookEntity> findAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public BookEntity findByBookCode(String bookCode) {
        BookEntity book = bookRepository.findByBookCode(bookCode);
        if (book == null) {
            throw new ApiException(404, "Fail!", "Book not found with code: " + bookCode);
        }
        return book;
    }

    @Override
    public List<BookEntity> findByCategory(CategoryEntity category) {
        return new ArrayList<>(bookRepository.findByCategories_Id(category.getId()));
    }

    @Override
    public List<BookEntity> findAllBooksByTitle(String title) {
        return bookRepository.findAllBooksByTitle(title);
    }

    @Override
    public List<BookEntity> findAllBooksByAuthor(String author) {
        return bookRepository.findAllBooksByAuthor(author);
    }

    @Override
    public List<BookEntity> findByBookCodeAndCategoryAndTitleAndAuthor(
            String bookCode, CategoryEntity category, String title, String author) {

        Specification<BookEntity> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (bookCode != null && !bookCode.isBlank()) {
                predicates.add(cb.equal(root.get("bookCode"), bookCode));
            }
            if (category != null) {
                Join<BookEntity, CategoryEntity> categoryJoin = root.join("categories");
                predicates.add(cb.equal(categoryJoin.get("id"), category.getId()));
            }
            if (title != null && !title.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
            }
            if (author != null && !author.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("author")), "%" + author.toLowerCase() + "%"));
            }

            if (query != null) {
                query.distinct(true);
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return bookRepository.findAll(spec);
    }

    @Override
    public BookResponse createBook(BookRequest request) {
        // Check for duplicate bookCode BEFORE creating
        BookEntity existing = bookRepository.findByBookCode(request.getBookCode());

        if (existing != null) {
            throw new DuplicateResourceException("Book already exists with code: " + request.getBookCode());
        }

        BookEntity book = bookMapper.toEntity(request);
        book.setAvailableQty(request.getBookQty()); // a new book starts with every copy available

        if (request.getCategoryIds() != null && !request.getCategoryIds().isEmpty()) {
            Set<CategoryEntity> categories = new HashSet<>(
                    categoryRepository.findAllById(request.getCategoryIds())
            );
            book.setCategories(categories);
        }

        BookEntity saved = bookRepository.save(book);
        return bookMapper.toResponse(saved);
    }

    @Override
    public BookResponse updateBook(Long id, BookRequest request) {

        // Check for duplicate bookCode BEFORE updating
        BookEntity existing = bookRepository.findByBookCode(request.getBookCode());

        if (existing != null) {
            throw new DuplicateResourceException("Book already exists with code: " + request.getBookCode());
        }

        BookEntity book = bookRepository.findById(id)
                .orElseThrow(() -> new ApiException(400, "Fail!", "Book not found: " + id));

        // copies currently out on loan must survive a bookQty edit, so derive it from the pre-update state
        long oldBookQty = book.getBookQty() != null ? book.getBookQty() : 0;
        long oldAvailableQty = book.getAvailableQty() != null ? book.getAvailableQty() : oldBookQty;
        long borrowedQty = oldBookQty - oldAvailableQty;

        bookMapper.updateEntityFromRequest(request, book);
        book.setAvailableQty(Math.max(request.getBookQty() - borrowedQty, 0));

        if (request.getCategoryIds() != null) {
            Set<CategoryEntity> categories = new HashSet<>(
                    categoryRepository.findAllById(request.getCategoryIds())
            );
            book.setCategories(categories);
        }

        return bookMapper.toResponse(bookRepository.save(book));
    }

    @Override
    public void deleteBookByBookCode(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ApiException(404, "Fail!", "Book not found: " + id);
        }
        bookRepository.deleteById(id);
    }
}
