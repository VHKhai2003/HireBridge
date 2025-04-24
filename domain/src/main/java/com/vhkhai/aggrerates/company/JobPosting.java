package com.vhkhai.aggrerates.company;

import com.vhkhai.enumerations.JobPostingStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "job_postings")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JobPosting {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;

    private String description;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private JobPostingStatus status;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    public JobPosting(String title, String description, Company company) {
        this.title = title;
        this.description = description;
        this.status = JobPostingStatus.OPENED;
        this.company = company;
    }
}
