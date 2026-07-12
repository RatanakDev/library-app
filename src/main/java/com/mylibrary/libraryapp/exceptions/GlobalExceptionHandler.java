package com.mylibrary.libraryapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<MessageResponse<Object>> handleApiException(
            ApiException exception
    ) {
        return new ResponseEntity<>(
                new MessageResponse<>(
                        null,
                        exception.getCode(),
                        exception.getStatus(),
                        exception.getMessage()
                ),
                HttpStatus.valueOf(exception.getCode())
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MessageResponse<Map<String, String>>> handleValidationException(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getAllErrors()
                .forEach(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });

        MessageResponse<Map<String, String>> response = new MessageResponse<>(
                errors,
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Validation failed"
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }


//    JWT

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<MessageResponse<Object>> handleAuthenticationException(
            AuthenticationException exception
    ) {
        return new ResponseEntity<>(
                new MessageResponse<>(
                        null,
                        HttpStatus.UNAUTHORIZED.value(),
                        HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                        "Invalid credentials or missing token"
                ),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<MessageResponse<Object>> handleAccessDeniedException(
            AccessDeniedException exception
    ) {
        return new ResponseEntity<>(
                new MessageResponse<>(
                        null,
                        HttpStatus.FORBIDDEN.value(),
                        HttpStatus.FORBIDDEN.getReasonPhrase(),
                        "You do not have permission to access this resource"
                ),
                HttpStatus.FORBIDDEN
        );
    }

    //    handle duplicate


        @ExceptionHandler(DuplicateResourceException.class)
        public ResponseEntity<MessageResponse<Object>> handleDuplicate(DuplicateResourceException ex) {
            MessageResponse<Object> response = new MessageResponse<>(
                    null, 409, "Conflict", ex.getMessage()
            );
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

}