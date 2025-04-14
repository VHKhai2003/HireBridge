package com.vhkhai.repositories.jpa;

import com.vhkhai.aggrerates.candidate.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotificationRepositoryJpa extends JpaRepository<Notification, UUID> {
}
