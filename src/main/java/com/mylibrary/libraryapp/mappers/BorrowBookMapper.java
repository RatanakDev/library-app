package com.mylibrary.libraryapp.mappers;

import com.mylibrary.libraryapp.dto.requests.BorrowsBookRequest;
import com.mylibrary.libraryapp.dto.response.BorrowsBooksResponse;
import com.mylibrary.libraryapp.entities.BorrowsBookEntity;
import com.mylibrary.libraryapp.entities.BorrowsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class, BookMapper.class})
public interface BorrowBookMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true) // set manually in service
    @Mapping(target = "borrowsBooks", ignore = true) // set manually in service
    @Mapping(target = "requestStatus", ignore = true)
    BorrowsEntity toEntity(BorrowsBookRequest request);

    @Mapping(target = "books", source = "borrowsBooks")
    BorrowsBooksResponse toResponse(BorrowsEntity entity);

    List<BorrowsBooksResponse> toResponseList(List<BorrowsEntity> entities);

    BorrowsBooksResponse.BorrowedBookResponse toBorrowedBookResponse(BorrowsBookEntity entity);

    List<BorrowsBooksResponse.BorrowedBookResponse> toBorrowedBookResponseList(List<BorrowsBookEntity> entities);
}
