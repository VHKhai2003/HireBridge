package com.vhkhai.dto.job_application;

import com.vhkhai.enumerations.InterviewStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InterviewResponseDto {
    private UUID id;
    private LocalDateTime startTime;
    private Integer duration;
    private Boolean isOnline;
    private String link;
    private InterviewStatus status;
}
