package com.vhkhai.service.impl;

import com.vhkhai.aggrerates.account.Account;
import com.vhkhai.dto.account.AccountRequestDto;
import com.vhkhai.dto.account.AccountResponseDto;
import com.vhkhai.mapper.AccountDtoMapper;
import com.vhkhai.provider.JwtProvider;
import com.vhkhai.provider.PasswordEncoderProvider;
import com.vhkhai.repositories.AccountRepository;
import com.vhkhai.repositories.CandidateRepository;
import com.vhkhai.repositories.CompanyRepository;
import com.vhkhai.service.AccountService;
import com.vhkhai.services.AccountCreatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final ApplicationEventPublisher applicationEventPublisher;
    private final AccountRepository accountRepository;
    private final CandidateRepository candidateRepository;
    private final CompanyRepository companyRepository;
    private final AccountCreatingService accountCreatingService;
    private final AccountDtoMapper mapper;
    private final JwtProvider jwtProvider;
    private final PasswordEncoderProvider passwordEncoder;


    @Override
    @Transactional
    public AccountResponseDto create(AccountRequestDto accountRequestDto) {
        // check if email already exists

        Account account = mapper.toAccount(accountRequestDto);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        Account savedAccount = accountRepository.create(account);

        switch (savedAccount.getType()) {
            case CANDIDATE -> {
                candidateRepository.create(accountCreatingService.createCandidate(savedAccount));
            }
            case COMPANY -> {
                companyRepository.create(accountCreatingService.createCompany(savedAccount));
            }
            default -> throw new IllegalArgumentException("Invalid account type: " + savedAccount.getType());
        }

        AccountResponseDto accountResponseDto = mapper.toAccountResponseDto(savedAccount);
        return accountResponseDto;
    }

    @Override
    public String login(AccountRequestDto accountRequestDto) {
        Account account = accountRepository.getByEmail(accountRequestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        if (!passwordEncoder.matches(accountRequestDto.getPassword(), account.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }

        return jwtProvider.generateToken(account, "access");
    }
}
