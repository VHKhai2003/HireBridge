package com.vhkhai.repositories.jpa;

import com.vhkhai.entities.company.JobPostingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JobPostingRepositoryJpa extends JpaRepository<JobPostingEntity, UUID> {
}
