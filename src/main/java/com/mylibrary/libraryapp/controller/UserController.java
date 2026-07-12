package com.mylibrary.libraryapp.controller;

import com.mylibrary.libraryapp.dto.requests.UserRequest;
import com.mylibrary.libraryapp.dto.response.UserResponse;
import com.mylibrary.libraryapp.entities.UserEntity;
import com.mylibrary.libraryapp.exceptions.MessageResponse;
import com.mylibrary.libraryapp.repositories.UserRepository;
import com.mylibrary.libraryapp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse<List<UserResponse>>> findAll(
            @RequestParam(required = false) String fullName
    ) {
        List<UserResponse> users = userService.findAll(fullName);

        return new ResponseEntity<>(
                new MessageResponse<>(
                        users,
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase(),
                        "Users retrieved successfully"
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse<List<UserResponse>>> findByFullName(
            @RequestParam String fullName
    ) {
        List<UserResponse> findUser = userService.findAll(fullName);

        return new ResponseEntity<>(
                new MessageResponse<>(
                        findUser,
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase(),
                        "User found"
                ),
                HttpStatus.OK
        );
    }


    @PostMapping()
    public ResponseEntity<MessageResponse<UserResponse>> createUser(@Valid @RequestBody UserRequest userRequest) {

        UserResponse user = userService.createUser(userRequest);

        MessageResponse<UserResponse> response = new MessageResponse<>(
                user,
                HttpStatus.CREATED.value(),
                HttpStatus.CREATED.getReasonPhrase(),
                "User created successfully"
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse<UserResponse>> updateUser(@PathVariable("id") String id, @RequestBody UserRequest userRequest) {

        UserResponse user = userService.updateUser(id, userRequest);

        MessageResponse<UserResponse> response = new MessageResponse<>(
                200,"Success", "User updated successfully"
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse<UserResponse>> deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id);
        MessageResponse<UserResponse> response = new MessageResponse<>(
                200,"Success", "User deleted successfully"
        );
                return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
