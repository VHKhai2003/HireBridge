package com.vhkhai.aggrerates.company;

import com.vhkhai.enumerations.JobField;
import com.vhkhai.enumerations.JobLevel;
import com.vhkhai.enumerations.JobPostingStatus;
import com.vhkhai.exception.DomainErrorCode;
import com.vhkhai.exception.DomainException;
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

    @Enumerated(EnumType.STRING)
    private JobField field;

    @Enumerated(EnumType.STRING)
    private JobLevel level;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private JobPostingStatus status;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    public JobPosting(String title, String description, JobField field, JobLevel level, Company company) {
        this.title = title;
        this.description = description;
        this.field = field;
        this.level = level;
        this.status = JobPostingStatus.OPENED;
        this.company = company;
    }

    public void changeStatus(JobPostingStatus status) {
        switch (status) {
            case CLOSED -> close();
            case REOPENED -> reopen();
            default -> throw new DomainException(DomainErrorCode.INVALID_JOB_POSTING_NEW_STATUS);
        }
    }

    private void close() {
        if(this.status == JobPostingStatus.CLOSED)
            throw new DomainException(DomainErrorCode.JOB_POSTING_ALREADY_CLOSED);
        this.status = JobPostingStatus.CLOSED;
    }

    private void reopen() {
        if(this.status != JobPostingStatus.CLOSED)
            throw new DomainException(DomainErrorCode.JOB_POSTING_NOT_CLOSED);
        this.status = JobPostingStatus.REOPENED;
    }
}
