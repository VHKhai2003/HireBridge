package com.vhkhai.aggrerates.candidate;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "notifications")
@DynamicInsert
@DynamicUpdate
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;

    private String content;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    public Notification(String title, String content, Candidate candidate) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.content = content;
        this.candidate = candidate;
    }
}
