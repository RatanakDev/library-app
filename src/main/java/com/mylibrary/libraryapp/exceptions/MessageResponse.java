package com.mylibrary.libraryapp.exceptions;

import lombok.Getter;

@Getter
public class MessageResponse<T> {

    private T data;
    private int code;
    private String status;
    private String message;

    public MessageResponse(int code, String status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

    public MessageResponse(T data, int code, String status, String message) {
        this.data = data;
        this.code = code;
        this.status = status;
        this.message = message;
    }
}
