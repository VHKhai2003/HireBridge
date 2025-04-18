package com.vhkhai.repositories;

import com.vhkhai.aggrerates.candidate.Candidate;
import com.vhkhai.aggrerates.candidate.Following;
import com.vhkhai.shared.BaseRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CandidateRepository extends BaseRepository<Candidate, UUID> {
    Optional<Candidate> findByAccountId(UUID accountId);
    List<Following> findAllByFollowedCompanyId(UUID companyId);
}
