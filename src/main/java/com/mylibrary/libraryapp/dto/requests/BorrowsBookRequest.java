package com.mylibrary.libraryapp.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BorrowsBookRequest {

    @NotBlank(message = "User is required")
    private String userId;

    @NotEmpty(message = "At least one book is required")
    private List<Long> bookIds;

    private Date borrowDate;

    private Date returnDate;
}
