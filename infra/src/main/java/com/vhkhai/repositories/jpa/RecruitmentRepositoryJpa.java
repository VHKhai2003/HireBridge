package com.vhkhai.repositories.jpa;

import com.vhkhai.aggrerates.job_application.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RecruitmentRepositoryJpa extends JpaRepository<Recruitment, UUID> {
}
