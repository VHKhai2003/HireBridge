package com.vhkhai.services;

import com.vhkhai.aggrerates.account.Account;
import com.vhkhai.aggrerates.candidate.Candidate;
import com.vhkhai.aggrerates.company.Company;
import org.springframework.stereotype.Service;

@Service
public class AccountCreatingService {
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
