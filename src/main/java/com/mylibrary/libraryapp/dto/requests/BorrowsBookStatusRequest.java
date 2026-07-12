package com.mylibrary.libraryapp.dto.requests;

import com.mylibrary.libraryapp.entities.Enum.BookStatusEnum;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BorrowsBookStatusRequest {

    @NotNull(message = "Status is required")
    private BookStatusEnum status;

    private String reason;
}
