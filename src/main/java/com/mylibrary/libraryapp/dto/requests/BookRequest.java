package com.mylibrary.libraryapp.dto.requests;


import com.mylibrary.libraryapp.entities.CategoryEntity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
//public class BookRequest {
//    @NotBlank
//    private String title;
//    @NotBlank
//    private String author;
//    @NotBlank
//    private String bookCode;
//    private String imageUrl;
//    private CategoryEntity category;
//
//    private String description;
//
//    @NotNull
//    @Positive
//    private Long bookQty;
//    private Long availableQty;
//}


public class BookRequest {

    @NotBlank(message = "Book code is required")
    private String bookCode;

    private String imageUrl;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Author is required")
    private String author;

    private String description;

    @NotNull(message = "Book quantity is required")
    @Min(value = 1, message = "Book quantity cannot be negative")
    private Long bookQty;

    private Set<Long> categoryIds = new HashSet<>();
}
