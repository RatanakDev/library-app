package com.mylibrary.libraryapp.service.impl;

import com.mylibrary.libraryapp.dto.response.UserResponse;
import com.mylibrary.libraryapp.entities.UserEntity;
import com.mylibrary.libraryapp.mappers.UserMapper;
import com.mylibrary.libraryapp.repositories.UserRepository;
import com.mylibrary.libraryapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Override
    public List<UserResponse> findAll() {

        List<UserEntity> user = userRepository.findAll();

        List<UserResponse> userReponse = user.stream().map(userMapper::toResponse)
                .toList();

        return userReponse;
    }


}
