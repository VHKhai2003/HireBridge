package com.vhkhai.repositories.impl;

import com.vhkhai.aggrerates.job_application.JobApplication;
import com.vhkhai.repositories.JobApplicationRepository;
import com.vhkhai.repositories.jpa.JobApplicationRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class JobApplicationRepositoryImpl implements JobApplicationRepository {

    private final JobApplicationRepositoryJpa jpaRepository;

    @Override
    public boolean existsByCandidateIdAndJobPostingId(UUID candidateId, UUID jobPostingId) {
        return jpaRepository.existsByCandidateIdAndJobPostingId(candidateId, jobPostingId);
    }

    @Override
    public JobApplication create(JobApplication entity) {
        return jpaRepository.save(entity);
    }

    @Override
    public JobApplication update(JobApplication entity) {
        return jpaRepository.save(entity);
    }

    @Override
    public void delete(UUID uuid) {
        jpaRepository.deleteById(uuid);
    }

    @Override
    public Optional<JobApplication> getById(UUID uuid) {
        return jpaRepository.findById(uuid);
    }
}
