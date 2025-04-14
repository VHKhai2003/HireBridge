package com.vhkhai.aggrerates.company;

import com.vhkhai.enumerations.JobPostingStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "job_postings")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class JobPosting {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;

    private String requirement;

    @Enumerated(EnumType.STRING)
    private JobPostingStatus status;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
}
