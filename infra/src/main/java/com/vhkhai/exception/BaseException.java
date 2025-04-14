package com.vhkhai.exception;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BaseException extends RuntimeException {
    protected final int statusCode;

    public BaseException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public BaseException(String message) {
        super(message);
        this.statusCode = -1;
    }
    public BaseException(ErrorCode errorCode) {
        super(errorCode.getDesc());
        this.statusCode = errorCode.getCode();
    }
}
