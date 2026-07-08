package com.mylibrary.libraryapp.dto.response;

import com.mylibrary.libraryapp.entities.GenderEnum;
import com.mylibrary.libraryapp.entities.RoleEnum;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserResponse {
    private String id;
    private String fullName;
    private String email;
    private GenderEnum gender;
    private String phoneNumber;
    private RoleEnum role;
}
