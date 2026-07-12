package com.mylibrary.libraryapp.mappers;

import com.mylibrary.libraryapp.dto.requests.CategoryRequest;
import com.mylibrary.libraryapp.dto.response.CategoryResponse;
import com.mylibrary.libraryapp.entities.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryEntity toEntity(CategoryRequest categoryRequest);

    CategoryResponse toResponse(CategoryEntity categoryEntity);

    List<CategoryResponse> toResponseList(List<CategoryEntity> entities);
}
