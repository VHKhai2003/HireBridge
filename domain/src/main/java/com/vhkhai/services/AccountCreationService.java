package com.vhkhai.services;

import com.vhkhai.aggrerates.account.Account;
import com.vhkhai.aggrerates.candidate.Candidate;
import com.vhkhai.aggrerates.company.Company;
import com.vhkhai.enumerations.AccountType;

public interface AccountCreationService {

    Account createAccount(String email, String password, AccountType type);

}
