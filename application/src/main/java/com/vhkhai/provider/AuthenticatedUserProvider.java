package com.vhkhai.provider;

import com.vhkhai.aggrerates.account.Account;

public interface AuthenticatedUserProvider {
    Account getAuthenticatedUser();
}
