package com.vhkhai.repositories.impl;

import com.vhkhai.aggrerates.candidate.Candidate;
import com.vhkhai.entities.candidate.CandidateEntity;
import com.vhkhai.mappers.CandidateMapper;
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
    private final CandidateMapper mapper;

    @Override
    public Candidate create(Candidate candidate) {
        CandidateEntity entity = mapper.toCandidateEntity(candidate);
        CandidateEntity savedEntity = candidateRepositoryJpa.save(entity);
        return mapper.toCandidate(savedEntity);
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
