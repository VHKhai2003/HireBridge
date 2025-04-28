package com.vhkhai.dto.job_posting;

import com.vhkhai.enumerations.JobField;
import com.vhkhai.enumerations.JobLevel;
import com.vhkhai.enumerations.JobPostingStatus;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobPostingResponseDto implements Serializable {
    private UUID id;
    private String title;
    private String description;
    private JobPostingStatus status;
    private LocalDateTime createdAt;
    private JobField field;
    private JobLevel level;
}
