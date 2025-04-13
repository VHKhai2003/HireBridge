package com.vhkhai.repositories;

import com.vhkhai.aggrerates.account.Account;
import com.vhkhai.shared.BaseRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends BaseRepository<Account, UUID> {
    Optional<Account> getByEmail(String email);
}
