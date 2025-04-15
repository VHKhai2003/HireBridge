package com.vhkhai.exception;

import lombok.Getter;

@Getter
public class SecurityException extends BaseException{
    public SecurityException(ErrorCode errorCode) {
        super(errorCode);
    }
}
