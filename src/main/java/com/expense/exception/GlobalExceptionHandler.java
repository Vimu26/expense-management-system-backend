package com.expense.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.expense.exception.ErrorResponse;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("error", ex.getMessage()));
    }

    // You can customize more exceptions later

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("error", "Something went wrong"));
    }
}
