package com.vhkhai.dto.job_posting;

import com.vhkhai.enumerations.JobField;
import com.vhkhai.enumerations.JobLevel;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobPostingRequestDto {
    @NotBlank(message = "Title is required")
    private String title;
    @NotBlank(message = "Description is required")
    private String description;
    private JobField field;
    private JobLevel level;
}
