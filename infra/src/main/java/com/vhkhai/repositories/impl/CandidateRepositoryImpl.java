package com.vhkhai.repositories.impl;

import com.vhkhai.aggrerates.candidate.Candidate;
import com.vhkhai.repositories.CandidateRepository;
import com.vhkhai.repositories.jpa.CandidateRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CandidateRepositoryImpl implements CandidateRepository {

    private final CandidateRepositoryJpa candidateRepositoryJpa;

    @Override
    public Candidate create(Candidate candidate) {
        return candidateRepositoryJpa.save(candidate);
    }

    @Override
    public Candidate update(Candidate entity) {
        return null;
    }

    @Override
    public void delete(UUID uuid) {

    }

    @Override
    public Optional<Candidate> getById(UUID uuid) {
        return Optional.empty();
    }
}
