package com.vhkhai.dto.job_posting;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JobPostingRequestDto {
    @NotBlank(message = "Title is required")
    private String title;
    @NotBlank(message = "Requirement is required")
    private String requirement;
}
