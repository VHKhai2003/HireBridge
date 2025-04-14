package com.vhkhai.exception;

public enum ApplicationErrorCode {
    INCORRECT_USERNAME_OR_PASSWORD(400, "Incorrect username or password");
    private final int code;
    private final String desc;

    ApplicationErrorCode(int code, String desc) {
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
