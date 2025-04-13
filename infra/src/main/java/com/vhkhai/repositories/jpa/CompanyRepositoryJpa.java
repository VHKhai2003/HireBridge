package com.vhkhai.repositories.jpa;

import com.vhkhai.entities.company.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CompanyRepositoryJpa extends JpaRepository<CompanyEntity, UUID> {
}
