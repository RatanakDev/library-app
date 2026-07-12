package com.mylibrary.libraryapp.repositories;

import com.mylibrary.libraryapp.dto.response.CategoryResponse;
import com.mylibrary.libraryapp.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    List<CategoryEntity> findByCategoryNameContaining(String name);
    boolean existsByCategoryNameIgnoreCase(String categoryName);
}
