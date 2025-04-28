package com.vhkhai.dto.job_posting;

import com.vhkhai.enumerations.JobPostingStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeStatusJobPostingRequestDto {
    private JobPostingStatus status;
}
