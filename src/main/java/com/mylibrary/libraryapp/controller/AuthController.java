package com.mylibrary.libraryapp.controller;

import com.mylibrary.libraryapp.dto.requests.LogInRequest;
import com.mylibrary.libraryapp.dto.response.AuthResponse;
import com.mylibrary.libraryapp.exceptions.MessageResponse;
import com.mylibrary.libraryapp.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<MessageResponse<AuthResponse>> login(@RequestBody LogInRequest logInRequest) {

        AuthResponse authResponse = authService.logIn(logInRequest);

        return new ResponseEntity<>(
                new MessageResponse<>(
                        authResponse,
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase(),
                        "Login successful"
                ),
                HttpStatus.OK
        );
    }

}
