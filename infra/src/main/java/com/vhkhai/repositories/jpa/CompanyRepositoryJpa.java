package com.vhkhai.repositories.jpa;

import com.vhkhai.aggrerates.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepositoryJpa extends JpaRepository<Company, UUID> {
    Optional<Company> findByAccountId(UUID accountId);
}
