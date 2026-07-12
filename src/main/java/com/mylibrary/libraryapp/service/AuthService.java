package com.mylibrary.libraryapp.service;

import com.mylibrary.libraryapp.dto.requests.LogInRequest;
import com.mylibrary.libraryapp.dto.response.AuthResponse;
import com.mylibrary.libraryapp.entities.UserEntity;
import com.mylibrary.libraryapp.service.jwt.JwtService;
import com.mylibrary.libraryapp.service.jwt.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthResponse logIn(LogInRequest logInRequest) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        logInRequest.fullName(),
                        logInRequest.password()
                )
        );
        if(auth.isAuthenticated()) {
            UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();

            UserEntity user = userPrincipal.getUser();
            String token = jwtService.generateToken(user);
            return  new AuthResponse(token,"Login succesfully");
        }

        throw new UsernameNotFoundException("Invalid username or password");

    }
}
