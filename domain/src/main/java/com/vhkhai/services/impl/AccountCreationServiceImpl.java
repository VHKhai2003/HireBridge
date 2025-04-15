package com.vhkhai.services.impl;

import com.vhkhai.aggrerates.account.Account;
import com.vhkhai.aggrerates.candidate.Candidate;
import com.vhkhai.aggrerates.company.Company;
import com.vhkhai.enumerations.AccountType;
import com.vhkhai.exception.DomainErrorCode;
import com.vhkhai.exception.DomainException;
import com.vhkhai.repositories.AccountRepository;
import com.vhkhai.repositories.CandidateRepository;
import com.vhkhai.repositories.CompanyRepository;
import com.vhkhai.services.AccountCreationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountCreationServiceImpl implements AccountCreationService {
    private final AccountRepository accountRepository;
    private final CandidateRepository candidateRepository;
    private final CompanyRepository companyRepository;

    @Override
    @Transactional
    public Account createAccount(String email, String password, AccountType type) {

        if (accountRepository.existsByEmail(email)) {
            throw new DomainException(DomainErrorCode.EMAIL_ALREADY_EXISTS);
        }

        if (type != AccountType.CANDIDATE && type != AccountType.COMPANY) {
            throw new DomainException(DomainErrorCode.ACCOUNT_TYPE_NOT_FOUND);
        }

        Account savedAccount = accountRepository.create(new Account(null, email, password, type));

        switch (savedAccount.getType()) {
            case CANDIDATE -> {
                candidateRepository.create(
                        Candidate.builder()
                                .account(savedAccount)
                                .email(savedAccount.getEmail())
                                .build()
                );
            }
            case COMPANY -> {
                companyRepository.create(
                        Company.builder()
                                .account(savedAccount)
                                .email(savedAccount.getEmail())
                                .build()
                );
            }
        }

        return savedAccount;
    }
}
