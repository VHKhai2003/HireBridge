package com.vhkhai.services;

import com.vhkhai.aggrerates.account.Account;
import com.vhkhai.aggrerates.candidate.Candidate;
import com.vhkhai.aggrerates.candidate.CandidateFollowing;
import com.vhkhai.aggrerates.company.Company;
import com.vhkhai.enumerations.AccountType;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class AccountCreatingService {
    public Candidate createCandidate(Account account) {
        return Candidate.builder()
                .accountId(account.getId())
                .email(account.getEmail())
                .candidateFollowing(new CandidateFollowing(Collections.emptyList()))
                .build();
    }

    public Company createCompany(Account account) {
        return Company.builder()
                .accountId(account.getId())
                .email(account.getEmail())
                .jobPostings(Collections.emptyList())
                .build();
    }
}
