package com.mylibrary.libraryapp.service.impl;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.mylibrary.libraryapp.dto.requests.UserRequest;
import com.mylibrary.libraryapp.dto.response.UserResponse;
import com.mylibrary.libraryapp.entities.UserEntity;
import com.mylibrary.libraryapp.exceptions.ApiException;
import com.mylibrary.libraryapp.exceptions.DuplicateResourceException;
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
    public List<UserResponse> findAll(String fullName) {

        if (fullName == null || fullName.isEmpty()) {
            List<UserEntity> user = userRepository.findAll();
            return userMapper.toResponseList(user);

        } else {
            List<UserEntity> findUser = userRepository.findByFullNameContaining(fullName);
            return userMapper.toResponseList(findUser);
        }
    }


    @Override
    public UserResponse createUser(UserRequest request) {

        if(userRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        UserEntity userEntity = userMapper.toEntity(request);
        userRepository.save(userEntity);

        return userMapper.toResponse(userEntity);

    }

    @Override
    public UserResponse updateUser(String id, UserRequest request) {

       UserEntity findUser = userRepository.findById(id).orElse(null);

       if (findUser == null) {
           throw new ApiException(404,"Not Found!", "User not found");
       }

       findUser.setFullName(request.getFullName());
       findUser.setEmail(request.getEmail());
       findUser.setPassword(request.getPassword());
       findUser.setGender(request.getGender());
       findUser.setPhoneNumber(request.getPhoneNumber());

       UserEntity userEntity = userRepository.save(findUser);

       UserResponse userResponse = userMapper.toResponse(userEntity);

        return userResponse;
    }

    @Override
    public void deleteUser(String id) {

        userRepository.deleteById(id);

    }


}
