package com.mylibrary.libraryapp.seeder;

import com.mylibrary.libraryapp.entities.UserEntity;
import com.mylibrary.libraryapp.entities.Enum.GenderEnum;
import com.mylibrary.libraryapp.entities.Enum.RoleEnum;
import com.mylibrary.libraryapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class UserSeeder implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        if (userRepository.findByFullNameContaining("admin").isEmpty()) {
            UserEntity admin = UserEntity.builder()
                    .fullName("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .role(RoleEnum.ADMIN)
                    .email("admin@library.com")
                    .gender(GenderEnum.OTHER)
                    .phoneNumber("0123456789")
                    .build();
            userRepository.save(admin);
            System.out.println("✅ Admin user seeded.");
        }

        if (userRepository.findByFullNameContaining("user").isEmpty()) {
            UserEntity user = UserEntity.builder()
                    .fullName("user")
                    .password(passwordEncoder.encode("user123"))
                    .role(RoleEnum.USER)
                    .email("user@library.com")
                    .gender(GenderEnum.OTHER)
                    .phoneNumber("0123456789")
                    .build();
            userRepository.save(user);
            System.out.println("✅ User user seeded.");
        }
    }
}