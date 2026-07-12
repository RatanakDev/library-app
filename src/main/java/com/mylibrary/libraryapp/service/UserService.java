package com.mylibrary.libraryapp.service;

import com.mylibrary.libraryapp.dto.requests.UserRequest;
import com.mylibrary.libraryapp.dto.response.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    List<UserResponse> findAll(String fullName);

    UserResponse createUser(UserRequest request);

    UserResponse updateUser(String id, UserRequest request);

    void deleteUser(String id);
}
