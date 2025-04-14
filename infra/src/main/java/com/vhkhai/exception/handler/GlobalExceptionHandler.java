package com.vhkhai.exception.handler;

import com.vhkhai.exception.AuthException;
import com.vhkhai.exception.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(AuthException.class)
    public ResponseEntity handleAuthException(AuthException ae) {
        log.error("Auth exception handler: {}", ae.getMessage());
        return ResponseEntity
                .status(ae.getStatusCode())
                .body(ErrorResponse.builder()
                        .status(ae.getStatusCode())
                        .message(ae.getMessage())
                        .timestamp(new Date())
                        .build());
    }


    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity handleException(Exception e) {
        log.error("Global exception handler: {}", e.getMessage());
        return ResponseEntity
                .status(500)
                .body(ErrorResponse.builder()
                        .status(500)
                        .message(e.getMessage())
                        .timestamp(new Date())
                        .build());
    }
}
