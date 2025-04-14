package com.vhkhai.aggrerates.job_application;

import com.vhkhai.aggrerates.company.JobPosting;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "recruitments")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Recruitment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "job_posting_id")
    private JobPosting jobPosting;

    @OneToMany(mappedBy = "recruitment")
    private List<JobApplication> applications;
}
