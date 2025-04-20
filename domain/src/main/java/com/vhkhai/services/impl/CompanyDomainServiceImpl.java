package com.vhkhai.services.impl;

import com.vhkhai.aggrerates.company.JobPosting;
import com.vhkhai.repositories.CompanyRepository;
import com.vhkhai.services.CompanyDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CompanyDomainServiceImpl implements CompanyDomainService {

    private final CompanyRepository companyRepository;

    @Override
    public Optional<JobPosting> getJobPostingById(UUID companyId, UUID jobPostingId) {
        return Optional.empty();
    }
}
