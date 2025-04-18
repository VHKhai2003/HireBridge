package com.vhkhai.dto.job_posting;

import com.vhkhai.enumerations.JobPostingStatus;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobPostingResponseDto {
    private UUID id;
    private String title;
    private String requirement;
    private JobPostingStatus status;
}
