package com.vhkhai.dto.job_application;

import com.vhkhai.enumerations.InterviewStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeInterviewStatusRequestDto {
    private InterviewStatus status;
}
