package com.mylibrary.libraryapp.service;

import com.mylibrary.libraryapp.dto.response.UserResponse;
import com.mylibrary.libraryapp.entities.UserEntity;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<UserResponse> findAll();
}
