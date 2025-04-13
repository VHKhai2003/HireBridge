package com.vhkhai.repositories.jpa;

import com.vhkhai.entities.recruitment.RecruitmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RecruitmentRepositoryJpa extends JpaRepository<RecruitmentEntity, UUID> {
}
