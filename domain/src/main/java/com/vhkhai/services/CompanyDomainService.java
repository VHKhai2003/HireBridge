package com.vhkhai.services;

import com.vhkhai.aggrerates.company.JobPosting;

import java.util.Optional;
import java.util.UUID;

public interface CompanyDomainService {
    Optional<JobPosting> getJobPostingById(UUID companyId, UUID jobPostingId);
}
