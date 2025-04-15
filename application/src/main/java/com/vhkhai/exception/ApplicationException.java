package com.vhkhai.exception;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {
    private ApplicationErrorCode errorCode;
    public ApplicationException(ApplicationErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
