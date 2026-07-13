package com.mylibrary.libraryapp.controller;

import com.mylibrary.libraryapp.dto.requests.BorrowsBookRequest;
import com.mylibrary.libraryapp.dto.requests.BorrowsBookStatusRequest;
import com.mylibrary.libraryapp.dto.response.BorrowsBooksResponse;
import com.mylibrary.libraryapp.entities.Enum.BookStatusEnum;
import com.mylibrary.libraryapp.entities.Enum.RequestStatusEnum;
import com.mylibrary.libraryapp.exceptions.MessageResponse;
import com.mylibrary.libraryapp.service.BorrowBookService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/borrows")
public class BorrowBookController {

    private final BorrowBookService borrowBookService;

    @PostMapping
    public ResponseEntity<MessageResponse<BorrowsBooksResponse>> createBorrow(
            @Valid @RequestBody BorrowsBookRequest request) {
        BorrowsBooksResponse response = borrowBookService.createBorrow(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new MessageResponse<>(response, HttpStatus.CREATED.value(), "success",
                        "Borrow request created successfully"));
    }

    @GetMapping
    public ResponseEntity<MessageResponse<List<BorrowsBooksResponse>>> getAllBorrows() {
        List<BorrowsBooksResponse> response = borrowBookService.findAllBorrows();
        return ResponseEntity.ok(new MessageResponse<>(response, HttpStatus.OK.value(), "success",
                "Borrows retrieved successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponse<BorrowsBooksResponse>> getBorrowById(@PathVariable Long id) {
        BorrowsBooksResponse response = borrowBookService.findBorrowById(id);
        return ResponseEntity.ok(new MessageResponse<>(response, HttpStatus.OK.value(), "success",
                "Borrow retrieved successfully"));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<MessageResponse<List<BorrowsBooksResponse>>> getBorrowsByUser(
            @PathVariable String userId) {
        List<BorrowsBooksResponse> response = borrowBookService.findBorrowsByUser(userId);
        return ResponseEntity.ok(new MessageResponse<>(response, HttpStatus.OK.value(), "success",
                "Borrows retrieved successfully"));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<MessageResponse<List<BorrowsBooksResponse>>> getBorrowsByStatus(
            @PathVariable RequestStatusEnum status) {
        List<BorrowsBooksResponse> response = borrowBookService.findBorrowsByStatus(status);
        return ResponseEntity.ok(new MessageResponse<>(response, HttpStatus.OK.value(), "success",
                "Borrows retrieved successfully"));
    }

    @PatchMapping("/{id}/approve")
    public ResponseEntity<MessageResponse<BorrowsBooksResponse>> approveBorrow(@PathVariable Long id) {
        BorrowsBooksResponse response = borrowBookService.approveBorrow(id);
        return ResponseEntity.ok(new MessageResponse<>(response, HttpStatus.OK.value(), "success",
                "Borrow approved successfully"));
    }

    @PatchMapping("/{id}/reject")
    public ResponseEntity<MessageResponse<BorrowsBooksResponse>> rejectBorrow(@PathVariable Long id) {
        BorrowsBooksResponse response = borrowBookService.rejectBorrow(id);
        return ResponseEntity.ok(new MessageResponse<>(response, HttpStatus.OK.value(), "success",
                "Borrow rejected successfully"));
    }

    @PatchMapping("/{id}/books/{bookId}/status")
    public ResponseEntity<MessageResponse<BorrowsBooksResponse>> updateBookStatus(
            @PathVariable Long id,
            @PathVariable Long bookId,
            @Valid @RequestBody BorrowsBookStatusRequest request) {
        BorrowsBooksResponse response = borrowBookService.updateBookStatus(id, bookId, request);
        String message = request.getStatus() == BookStatusEnum.LOST
                ? "Book marked as lost successfully"
                : "Book returned successfully";
        return ResponseEntity.ok(new MessageResponse<>(response, HttpStatus.OK.value(), "success", message));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse<Void>> deleteBorrow(@PathVariable Long id) {
        borrowBookService.deleteBorrow(id);
        return ResponseEntity.ok(new MessageResponse<>(HttpStatus.OK.value(), "success",
                "Borrow deleted successfully"));
    }
}
