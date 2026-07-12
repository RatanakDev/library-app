package com.mylibrary.libraryapp.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ApiException extends RuntimeException {

    private int code;
    private String status;
    private String message;

    public ApiException(int code, String status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }


}
