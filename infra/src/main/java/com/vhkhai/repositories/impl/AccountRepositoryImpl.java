package com.vhkhai.repositories.impl;

import com.vhkhai.aggrerates.account.Account;
import com.vhkhai.repositories.AccountRepository;
import com.vhkhai.repositories.jpa.AccountRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class AccountRepositoryImpl implements AccountRepository {
    private final AccountRepositoryJpa accountRepositoryJpa;

    @Override
    public Account create(Account account) {
        return accountRepositoryJpa.save(account);
    }

    @Override
    public Account update(Account account) {
        return null;
    }

    @Override
    public void delete(UUID uuid) {

    }

    @Override
    public Optional<Account> findById(UUID id) {
        return accountRepositoryJpa.findById(id);
    }

    @Override
    public Optional<Account> getByEmail(String email) {
        return accountRepositoryJpa.findByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return accountRepositoryJpa.existsByEmail(email);
    }
}
