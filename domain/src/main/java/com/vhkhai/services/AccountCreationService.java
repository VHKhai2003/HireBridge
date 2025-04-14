package com.vhkhai.services;

import com.vhkhai.aggrerates.account.Account;
import com.vhkhai.aggrerates.candidate.Candidate;
import com.vhkhai.aggrerates.company.Company;

public interface AccountCreationService {

    Candidate createCandidate(Account account);

    Company createCompany(Account account);

}
