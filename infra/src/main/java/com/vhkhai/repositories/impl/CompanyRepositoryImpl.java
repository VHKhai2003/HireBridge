package com.vhkhai.repositories.impl;

import com.vhkhai.aggrerates.company.Company;
import com.vhkhai.entities.company.CompanyEntity;
import com.vhkhai.mappers.CompanyMapper;
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
    private final CompanyMapper mapper;

    @Override
    public Company create(Company company) {
        CompanyEntity entity = mapper.toCompanyEntity(company);
        CompanyEntity savedEntity = companyRepositoryJpa.save(entity);
        return mapper.toCompany(savedEntity);
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
