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

    INVALID_INTERVIEW_TIME(4001, "Interview time must be after at least 1 hour from now"),
    CANNOT_SCHEDULE_INTERVIEW(4002, "Cannot schedule interview"),
    CANNOT_CANCEL_INTERVIEW(4003, "Cannot cancel interview"),
    CANNOT_COMPLETE_INTERVIEW(4004, "Cannot complete interview"),
    CANNOT_CONDUCT_INTERVIEW(4005, "Cannot update interview"),
    JOB_APPLICATION_ALREADY_EXISTS(4006, "Job application already exists"),
    JOB_APPLICATION_NOT_FOUND(4007, "Job application not found"),
    CANDIDATE_HAS_NO_CV(4008, "Candidate has no CV"),
    INTERVIEW_NOT_FOUND(4009, "Interview not found"),
    CANNOT_UPDATE_INTERVIEW(4010, "Cannot update interview"),
    INVALID_INTERVIEW_LINK(4011, "Invalid interview link"),
    INVALID_INTERVIEW_DURATION(4012, "Invalid interview duration"),
    REJECT_REASON_IS_REQUIRED(4013, "Reject reason is required"),
    CANNOT_REJECT_WHEN_HAVING_INTERVIEW(4014, "Cannot reject when having interview"),
    CANNOT_OFFER_WHEN_HAVING_INTERVIEW(4015, "Cannot offer when having interview"),
    JOB_APPLICATION_OFFERED_OR_REJECTED(4016, "Job application has been offered or rejected"),
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
