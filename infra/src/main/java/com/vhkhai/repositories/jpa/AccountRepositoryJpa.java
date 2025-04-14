package com.vhkhai.repositories.jpa;

import com.vhkhai.aggrerates.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepositoryJpa extends JpaRepository<Account, UUID> {
    Optional<Account> findByEmail(String email);
}
