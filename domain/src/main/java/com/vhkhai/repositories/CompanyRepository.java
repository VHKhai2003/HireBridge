package com.vhkhai.repositories;

import com.vhkhai.aggrerates.company.Company;
import com.vhkhai.aggrerates.company.JobPosting;
import com.vhkhai.enumerations.JobField;
import com.vhkhai.enumerations.JobLevel;
import com.vhkhai.shared.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository extends BaseRepository<Company, UUID> {
    Optional<Company> findByAccountId(UUID accountId);
    Page<Company> findAll(Pageable pageable);
    Page<Company> findByNameContaining(String name, Pageable pageable);
    Page<JobPosting> findJobPostingsByFieldAndLevelWithPagination(
            JobField field, JobLevel level, Pageable pageable);
}
