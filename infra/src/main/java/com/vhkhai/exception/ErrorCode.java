package com.vhkhai.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public enum ErrorCode {
    INVALID_REQUEST(400001, "Invalid request", 400),
    INVALID_PARAMETER(400002, "Invalid parameter", 400),
    INVALID_TOKEN(401001, "Invalid token", 401),
    UNAUTHORIZED(401002, "Unauthorized", 401),
    FORBIDDEN(403001, "Forbidden", 403),
    NOT_FOUND(404001, "Not found", 404),
    CONFLICT(409001, "Conflict", 409),
    INTERNAL_SERVER_ERROR(500001, "Internal server error", 500);

    private final int code;
    private final String desc;
    private final int statusCode;

}
