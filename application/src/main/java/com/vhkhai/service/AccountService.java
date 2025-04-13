package com.vhkhai.service;

import com.vhkhai.dto.account.AccountRequestDto;
import com.vhkhai.dto.account.AccountResponseDto;

public interface AccountService {
    public AccountResponseDto create(AccountRequestDto accountRequestDto);
}
