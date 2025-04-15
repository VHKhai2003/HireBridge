package com.vhkhai.dto.candidate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CandidateUpdateProfileRequestDto {
    @NotBlank(message = "Full name is required")
    private String fullName;
    @Pattern(regexp = "^\\+?[0-9]{9,15}$", message = "Phone number is invalid")
    private String phone;
}
