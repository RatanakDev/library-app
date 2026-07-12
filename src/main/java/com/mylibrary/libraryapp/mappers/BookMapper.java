package com.mylibrary.libraryapp.mappers;

import com.mylibrary.libraryapp.dto.requests.BookRequest;
import com.mylibrary.libraryapp.dto.response.BookResponse;
import com.mylibrary.libraryapp.entities.BookEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = CategoryMapper.class)
public interface BookMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categories", ignore = true) // set manually in service
    BookEntity toEntity(BookRequest request);

    BookResponse toResponse(BookEntity entity);

    List<BookResponse> toResponseList(List<BookEntity> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categories", ignore = true)
    void updateEntityFromRequest(BookRequest request, @MappingTarget BookEntity entity);
}
