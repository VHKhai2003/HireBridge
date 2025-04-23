package com.vhkhai.command.company;

import an.awesome.pipelinr.Command;
import com.vhkhai.aggrerates.account.Account;
import com.vhkhai.aggrerates.company.Company;
import com.vhkhai.dto.account.AccountResponseDto;
import com.vhkhai.enumerations.AccountType;
import com.vhkhai.exception.ApplicationErrorCode;
import com.vhkhai.exception.ApplicationException;
import com.vhkhai.mapper.AccountDtoMapper;
import com.vhkhai.port.PwEncoder;
import com.vhkhai.repositories.AccountRepository;
import com.vhkhai.repositories.CompanyRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Getter
public class CreateCompanyCommand implements Command<AccountResponseDto> {
    @NotBlank(message = "Email is required")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email format")
    private final String email;
    @NotBlank(message = "Password is required")
    private final String password;
}

@Component
@RequiredArgsConstructor
class CreateCompanyCommandHandler implements Command.Handler<CreateCompanyCommand, AccountResponseDto> {

    private final AccountRepository accountRepository;
    private final CompanyRepository companyRepository;
    private final AccountDtoMapper mapper;
    private final PwEncoder pwEncoder;

    @Override
    public AccountResponseDto handle(CreateCompanyCommand command) {
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

        return mapper.toAccountResponseDto(savedAccount);
    }
}
