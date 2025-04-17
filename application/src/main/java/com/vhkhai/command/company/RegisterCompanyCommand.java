package com.vhkhai.command.company;

import an.awesome.pipelinr.Command;
import com.vhkhai.aggrerates.account.Account;
import com.vhkhai.aggrerates.company.Company;
import com.vhkhai.dto.account.AccountResponseDto;
import com.vhkhai.dto.token.TokenResponseDto;
import com.vhkhai.enumerations.AccountType;
import com.vhkhai.exception.ApplicationErrorCode;
import com.vhkhai.exception.ApplicationException;
import com.vhkhai.mapper.AccountDtoMapper;
import com.vhkhai.port.Jwt;
import com.vhkhai.port.PwEncoder;
import com.vhkhai.repositories.AccountRepository;
import com.vhkhai.repositories.CompanyRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Getter
public class RegisterCompanyCommand implements Command<AccountResponseDto> {
    private final String email;
    private final String password;
}

@Component
@RequiredArgsConstructor
class RegisterCompanyCommandHandler implements Command.Handler<RegisterCompanyCommand, AccountResponseDto> {

    private final AccountRepository accountRepository;
    private final CompanyRepository companyRepository;
    private final Jwt jwt;
    private final AccountDtoMapper mapper;
    private final PwEncoder pwEncoder;

    @Override
    public AccountResponseDto handle(RegisterCompanyCommand command) {
        // Check if the email already exists
        if (accountRepository.existsByEmail(command.getEmail())) {
            throw new ApplicationException(ApplicationErrorCode.EMAIL_ALREADY_EXISTS);
        }
        // Create a new account
        Account savedAccount = accountRepository.create(Account.builder()
                .email(command.getEmail())
                .password(pwEncoder.encode(command.getPassword()))
                .type(AccountType.COMPANY)
                .build());
        // Create a new company
        companyRepository.create(Company.builder()
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
