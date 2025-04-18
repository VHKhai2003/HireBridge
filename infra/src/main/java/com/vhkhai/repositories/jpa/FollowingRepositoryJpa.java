package com.vhkhai.repositories.jpa;

import com.vhkhai.aggrerates.candidate.Candidate;
import com.vhkhai.aggrerates.candidate.Following;
import com.vhkhai.aggrerates.candidate.FollowingId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FollowingRepositoryJpa
        extends JpaRepository<Following, FollowingId> {
    List<Following> findAllByCompanyId(UUID companyId);
}
