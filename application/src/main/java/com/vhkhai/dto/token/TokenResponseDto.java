package com.vhkhai.dto.token;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class TokenResponseDto {
    @NotBlank(message = "Access token is required")
    private String accessToken;
    @NotBlank(message = "Refresh token is required")
    private String refreshToken;
}
