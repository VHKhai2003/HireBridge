package com.vhkhai.exception;

import lombok.Getter;

@Getter
public class AuthException extends BaseException {

    public AuthException(ErrorCode errorCode) {
        super(errorCode);
    }
}
