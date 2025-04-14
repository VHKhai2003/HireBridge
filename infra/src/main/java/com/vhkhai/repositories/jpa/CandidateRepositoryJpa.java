package com.vhkhai.repositories.jpa;

import com.vhkhai.aggrerates.candidate.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CandidateRepositoryJpa extends JpaRepository<Candidate, UUID> {
}
