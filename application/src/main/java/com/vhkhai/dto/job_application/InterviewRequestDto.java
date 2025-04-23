package com.vhkhai.dto.job_application;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class InterviewRequestDto {
    private UUID jobApplicationId;
    private LocalDateTime startTime;
    private Integer duration;
    private boolean isOnline;
    private String link;

}
