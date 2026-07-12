package com.mylibrary.libraryapp.repositories;

import com.mylibrary.libraryapp.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    List<UserEntity> findByFullNameContaining(String fullName);
    Optional<UserEntity> findByFullName(String fullName);

    boolean existsByEmailIgnoreCase(String email);



}
