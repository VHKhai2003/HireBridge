package com.vhkhai.exception;

import lombok.Getter;

@Getter
public class SecurityException extends InfrastructureException {
    public SecurityException(ErrorCode errorCode) {
        super(errorCode);
    }
}
