package com.vhkhai.repositories.jpa;

import com.vhkhai.aggrerates.account.Account;
import com.vhkhai.aggrerates.candidate.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CandidateRepositoryJpa extends JpaRepository<Candidate, UUID> {
    Optional<Candidate> findByAccountId(UUID accountId);
}
