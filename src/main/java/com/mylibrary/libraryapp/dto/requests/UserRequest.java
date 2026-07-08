package com.mylibrary.libraryapp.dto.requests;

import com.mylibrary.libraryapp.entities.GenderEnum;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRequest {
    private String fullName;
    private String email;
    private GenderEnum gender;
    private String phoneNumber;
    private String password;
}
