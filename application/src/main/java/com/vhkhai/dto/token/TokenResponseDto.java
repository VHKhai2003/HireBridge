package com.vhkhai.dto.token;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class TokenResponseDto {
    private String accessToken;
    private String refreshToken;
}
