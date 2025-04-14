package com.vhkhai.repositories.impl;

import com.vhkhai.aggrerates.company.Company;
import com.vhkhai.repositories.CompanyRepository;
import com.vhkhai.repositories.jpa.CompanyRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CompanyRepositoryImpl implements CompanyRepository {

    private final CompanyRepositoryJpa companyRepositoryJpa;

    @Override
    public Company create(Company company) {
        return companyRepositoryJpa.save(company);
    }

    @Override
    public Company update(Company company) {
        return null;
    }

    @Override
    public void delete(UUID uuid) {

    }

    @Override
    public Optional<Company> getById(UUID uuid) {
        return Optional.empty();
    }
}
