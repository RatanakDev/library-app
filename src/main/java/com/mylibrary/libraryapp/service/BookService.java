package com.mylibrary.libraryapp.service;

import com.mylibrary.libraryapp.dto.requests.BookRequest;
import com.mylibrary.libraryapp.dto.response.BookResponse;
import com.mylibrary.libraryapp.entities.BookEntity;
import com.mylibrary.libraryapp.entities.CategoryEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {
    List<BookEntity> findAllBooks();
    BookEntity findByBookCode(String bookCode);
    List<BookEntity> findByCategory(CategoryEntity category);
    List<BookEntity> findAllBooksByTitle(String title);
    List<BookEntity> findAllBooksByAuthor(String author);
    List<BookEntity> findByBookCodeAndCategoryAndTitleAndAuthor(
            String bookCode, CategoryEntity category, String title, String author);


    BookResponse createBook(BookRequest bookRequest);
    BookResponse updateBook(Long id,BookRequest bookRequest);
    void deleteBookByBookCode(Long id);
}
