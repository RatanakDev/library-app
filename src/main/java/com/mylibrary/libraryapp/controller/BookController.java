package com.mylibrary.libraryapp.controller;

import com.mylibrary.libraryapp.dto.requests.BookRequest;
import com.mylibrary.libraryapp.dto.response.BookResponse;
import com.mylibrary.libraryapp.entities.BookEntity;
import com.mylibrary.libraryapp.entities.CategoryEntity;
import com.mylibrary.libraryapp.exceptions.ApiException;
import com.mylibrary.libraryapp.exceptions.MessageResponse;
import com.mylibrary.libraryapp.mappers.BookMapper;
import com.mylibrary.libraryapp.repositories.CategoryRepository;
import com.mylibrary.libraryapp.service.BookService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/books")
public class BookController {

    private final BookService bookService;
    private final CategoryRepository categoryRepository;
    private final BookMapper bookMapper;

    @GetMapping
    public ResponseEntity<MessageResponse<List<BookResponse>>> getAllBooks() {
        List<BookResponse> response = bookMapper.toResponseList(bookService.findAllBooks());
        return ResponseEntity.ok(new MessageResponse<>(response, HttpStatus.OK.value(), "success",
                "Books retrieved successfully"));
    }

    @PostMapping
    public ResponseEntity<MessageResponse<BookResponse>> createBook(
            @Valid @RequestBody BookRequest request) {
        BookResponse response = bookService.createBook(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new MessageResponse<>(response, HttpStatus.CREATED.value(), "success",
                        "Book created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse<BookResponse>> updateBook(
            @PathVariable Long id,
            @Valid @RequestBody BookRequest request) {
        BookResponse response = bookService.updateBook(id, request);
        return ResponseEntity.ok(new MessageResponse<>(response, HttpStatus.OK.value(), "success",
                "Book updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse<Void>> deleteBook(@PathVariable Long id) {
        bookService.deleteBookByBookCode(id);
        return ResponseEntity.ok(new MessageResponse<>(HttpStatus.OK.value(), "success",
                "Book deleted successfully"));
    }

    @GetMapping("/code/{bookCode}")
    public ResponseEntity<MessageResponse<BookResponse>> getByBookCode(
            @PathVariable String bookCode) {
        BookEntity book = bookService.findByBookCode(bookCode);
        BookResponse response = bookMapper.toResponse(book);
        return ResponseEntity.ok(new MessageResponse<>(response, HttpStatus.OK.value(), "success",
                "Book retrieved successfully"));
    }

    @GetMapping("/title")
    public ResponseEntity<MessageResponse<List<BookResponse>>> getByTitle(
            @RequestParam String title) {
        List<BookResponse> response = bookMapper.toResponseList(
                bookService.findAllBooksByTitle(title));
        return ResponseEntity.ok(new MessageResponse<>(response, HttpStatus.OK.value(), "success",
                "Books retrieved successfully"));
    }

    @GetMapping("/author")
    public ResponseEntity<MessageResponse<List<BookResponse>>> getByAuthor(
            @RequestParam String author) {
        List<BookResponse> response = bookMapper.toResponseList(
                bookService.findAllBooksByAuthor(author));
        return ResponseEntity.ok(new MessageResponse<>(response, HttpStatus.OK.value(), "success",
                "Books retrieved successfully"));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<MessageResponse<List<BookResponse>>> getByCategory(
            @PathVariable Long categoryId) {
        CategoryEntity category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ApiException(404, "Fail", "Category not found: " + categoryId));
        List<BookResponse> response = bookMapper.toResponseList(
                bookService.findByCategory(category));
        return ResponseEntity.ok(new MessageResponse<>(response, HttpStatus.OK.value(), "success",
                "Books retrieved successfully"));
    }

    @GetMapping("/search")
    public ResponseEntity<MessageResponse<List<BookResponse>>> search(
            @RequestParam(required = false) String bookCode,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author) {

        CategoryEntity category = null;
        if (categoryId != null) {
            category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ApiException(404, "Fail", "Category not found: " + categoryId));
        }

        List<BookResponse> response = bookMapper.toResponseList(
                bookService.findByBookCodeAndCategoryAndTitleAndAuthor(bookCode, category, title, author));
        return ResponseEntity.ok(new MessageResponse<>(response, HttpStatus.OK.value(), "success",
                "Books retrieved successfully"));
    }
}
