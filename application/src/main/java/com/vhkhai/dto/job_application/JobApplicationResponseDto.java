package com.vhkhai.dto.job_application;

import com.vhkhai.dto.candidate.CandidateResponseDto;
import com.vhkhai.dto.job_posting.JobPostingResponseDto;
import com.vhkhai.enumerations.ApplicationStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobApplicationResponseDto {
    private UUID id;
    private CandidateResponseDto candidate;
    private JobPostingResponseDto jobPosting;
    private ApplicationStatus status;
    private LocalDateTime createdAt;
    private String rejectReason;
    private List<InterviewResponseDto> interviews;
}
