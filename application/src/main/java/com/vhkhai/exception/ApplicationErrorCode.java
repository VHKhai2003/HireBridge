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
    FAILED_TO_CREATE_ACCOUNT(400003, "Failed to create account", 400),
    INVALID_FILE(400004, "Invalid file", 400),

    CANDIDATE_NOT_FOUND(404001, "Candidate not found", 404),
    COMPANY_NOT_FOUND(404002, "Company not found", 404),

    ACCESS_DENIED(403001, "Access denied", 403);


    private final int code;
    private final String desc;
    private final int statusCode;
}
