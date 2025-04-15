package com.vhkhai.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public enum ApplicationErrorCode {
    INCORRECT_EMAIL_OR_PASSWORD(400001, "Incorrect email or password", 400),
    ACCOUNT_NOT_FOUND(400002, "Account not found", 400),
    FAILED_TO_CREATE_ACCOUNT(400003, "Failed to create account", 400);

    private final int code;
    private final String desc;
    private final int statusCode;
}
