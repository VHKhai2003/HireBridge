package com.vhkhai.command.company;

import an.awesome.pipelinr.Command;
import com.vhkhai.dto.account.AccountResponseDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class RegisterCompanyCommand implements Command<AccountResponseDto> {
    private final String email;
    private final String password;
}
