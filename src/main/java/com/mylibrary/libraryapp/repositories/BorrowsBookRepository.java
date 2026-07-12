package com.mylibrary.libraryapp.repositories;

import com.mylibrary.libraryapp.entities.BorrowsBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowsBookRepository extends JpaRepository<BorrowsBookEntity, Long> {
    List<BorrowsBookEntity> findByBorrows_Id(Long borrowId);
}