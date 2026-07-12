package com.mylibrary.libraryapp.service;

import com.mylibrary.libraryapp.dto.requests.BorrowsBookRequest;
import com.mylibrary.libraryapp.dto.requests.BorrowsBookStatusRequest;
import com.mylibrary.libraryapp.dto.response.BorrowsBooksResponse;
import com.mylibrary.libraryapp.entities.Enum.RequestStatusEnum;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BorrowBookService {

    BorrowsBooksResponse createBorrow(BorrowsBookRequest request);

    List<BorrowsBooksResponse> findAllBorrows();

    BorrowsBooksResponse findBorrowById(Long id);

    List<BorrowsBooksResponse> findBorrowsByUser(String userId);

    List<BorrowsBooksResponse> findBorrowsByStatus(RequestStatusEnum status);

    BorrowsBooksResponse approveBorrow(Long id);

    BorrowsBooksResponse rejectBorrow(Long id);

    BorrowsBooksResponse updateBookStatus(Long borrowId, Long bookId, BorrowsBookStatusRequest request);

    void deleteBorrow(Long id);
}