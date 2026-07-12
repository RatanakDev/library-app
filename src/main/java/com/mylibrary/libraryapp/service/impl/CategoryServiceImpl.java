package com.mylibrary.libraryapp.service.impl;

import com.mylibrary.libraryapp.dto.requests.CategoryRequest;
import com.mylibrary.libraryapp.dto.response.CategoryResponse;
import com.mylibrary.libraryapp.entities.CategoryEntity;
import com.mylibrary.libraryapp.exceptions.ApiException;
import com.mylibrary.libraryapp.exceptions.DuplicateResourceException;
import com.mylibrary.libraryapp.mappers.CategoryMapper;
import com.mylibrary.libraryapp.repositories.CategoryRepository;
import com.mylibrary.libraryapp.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryResponse> findAll() {

        List<CategoryEntity> categoryEntities = categoryRepository.findAll();

        return categoryMapper.toResponseList(categoryEntities);
    }

    @Override
    public List<CategoryResponse> findByName(String name) {

        List<CategoryEntity> findCate = categoryRepository.findByCategoryNameContaining(name);

        return categoryMapper.toResponseList(findCate);
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {

        if (categoryRepository.existsByCategoryNameIgnoreCase(request.getCategoryName())) {
            throw new DuplicateResourceException("Category with name '" + request.getCategoryName() + "' already exists");
        }

        CategoryEntity categoryEntity = categoryMapper.toEntity(request);
        return categoryMapper.toResponse(categoryRepository.save(categoryEntity));
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {

        CategoryEntity findCategory = categoryRepository.findById(id).orElse(null);

        if (findCategory == null) {
            throw new ApiException(404,"Fail","Category not found");
        }

        findCategory.setCategoryName(request.getCategoryName());

        if (categoryRepository.existsByCategoryNameIgnoreCase(request.getCategoryName())) {
            throw new DuplicateResourceException("Category with name '" + request.getCategoryName() + "' already exists");
        }

        return categoryMapper.toResponse(categoryRepository.save(findCategory));

    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
