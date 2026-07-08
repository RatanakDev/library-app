package com.mylibrary.libraryapp.service.jwt;

import com.mylibrary.libraryapp.entities.UserEntity;
import com.mylibrary.libraryapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailService implements UserDetailsService
{
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String fullName) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByFullName(fullName).orElseThrow(()
                -> new UsernameNotFoundException("User not found"));
        return new UserPrincipal(user);
    }
}
