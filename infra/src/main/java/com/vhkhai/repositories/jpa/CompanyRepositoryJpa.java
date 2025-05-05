package com.vhkhai.repositories.jpa;

import com.vhkhai.aggrerates.company.Company;
import com.vhkhai.aggrerates.company.JobPosting;
import com.vhkhai.enumerations.JobField;
import com.vhkhai.enumerations.JobLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepositoryJpa extends JpaRepository<Company, UUID> {
    Optional<Company> findByAccountId(UUID accountId);

    @Query("""
        SELECT jp 
        FROM JobPosting jp 
        WHERE (:field IS NULL OR jp.field = :field) 
          AND (:level IS NULL OR jp.level = :level)
          AND jp.status != 'CLOSED'
    """)
    Page<JobPosting> findJobPostingsByFieldAndLevel(
            @Param("field") JobField field,
            @Param("level") JobLevel level,
            Pageable pageable
    );

    Page<Company> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
