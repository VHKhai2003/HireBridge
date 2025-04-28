package com.vhkhai.dto.job_posting;

import com.vhkhai.enumerations.JobField;
import com.vhkhai.enumerations.JobLevel;
import com.vhkhai.enumerations.JobPostingStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobPostingResponseDto {
    private UUID id;
    private String title;
    private String description;
    private JobPostingStatus status;
    private LocalDateTime createdAt;
    private JobField field;
    private JobLevel level;
}
