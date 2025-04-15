package com.vhkhai.repositories;

import com.vhkhai.aggrerates.candidate.Candidate;
import com.vhkhai.shared.BaseRepository;

import java.util.Optional;
import java.util.UUID;

public interface CandidateRepository extends BaseRepository<Candidate, UUID> {
    Optional<Candidate> findByAccountId(UUID accountId);
}
