package com.vhkhai.services.impl;

import com.vhkhai.aggrerates.account.Account;
import com.vhkhai.aggrerates.candidate.Candidate;
import com.vhkhai.aggrerates.company.Company;
import com.vhkhai.services.AccountCreationService;
import org.springframework.stereotype.Service;

@Service
public class AccountCreationServiceImpl implements AccountCreationService {
    public Candidate createCandidate(Account account) {
        return Candidate.builder()
                .account(account)
                .email(account.getEmail())
                .build();
    }

    public Company createCompany(Account account) {
        return Company.builder()
                .account(account)
                .email(account.getEmail())
                .build();
    }
}
