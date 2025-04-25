package com.vhkhai.port.auth;

import com.vhkhai.aggrerates.account.Account;

public interface UserAuthentication {
    Account getAuthenticatedUser();
}
