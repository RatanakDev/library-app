package com.mylibrary.libraryapp.repositories;

import com.mylibrary.libraryapp.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

//@Repository
//public interface BookRepository extends JpaRepository<BookEntity, Long> {
//    BookEntity findByBookCode(String bookCode);
//    List<BookEntity> findAllBooksByTitle(String title);
//    List<BookEntity> findAllBooksByAuthor(String author);
//
//    Set<BookEntity> findByCategories_Id(Long categoryId);
//}

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long>, JpaSpecificationExecutor<BookEntity> {
    BookEntity findByBookCode(String bookCode);

    List<BookEntity> findAllBooksByTitle(String title);

    List<BookEntity> findAllBooksByAuthor(String author);

    Set<BookEntity> findByCategories_Id(Long categoryId);
}
