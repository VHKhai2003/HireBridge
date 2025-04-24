package com.vhkhai.dto.job_application;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class InterviewRequestDto {
    private LocalDateTime startTime;
    private Integer duration;
    private boolean isOnline;
    private String link;
}
