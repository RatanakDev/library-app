package com.mylibrary.libraryapp.dto.requests;

import com.mylibrary.libraryapp.entities.Enum.GenderEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRequest {

    @NotBlank(message = "Fullname is required")
    private String fullName;
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Gender is required")
    private GenderEnum gender;
    @NotBlank(message = "Phone Number is required")
    private String phoneNumber;
    @NotBlank(message = "Password is required")
    private String password;
}
