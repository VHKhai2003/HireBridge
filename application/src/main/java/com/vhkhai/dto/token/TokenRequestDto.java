package com.vhkhai.dto.token;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenRequestDto {
    private String accessToken;
    private String refreshToken;
}
