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
    START_TIME_AFTER_CURRENT_TIME(400003, "Start time must be before current time", 400),
    INVALID_START_TIME_FOR_SCHEDULED_TASK(400004, "Start time must be after current time", 400),
    SCHEDULED_TASK_ALREADY_EXISTS(400005, "Scheduled task already exists", 400),

    INVALID_TOKEN(401001, "Invalid token", 401),
    UNAUTHORIZED(401002, "Unauthorized", 401),

    FORBIDDEN(403001, "Forbidden", 403),

    NOT_FOUND(404001, "Not found", 404),
    SCHEDULED_TASK_NOT_FOUND(404002, "Scheduled task not found", 404),

    CONFLICT(409001, "Conflict", 409),

    INTERNAL_SERVER_ERROR(500001, "Internal server error", 500),
    FAILED_TO_UPLOAD_FILE(500002, "Failed to upload file", 500),
    FAILED_TO_DELETE_FILE(500003, "Failed to delete file", 500);


    private final int code;
    private final String desc;
    private final int statusCode;

}
