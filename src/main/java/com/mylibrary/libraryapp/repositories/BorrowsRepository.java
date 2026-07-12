package com.mylibrary.libraryapp.repositories;

import com.mylibrary.libraryapp.entities.BorrowsEntity;
import com.mylibrary.libraryapp.entities.Enum.RequestStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowsRepository extends JpaRepository<BorrowsEntity, Long> {
    List<BorrowsEntity> findByUser_Id(String userId);

    List<BorrowsEntity> findByRequestStatus(RequestStatusEnum requestStatus);
}