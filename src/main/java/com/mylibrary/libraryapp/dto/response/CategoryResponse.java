package com.mylibrary.libraryapp.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryResponse {
    private Long id;
    private String categoryName;
}
