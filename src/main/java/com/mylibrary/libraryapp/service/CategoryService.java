package com.mylibrary.libraryapp.service;

import com.mylibrary.libraryapp.dto.requests.CategoryRequest;
import com.mylibrary.libraryapp.dto.response.CategoryResponse;
import com.mylibrary.libraryapp.entities.CategoryEntity;
import com.mylibrary.libraryapp.exceptions.MessageResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    List<CategoryResponse> findAll();

    List<CategoryResponse> findByName(String name);

    CategoryResponse createCategory(CategoryRequest request);

    CategoryResponse updateCategory(Long id, CategoryRequest request);

    void deleteCategory(Long id);

}
