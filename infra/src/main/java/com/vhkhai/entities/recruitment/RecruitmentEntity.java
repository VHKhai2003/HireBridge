package com.vhkhai.entities.recruitment;

import com.vhkhai.entities.company.JobPostingEntity;
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
public class RecruitmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "job_posting_id")
    private JobPostingEntity jobPosting;

    @OneToMany(mappedBy = "recruitment")
    private List<JobApplicationEntity> applications;
}
