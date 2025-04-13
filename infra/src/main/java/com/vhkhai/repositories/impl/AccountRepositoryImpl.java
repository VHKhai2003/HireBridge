package com.vhkhai.repositories.impl;

import com.vhkhai.aggrerates.account.Account;
import com.vhkhai.entities.account.AccountEntity;
import com.vhkhai.mappers.AccountMapper;
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
    private final AccountMapper accountMapper;

    @Override
    public Account create(Account account) {
        AccountEntity accountEntity = accountMapper.toAccountEntity(account);
        AccountEntity savedEntity = accountRepositoryJpa.save(accountEntity);
        return accountMapper.toAccount(savedEntity);
    }

    @Override
    public Account update(Account account) {
        return null;
    }

    @Override
    public void delete(UUID uuid) {

    }

    @Override
    public Optional<Account> getById(UUID uuid) {
        return Optional.empty();
    }
}
