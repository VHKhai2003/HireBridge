package com.vhkhai.exception;

public enum DomainErrorCode {

    EMAIL_ALREADY_EXISTS(1001, "Email already exists"),
    ACCOUNT_TYPE_NOT_FOUND(1002, "Account type not found"),


    COMPANY_ALREADY_FOLLOWED(2001, "Company already followed"),
    COMPANY_NOT_FOLLOWED(2002, "Company not followed"),;



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
