package com.vhkhai.dto.token;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RefreshTokenRequestDto {
    @NotBlank(message = "Refresh token must not be blank")
    private String refreshToken;
}
