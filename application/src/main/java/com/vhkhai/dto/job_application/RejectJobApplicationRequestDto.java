package com.vhkhai.dto.job_application;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RejectJobApplicationRequestDto {
    @NotBlank(message = "Reason is required")
    private String reason;
}
