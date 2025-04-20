package com.vhkhai.dto.job_application;

import com.vhkhai.dto.candidate.CandidateResponseDto;
import com.vhkhai.dto.job_posting.JobPostingResponseDto;
import com.vhkhai.enumerations.ApplicationStatus;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobApplicationResponseDto {
    private CandidateResponseDto candidate;
    private JobPostingResponseDto jobPosting;
    private ApplicationStatus status;
    private List<InterviewResponseDto> interviews;
}
