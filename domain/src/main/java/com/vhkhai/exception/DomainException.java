package com.vhkhai.exception;

public class DomainException extends RuntimeException {
    private final int statusCode;

    public DomainException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public DomainException(String message) {
        super(message);
        this.statusCode = -1;
    }
    public DomainException(DomainErrorCode errorCode) {
        super(errorCode.getDesc());
        this.statusCode = errorCode.getCode();
    }
}
