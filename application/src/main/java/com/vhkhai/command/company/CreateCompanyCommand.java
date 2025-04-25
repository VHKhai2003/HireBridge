package com.vhkhai.command.company;

import an.awesome.pipelinr.Command;
import com.vhkhai.aggrerates.account.Account;
import com.vhkhai.aggrerates.company.Company;
import com.vhkhai.dto.account.AccountResponseDto;
import com.vhkhai.enumerations.AccountType;
import com.vhkhai.exception.ApplicationErrorCode;
import com.vhkhai.exception.ApplicationException;
import com.vhkhai.mapper.AccountDtoMapper;
import com.vhkhai.port.auth.PwEncoder;
import com.vhkhai.repositories.AccountRepository;
import com.vhkhai.repositories.CompanyRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Getter
@NoArgsConstructor
@Setter
public class CreateCompanyCommand implements Command<AccountResponseDto> {
    @NotBlank(message = "Email is required")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email format")
    private String email;
    @NotBlank(message = "Password is required")
    private String password;
}

@Component
@RequiredArgsConstructor
class CreateCompanyCommandHandler implements Command.Handler<CreateCompanyCommand, AccountResponseDto> {

    private final AccountRepository accountRepository;
    private final CompanyRepository companyRepository;
    private final AccountDtoMapper mapper;
    private final PwEncoder pwEncoder;

    @Override
    @Transactional
    @CacheEvict(value = "company", key = "'all'")
    public AccountResponseDto handle(CreateCompanyCommand command) {
        // Check if the email already exists
        if (accountRepository.existsByEmail(command.getEmail())) {
            throw new ApplicationException(ApplicationErrorCode.EMAIL_ALREADY_EXISTS);
        }
        // Create a new account
        var savedAccount = accountRepository.create(
                new Account(command.getEmail(), pwEncoder.encode(command.getPassword()), AccountType.CANDIDATE)
        );
        companyRepository.create(new Company(savedAccount));
        return mapper.toAccountResponseDto(savedAccount);
    }
}
