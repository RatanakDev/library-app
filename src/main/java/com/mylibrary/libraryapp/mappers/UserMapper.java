package com.mylibrary.libraryapp.mappers;

import com.mylibrary.libraryapp.dto.requests.UserRequest;
import com.mylibrary.libraryapp.dto.response.UserResponse;
import com.mylibrary.libraryapp.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
//    public UserEntity toEntity(UserRequest request){
//        return UserEntity.builder()
//                .fullName(request.getFullName())
//                .email(request.getEmail())
//                .gender(request.getGender())
//                .phoneNumber(request.getPhoneNumber())
//                .password(request.getPassword())
//                .role(RoleEnum.USER)
//                .build();
//    }
//
//    public UserResponse toResponse(UserEntity request){
//        return UserResponse.builder()
//                .id(request.getId())
//                .fullName(request.getFullName())
//                .email(request.getEmail())
//                .gender(request.getGender())
//                .phoneNumber(request.getPhoneNumber())
//                .role(request.getRole())
//                .build();
//    }

    @Mapping(target = "role", constant = "USER")
    UserEntity toEntity(UserRequest request);

    UserResponse toResponse(UserEntity entity);

    List<UserResponse> toResponseList(List<UserEntity> entities);
}
