package com.vhkhai.exception;

public enum DomainErrorCode {
    INVALID_REQUEST(400, "Invalid request"),
    INVALID_PARAMETER(400, "Invalid parameter"),
    INVALID_TOKEN(401, "Invalid token"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not found"),
    CONFLICT(409, "Conflict"),
    INTERNAL_SERVER_ERROR(500, "Internal server error");

    private final int code;
    private final String desc;

    DomainErrorCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
