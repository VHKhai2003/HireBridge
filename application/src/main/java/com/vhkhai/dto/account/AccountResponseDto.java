package com.vhkhai.dto.account;

import com.vhkhai.dto.token.TokenResponseDto;
import com.vhkhai.enumerations.AccountType;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@ToString
public class AccountResponseDto {
    private UUID id;
    private String email;
    private AccountType type;
    private TokenResponseDto token;
}
