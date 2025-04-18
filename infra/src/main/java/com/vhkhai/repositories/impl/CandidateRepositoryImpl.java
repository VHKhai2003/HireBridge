package com.vhkhai.repositories.impl;

import com.vhkhai.aggrerates.candidate.Candidate;
import com.vhkhai.aggrerates.candidate.Following;
import com.vhkhai.repositories.CandidateRepository;
import com.vhkhai.repositories.jpa.FollowingRepositoryJpa;
import com.vhkhai.repositories.jpa.CandidateRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CandidateRepositoryImpl implements CandidateRepository {

    private final CandidateRepositoryJpa candidateRepositoryJpa;
    private final FollowingRepositoryJpa followingRepositoryJpa;

    @Override
    public Candidate create(Candidate candidate) {
        return candidateRepositoryJpa.save(candidate);
    }

    @Override
    public Candidate update(Candidate candidate) {
        return candidateRepositoryJpa.save(candidate);
    }

    @Override
    public void delete(UUID uuid) {
        candidateRepositoryJpa.deleteById(uuid);
    }

    @Override
    public Optional<Candidate> getById(UUID uuid) {
        return candidateRepositoryJpa.findById(uuid);
    }

    @Override
    public Optional<Candidate> findByAccountId(UUID accountId) {
        return candidateRepositoryJpa.findByAccountId(accountId);
    }

    @Override
    public List<Following> findAllByFollowedCompanyId(UUID companyId) {
        return followingRepositoryJpa.findAllByCompanyId(companyId);
    }
}
