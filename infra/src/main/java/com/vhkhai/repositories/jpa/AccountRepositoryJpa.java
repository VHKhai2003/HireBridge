package com.vhkhai.repositories.jpa;

import com.vhkhai.entities.account.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepositoryJpa extends JpaRepository<AccountEntity, UUID> {
}
