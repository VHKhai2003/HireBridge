package com.vhkhai.repositories.impl;

import com.vhkhai.aggrerates.company.Company;
import com.vhkhai.aggrerates.company.JobPosting;
import com.vhkhai.enumerations.JobField;
import com.vhkhai.enumerations.JobLevel;
import com.vhkhai.repositories.CompanyRepository;
import com.vhkhai.repositories.jpa.CompanyRepositoryJpa;
import com.vhkhai.repositories.jpa.JobPostingRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
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
    public Optional<Company> findById(UUID uuid) {
        return companyRepositoryJpa.findById(uuid);
    }

    @Override
    public Optional<Company> findByAccountId(UUID accountId) {
        return companyRepositoryJpa.findByAccountId(accountId);
    }

    @Override
    public Page<Company> findAll(Pageable pageable) {
        return companyRepositoryJpa.findAll(pageable);
    }

    @Override
    public Page<Company> findByNameContaining(String name, Pageable pageable) {
        return companyRepositoryJpa.findByNameContainingIgnoreCase(name, pageable);
    }


    @Override
    public Page<JobPosting> findJobPostingsByFieldAndLevelWithPagination(
            JobField field, JobLevel level, Pageable pageable) {
        return companyRepositoryJpa.findJobPostingsByFieldAndLevel(field, level, pageable);
    }

}
