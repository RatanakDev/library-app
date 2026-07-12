package com.mylibrary.libraryapp.dto.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryRequest {

    @NotBlank(message = "Category Name is required")
    private String categoryName;
}
