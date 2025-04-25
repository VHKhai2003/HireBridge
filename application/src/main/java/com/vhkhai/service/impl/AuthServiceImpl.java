package com.vhkhai.service.impl;

import com.vhkhai.aggrerates.account.Account;
import com.vhkhai.dto.account.AccountRequestDto;
import com.vhkhai.dto.token.RefreshTokenRequestDto;
import com.vhkhai.dto.token.TokenRequestDto;
import com.vhkhai.dto.token.TokenResponseDto;
import com.vhkhai.exception.ApplicationErrorCode;
import com.vhkhai.exception.ApplicationException;
import com.vhkhai.port.auth.Jwt;
import com.vhkhai.port.auth.PwEncoder;
import com.vhkhai.port.cache.CachingConstant;
import com.vhkhai.port.cache.CachingService;
import com.vhkhai.repositories.AccountRepository;
import com.vhkhai.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AccountRepository accountRepository;
    private final Jwt jwtProvider;
    private final PwEncoder passwordEncoder;
    private final CachingService cachingService;


    @Override
    public TokenResponseDto login(AccountRequestDto accountRequestDto) {
        Account account = accountRepository.getByEmail(accountRequestDto.getEmail())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.ACCOUNT_NOT_FOUND));

        if (!passwordEncoder.matches(accountRequestDto.getPassword(), account.getPassword())) {
            throw new ApplicationException(ApplicationErrorCode.INCORRECT_EMAIL_OR_PASSWORD);
        }

        return generateToken(account.getId());
    }

    @Override
    public void logout(TokenRequestDto tokenRequestDto) {
        String accessToken = tokenRequestDto.getAccessToken();
        String refreshToken = tokenRequestDto.getRefreshToken();
        Date now = new Date();
        String accessTokenKey = CachingConstant.BLACKLIST_ACCESS_TOKEN.name() + "::" + accessToken;
        String refreshTokenKey = CachingConstant.BLACKLIST_REFRESH_TOKEN.name() + "::" + refreshToken;
        if(jwtProvider.isAccessToken(accessToken) && !cachingService.hasKey(accessTokenKey)){
            Date expirationDateAccessToken = jwtProvider.getExpirationDate(accessToken);
            cachingService.set(accessTokenKey, true, expirationDateAccessToken.getTime() - now.getTime());
        }
        if(jwtProvider.isRefreshToken(refreshToken) && !cachingService.hasKey(refreshTokenKey)){
            Date expirationDateRefreshToken = jwtProvider.getExpirationDate(refreshToken);
            cachingService.set(refreshTokenKey, true, expirationDateRefreshToken.getTime() - now.getTime());
        }
    }

    @Override
    public TokenResponseDto generateToken(UUID id) {
        return TokenResponseDto.builder()
                .accessToken(jwtProvider.generateAccessToken(id))
                .refreshToken(jwtProvider.generateRefreshToken(id))
                .build();
    }

    @Override
    public TokenResponseDto refreshToken(RefreshTokenRequestDto refreshTokenRequestDto) {
        String refreshToken = refreshTokenRequestDto.getRefreshToken();
        String key = CachingConstant.BLACKLIST_REFRESH_TOKEN.name() + "::" + refreshToken;
        if(jwtProvider.isRefreshToken(refreshToken) && !cachingService.hasKey(key)){
            UUID id = jwtProvider.getAccountIdFromToken(refreshToken);
            Date expirationTime = jwtProvider.getExpirationDate(refreshToken);
            Date now = new Date();
            cachingService.set(key, true, expirationTime.getTime() - now.getTime());
            return generateToken(id);
        }
        throw new ApplicationException(ApplicationErrorCode.INVALID_REFRESH_TOKEN);
    }

    @Override
    public Account getAccountById(UUID id) {
        return accountRepository.findById(id).orElse(null);
    }
}
