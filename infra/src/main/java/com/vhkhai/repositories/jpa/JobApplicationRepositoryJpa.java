package com.vhkhai.repositories.jpa;

import com.vhkhai.entities.recruitment.JobApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JobApplicationRepositoryJpa extends JpaRepository<JobApplicationEntity, UUID> {
}
