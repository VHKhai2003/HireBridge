package com.vhkhai.repositories.impl;

import com.vhkhai.aggrerates.company.Company;
import com.vhkhai.aggrerates.company.JobPosting;
import com.vhkhai.repositories.CompanyRepository;
import com.vhkhai.repositories.jpa.CompanyRepositoryJpa;
import com.vhkhai.repositories.jpa.JobPostingRepositoryJpa;
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
        return companyRepositoryJpa.save(company);
    }

    @Override
    public void delete(UUID uuid) {
        companyRepositoryJpa.deleteById(uuid);
    }

    @Override
    public Optional<Company> getById(UUID uuid) {
        return companyRepositoryJpa.findById(uuid);
    }

    @Override
    public Optional<Company> findByAccountId(UUID accountId) {
        return companyRepositoryJpa.findByAccountId(accountId);
    }

}
