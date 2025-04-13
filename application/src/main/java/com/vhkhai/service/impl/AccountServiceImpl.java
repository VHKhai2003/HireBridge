package com.vhkhai.service.impl;

import com.vhkhai.aggrerates.account.Account;
import com.vhkhai.dto.account.AccountRequestDto;
import com.vhkhai.dto.account.AccountResponseDto;
import com.vhkhai.mapper.AccountDtoMapper;
import com.vhkhai.repositories.AccountRepository;
import com.vhkhai.repositories.CandidateRepository;
import com.vhkhai.repositories.CompanyRepository;
import com.vhkhai.service.AccountService;
import com.vhkhai.services.AccountCreatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final ApplicationEventPublisher applicationEventPublisher;
    private final AccountRepository accountRepository;
    private final CandidateRepository candidateRepository;
    private final CompanyRepository companyRepository;
    private final AccountCreatingService accountCreatingService;
    private final AccountDtoMapper mapper;


    @Override
    @Transactional
    public AccountResponseDto create(AccountRequestDto accountRequestDto) {
        Account account = mapper.toAccount(accountRequestDto);
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
}
