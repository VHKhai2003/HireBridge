package com.vhkhai.exception;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BaseException extends RuntimeException {
    protected final ErrorCode errorCode;

    public BaseException(ErrorCode errorCode) {
        super(errorCode.getDesc());
        this.errorCode = errorCode;
    }
}
