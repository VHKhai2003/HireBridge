package com.vhkhai.repositories.jpa;

import com.vhkhai.entities.candidate.CandidateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CandidateRepositoryJpa extends JpaRepository<CandidateEntity, UUID> {
}
