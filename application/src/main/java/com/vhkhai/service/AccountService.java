package com.vhkhai.service;

import com.vhkhai.aggrerates.account.Account;
import com.vhkhai.dto.account.AccountRequestDto;
import com.vhkhai.dto.account.AccountResponseDto;

import java.util.UUID;

public interface AccountService {
    public AccountResponseDto create(AccountRequestDto accountRequestDto);
    public String login(AccountRequestDto accountRequestDto);
}
