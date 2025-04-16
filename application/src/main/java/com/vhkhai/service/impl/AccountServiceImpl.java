package com.vhkhai.service.impl;

import com.vhkhai.aggrerates.account.Account;
import com.vhkhai.dto.account.AccountRequestDto;
import com.vhkhai.dto.token.TokenResponseDto;
import com.vhkhai.exception.ApplicationErrorCode;
import com.vhkhai.exception.ApplicationException;
import com.vhkhai.port.Jwt;
import com.vhkhai.port.PwEncoder;
import com.vhkhai.repositories.AccountRepository;
import com.vhkhai.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final Jwt jwtProvider;
    private final PwEncoder passwordEncoder;


    @Override
    public TokenResponseDto login(AccountRequestDto accountRequestDto) {
        Account account = accountRepository.getByEmail(accountRequestDto.getEmail())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.ACCOUNT_NOT_FOUND));

        if (!passwordEncoder.matches(accountRequestDto.getPassword(), account.getPassword())) {
            throw new ApplicationException(ApplicationErrorCode.INCORRECT_EMAIL_OR_PASSWORD);
        }

        return TokenResponseDto.builder()
                .accessToken(jwtProvider.generateAccessToken(account.getId()))
                .refreshToken(jwtProvider.generateRefreshToken(account.getId()))
                .build();
    }

    @Override
    public Account getAccountById(UUID id) {
        return accountRepository.getById(id).orElse(null);
    }
}
