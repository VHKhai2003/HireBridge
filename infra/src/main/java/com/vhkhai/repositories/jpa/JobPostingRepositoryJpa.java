package com.vhkhai.repositories.jpa;

import com.vhkhai.aggrerates.company.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JobPostingRepositoryJpa extends JpaRepository<JobPosting, UUID> {
}
