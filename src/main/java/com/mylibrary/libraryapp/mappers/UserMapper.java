package com.mylibrary.libraryapp.mappers;

import com.mylibrary.libraryapp.dto.requests.UserRequest;
import com.mylibrary.libraryapp.dto.response.UserResponse;
import com.mylibrary.libraryapp.entities.RoleEnum;
import com.mylibrary.libraryapp.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserEntity toEntity(UserRequest request){
        return UserEntity.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .gender(request.getGender())
                .phoneNumber(request.getPhoneNumber())
                .password(request.getPassword())
                .role(RoleEnum.USER)
                .build();
    }

    public UserResponse toResponse(UserEntity request){
        return UserResponse.builder()
                .id(request.getId())
                .fullName(request.getFullName())
                .email(request.getEmail())
                .gender(request.getGender())
                .phoneNumber(request.getPhoneNumber())
                .role(request.getRole())
                .build();
    }
}
