package com.vhkhai.command;

import an.awesome.pipelinr.Command;
import com.vhkhai.dto.account.AccountResponseDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class RegisterCandidateCommand implements Command<AccountResponseDto> {
    private final String email;
    private final String password;
}
