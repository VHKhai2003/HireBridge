package com.vhkhai.repositories.jpa;

import com.vhkhai.aggrerates.job_application.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JobApplicationRepositoryJpa extends JpaRepository<JobApplication, UUID> {
    boolean existsByCandidateIdAndJobPostingId(UUID candidateId, UUID jobPostingId);
}
