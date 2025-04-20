package com.vhkhai.repositories;

import com.vhkhai.aggrerates.job_application.JobApplication;
import com.vhkhai.shared.BaseRepository;

import java.util.UUID;

public interface JobApplicationRepository extends BaseRepository<JobApplication, UUID> {
    boolean existsByCandidateIdAndJobPostingId(UUID candidateId, UUID jobPostingId);
}
