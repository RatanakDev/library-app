package com.mylibrary.libraryapp.dto.response;

import com.mylibrary.libraryapp.entities.CategoryEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
//public class BookResponse {
//    private Long id;
//    private String title;
//    private String author;
//    private String bookCode;
//    private String imageUrl;
//    private CategoryEntity category;
//    private String description;
//    private Long bookQty;
//}

public class BookResponse {
    private Long id;
    private String bookCode;
    private String imageUrl;
    private String title;
    private String author;
    private String description;
    private Long bookQty;
    private Long availableQty;
    private Set<CategoryResponse> categories = new HashSet<>();
}
