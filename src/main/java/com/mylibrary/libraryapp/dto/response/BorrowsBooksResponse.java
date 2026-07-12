package com.mylibrary.libraryapp.dto.response;

import com.mylibrary.libraryapp.entities.Enum.BookStatusEnum;
import com.mylibrary.libraryapp.entities.Enum.RequestStatusEnum;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BorrowsBooksResponse {
    private Long id;
    private UserResponse user;
    private Date borrowDate;
    private Date returnDate;
    private RequestStatusEnum requestStatus;
    private List<BorrowedBookResponse> books = new ArrayList<>();

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class BorrowedBookResponse {
        private Long id;
        private BookResponse book;
        private BookStatusEnum status;
        private Date returnDate;
        private String reason;
    }
}