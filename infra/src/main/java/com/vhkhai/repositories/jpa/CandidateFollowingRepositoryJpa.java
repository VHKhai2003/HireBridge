package com.vhkhai.repositories.jpa;

import com.vhkhai.aggrerates.candidate.Following;
import com.vhkhai.aggrerates.candidate.FollowingId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateFollowingRepositoryJpa
        extends JpaRepository<Following, FollowingId> {
}
