package com.vhkhai.service;

import com.vhkhai.aggrerates.account.Account;
import com.vhkhai.dto.account.AccountRequestDto;
import com.vhkhai.dto.account.AccountResponseDto;
import com.vhkhai.dto.token.TokenResponseDto;

import java.util.UUID;

public interface AccountService {
    AccountResponseDto create(AccountRequestDto accountRequestDto);
    TokenResponseDto login(AccountRequestDto accountRequestDto);
    Account getAccountById(UUID id);
}
