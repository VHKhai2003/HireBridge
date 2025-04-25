package com.vhkhai.command.candidate;

import an.awesome.pipelinr.Command;
import com.vhkhai.aggrerates.account.Account;
import com.vhkhai.aggrerates.candidate.Candidate;
import com.vhkhai.dto.account.AccountResponseDto;
import com.vhkhai.enumerations.AccountType;
import com.vhkhai.exception.ApplicationErrorCode;
import com.vhkhai.exception.ApplicationException;
import com.vhkhai.mapper.AccountDtoMapper;
import com.vhkhai.port.auth.PwEncoder;
import com.vhkhai.repositories.AccountRepository;
import com.vhkhai.repositories.CandidateRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterCandidateCommand implements Command<AccountResponseDto> {
    @NotBlank(message = "Email is required")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email format")
    private String email;
    @NotBlank(message = "Password is required")
    private String password;
}

@Service
@RequiredArgsConstructor
class RegisterCandidateCommandHandler implements Command.Handler<RegisterCandidateCommand, AccountResponseDto> {

    private final AccountRepository accountRepository;
    private final CandidateRepository candidateRepository;
    private final PwEncoder pwEncoder;
    private final AccountDtoMapper mapper;

    @Override
    @Transactional
    public AccountResponseDto handle(RegisterCandidateCommand command) {
        // Check if the email already exists
        if (accountRepository.existsByEmail(command.getEmail())) {
            throw new ApplicationException(ApplicationErrorCode.EMAIL_ALREADY_EXISTS);
        }
        // Create a new account
        var savedAccount = accountRepository.create(
                new Account(command.getEmail(), pwEncoder.encode(command.getPassword()), AccountType.CANDIDATE)
        );
        candidateRepository.create(new Candidate(savedAccount));
        return mapper.toAccountResponseDto(savedAccount);
    }
}
