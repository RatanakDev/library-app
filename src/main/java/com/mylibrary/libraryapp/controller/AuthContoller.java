package com.mylibrary.libraryapp.controller;

import com.mylibrary.libraryapp.dto.requests.LogInRequest;
import com.mylibrary.libraryapp.dto.response.AuthResponse;
import com.mylibrary.libraryapp.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthContoller {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LogInRequest logInRequest) {
        return ResponseEntity.ok(authService.logIn(logInRequest));
    }

}
