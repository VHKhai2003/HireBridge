package com.vhkhai.exception.handler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.vhkhai.exception.ApplicationException;
import com.vhkhai.exception.DomainException;
import com.vhkhai.exception.SecurityException;
import com.vhkhai.exception.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Date;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

    //HttpRequestMethodNotSupportedException

    // for @RequestBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity handleHttpMessageNotReadable(HttpMessageNotReadableException exception, WebRequest request) {
        log.error("HttpMessageNotReadableException handler: {}", exception.getMessage());
        logRequestUrl(request);
        Throwable cause = exception.getCause();
        String message = exception.getMessage();
        if (cause instanceof InvalidFormatException) {
            InvalidFormatException ife = (InvalidFormatException) cause;
            String fieldName = ife.getPath().get(0).getFieldName();
            message = String.format("Field '%s' has the invalid value", fieldName);
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.builder()
                        .status(400)
                        .message(message)
                        .timestamp(new Date())
                        .build());
    }

    // for @NotBlank, @Min,...
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleValidationErrors(MethodArgumentNotValidException exception, WebRequest request) {
        log.error("MethodArgumentNotValidException handler: {}", exception.getMessage());
        logRequestUrl(request);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.builder()
                        .status(400)
                        .message(exception.getAllErrors().get(0).getDefaultMessage())
                        .timestamp(new Date())
                        .build());
    }

    // for @PathVarialble, @RequestParam validation
    // replace fow ConstraintViolationException
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity handleHandlerMethodValidationException(HandlerMethodValidationException exception, WebRequest request) {
        log.error("HandlerMethodValidationException handler: {}", exception.getMessage());
        logRequestUrl(request);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.builder()
                        .status(400)
                        .message("Invalid value for parameter")
                        .timestamp(new Date())
                        .build());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException exception, WebRequest request) {
        log.error("MethodArgumentTypeMismatchException handler: {}", exception.getMessage());
        logRequestUrl(request);

        String paramName = exception.getName();
        String expectedType = exception.getRequiredType() != null ? exception.getRequiredType().getSimpleName() : "unknown";
        String actualValue = String.valueOf(exception.getValue());

        String message = String.format(
                "Parameter '%s' has value '%s' can not convert to %s.",
                paramName, actualValue, expectedType
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.builder()
                        .status(400)
                        .message(message)
                        .timestamp(new Date())
                        .build());
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity handleApplicationException(ApplicationException exception, WebRequest request) {
        log.error("ApplicationException handler: {}", exception.getErrorCode());
        logRequestUrl(request);
        return ResponseEntity
                .status(exception.getErrorCode().getStatusCode())
                .body(ErrorResponse.builder()
                        .status(exception.getErrorCode().getStatusCode())
                        .message(exception.getErrorCode().getDesc())
                        .timestamp(new Date())
                        .build());
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity handleSecurityException(SecurityException exception, WebRequest request) {
        log.error("SecurityException handler: {}", exception.getErrorCode());
        logRequestUrl(request);
        return ResponseEntity
                .status(exception.getErrorCode().getStatusCode())
                .body(ErrorResponse.builder()
                        .status(exception.getErrorCode().getStatusCode())
                        .message(exception.getErrorCode().getDesc())
                        .timestamp(new Date())
                        .build());
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity handleDomainException(DomainException exception, WebRequest request) {
        log.error("DomainException handler: (code={}, message={})", exception.getStatusCode(), exception.getMessage());
        logRequestUrl(request);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.builder()
                        .status(400)
                        .message(exception.getMessage())
                        .timestamp(new Date())
                        .build());
    }

    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity handleException(Exception e, WebRequest request) {
        log.error("Global exception handler: {} - {}", e.getClass(), e.getMessage());
        logRequestUrl(request);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.builder()
                        .status(500)
                        .message(e.getMessage())
                        .timestamp(new Date())
                        .build());
    }

    private void logRequestUrl(WebRequest request) {
        log.info("Request url: {}", request.getDescription(false).replace("uri=", ""));
    }

}
