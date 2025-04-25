package com.vhkhai.service;

import com.vhkhai.aggrerates.account.Account;
import com.vhkhai.dto.account.AccountRequestDto;
import com.vhkhai.dto.token.RefreshTokenRequestDto;
import com.vhkhai.dto.token.TokenRequestDto;
import com.vhkhai.dto.token.TokenResponseDto;

import java.util.UUID;

public interface AuthService {
    TokenResponseDto login(AccountRequestDto accountRequestDto);
    void logout(TokenRequestDto tokenRequestDto);
    TokenResponseDto generateToken(UUID id);
    TokenResponseDto refreshToken(RefreshTokenRequestDto refreshTokenRequestDto);
    Account getAccountById(UUID id);
}
