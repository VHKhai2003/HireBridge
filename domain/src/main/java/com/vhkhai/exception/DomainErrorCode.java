package com.vhkhai.exception;

public enum DomainErrorCode {

    EMAIL_ALREADY_EXISTS(1001, "Email already exists"),
    ACCOUNT_TYPE_NOT_FOUND(1002, "Account type not found"),


    COMPANY_ALREADY_FOLLOWED(2001, "Company already followed"),
    COMPANY_NOT_FOLLOWED(2002, "Company not followed"),
    INVALID_FULLNAME(2003, "Invalid fullname"),
    INVALID_PHONE(2004, "Invalid phone"),

    INVALID_COMPANY_NAME(3001, "Invalid company name"),
    INVALID_COMPANY_PHONE(3002, "Invalid company phone"),
    INVALID_COMPANY_ADDRESS(3003, "Invalid company address"),

    INVALID_INTERVIEW_TIME(4001, "Invalid interview time"),
    CANNOT_SCHEDULE_INTERVIEW(4002, "Cannot schedule interview"),
    CANNOT_CANCEL_INTERVIEW(4003, "Cannot cancel interview"),
    CANNOT_COMPLETE_INTERVIEW(4004, "Cannot complete interview"),
    CANNOT_CONDUCT_INTERVIEW(4005, "Cannot update interview"),
    ;



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
