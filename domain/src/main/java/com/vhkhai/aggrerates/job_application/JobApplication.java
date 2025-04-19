package com.vhkhai.aggrerates.job_application;

import com.vhkhai.aggrerates.candidate.Candidate;
import com.vhkhai.aggrerates.company.JobPosting;
import com.vhkhai.enumerations.ApplicationStatus;
import com.vhkhai.enumerations.InterviewStatus;
import com.vhkhai.exception.DomainErrorCode;
import com.vhkhai.exception.DomainException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "job_applications")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JobApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    @ManyToOne
    @JoinColumn(name = "job_posting_id")
    private JobPosting jobPosting;

    @OneToMany(mappedBy = "jobApplication", cascade = CascadeType.ALL)
    private List<Interview> interviews;


    public void reject() {
        if(this.status == ApplicationStatus.OFFERED || this.status == ApplicationStatus.REJECTED) return;
        this.status = ApplicationStatus.REJECTED;
    }

    public void offer() {
        if(this.status == ApplicationStatus.OFFERED || this.status == ApplicationStatus.REJECTED) return;
        this.status = ApplicationStatus.OFFERED;
    }

    public void addInterview(LocalDateTime startTime, Integer duration, boolean isOnline, String link) {

        if(this.status != ApplicationStatus.PENDING) {
            throw new DomainException(DomainErrorCode.CANNOT_SCHEDULE_INTERVIEW);
        }

        // check time
        if(startTime.isBefore(LocalDateTime.now())) {
            throw new DomainException(DomainErrorCode.INVALID_INTERVIEW_TIME);
        }

        // cannot schedule interview if there is another incompleted interview
        boolean hasInterview = interviews.stream()
                .anyMatch(interview -> interview.getStatus() != InterviewStatus.COMPLETED
                        && interview.getStatus() != InterviewStatus.CANCELLED);
        if(hasInterview) {
            throw new DomainException(DomainErrorCode.CANNOT_SCHEDULE_INTERVIEW);
        }

        Interview interview = new Interview(
                UUID.randomUUID(),
                startTime,
                duration,
                isOnline,
                link,
                InterviewStatus.SCHEDULED,
                this
        );
        this.interviews.add(interview);
    }


}
