package com.mylibrary.libraryapp.entities;

import com.mylibrary.libraryapp.entities.Enum.GenderEnum;
import com.mylibrary.libraryapp.entities.Enum.RoleEnum;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GenderEnum gender;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleEnum role;

}
