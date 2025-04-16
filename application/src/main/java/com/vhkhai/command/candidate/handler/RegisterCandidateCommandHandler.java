package com.vhkhai.command.candidate.handler;

import an.awesome.pipelinr.Command;
import com.vhkhai.aggrerates.account.Account;
import com.vhkhai.aggrerates.candidate.Candidate;
import com.vhkhai.command.candidate.RegisterCandidateCommand;
import com.vhkhai.dto.account.AccountResponseDto;
import com.vhkhai.dto.token.TokenResponseDto;
import com.vhkhai.enumerations.AccountType;
import com.vhkhai.exception.ApplicationErrorCode;
import com.vhkhai.exception.ApplicationException;
import com.vhkhai.mapper.AccountDtoMapper;
import com.vhkhai.port.Jwt;
import com.vhkhai.port.PwEncoder;
import com.vhkhai.repositories.AccountRepository;
import com.vhkhai.repositories.CandidateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisterCandidateCommandHandler implements Command.Handler<RegisterCandidateCommand, AccountResponseDto> {

    private final AccountRepository accountRepository;
    private final CandidateRepository candidateRepository;
    private final PwEncoder pwEncoder;
    private final Jwt jwt;
    private final AccountDtoMapper mapper;

    @Override
    public AccountResponseDto handle(RegisterCandidateCommand command) {
        // Check if the email already exists
        if (accountRepository.existsByEmail(command.getEmail())) {
            throw new ApplicationException(ApplicationErrorCode.EMAIL_ALREADY_EXISTS);
        }
        // Create a new account
        Account savedAccount = accountRepository.create(Account.builder()
                        .email(command.getEmail())
                        .password(pwEncoder.encode(command.getPassword()))
                        .type(AccountType.CANDIDATE)
                        .build());
        // Create a new candidate
        candidateRepository.create(Candidate.builder()
                        .email(savedAccount.getEmail())
                        .account(savedAccount)
                        .build());

        // genreate access token and refresh token
        String accessToken = jwt.generateAccessToken(savedAccount.getId());
        String refreshToken = jwt.generateRefreshToken(savedAccount.getId());
        AccountResponseDto accountResponseDto = mapper.toAccountResponseDto(savedAccount);
        accountResponseDto.setToken(new TokenResponseDto(accessToken, refreshToken));

        return accountResponseDto;
    }
}
