package com.vhkhai.exception;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class InfrastructureException extends RuntimeException {
    protected final ErrorCode errorCode;

    public InfrastructureException(ErrorCode errorCode) {
        super(errorCode.getDesc());
        this.errorCode = errorCode;
    }
}
