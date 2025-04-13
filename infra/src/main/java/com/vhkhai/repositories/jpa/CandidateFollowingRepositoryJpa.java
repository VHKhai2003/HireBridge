package com.vhkhai.repositories.jpa;

import com.vhkhai.aggrerates.candidate.CandidateFollowing;
import com.vhkhai.entities.candidate.CandidateFollowingEntity;
import com.vhkhai.entities.candidate.CandidateFollowingId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CandidateFollowingRepositoryJpa
        extends JpaRepository<CandidateFollowingEntity, CandidateFollowingId> {
}
