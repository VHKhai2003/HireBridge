package com.vhkhai.aggrerates.job_application;

import com.vhkhai.aggrerates.candidate.Candidate;
import com.vhkhai.aggrerates.company.JobPosting;
import com.vhkhai.enumerations.ApplicationStatus;
import com.vhkhai.enumerations.InterviewStatus;
import com.vhkhai.events.InterviewCreationEvent;
import com.vhkhai.events.InterviewReScheduleEvent;
import com.vhkhai.events.JobOfferEvent;
import com.vhkhai.events.JobRejectEvent;
import com.vhkhai.exception.DomainErrorCode;
import com.vhkhai.exception.DomainException;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "job_applications")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@DynamicInsert
public class JobApplication extends AbstractAggregateRoot<JobApplication> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    @Column(name = "reject_reason")
    private String rejectReason;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    @ManyToOne
    @JoinColumn(name = "job_posting_id")
    private JobPosting jobPosting;

    @OneToMany(mappedBy = "jobApplication", cascade = CascadeType.ALL)
    private List<Interview> interviews;

    public JobApplication(Candidate candidate, JobPosting jobPosting) {
        this.id = UUID.randomUUID();
        this.candidate = candidate;
        this.jobPosting = jobPosting;
        this.status = ApplicationStatus.PENDING;
        this.interviews = List.of();
    }


    public void reject(String reason) {
        if(hasInterview()) {
            throw new DomainException(DomainErrorCode.CANNOT_REJECT_WHEN_HAVING_INTERVIEW);
        }
        if(reason.isBlank()) {
            throw new DomainException(DomainErrorCode.REJECT_REASON_IS_REQUIRED);
        }
        if(this.status == ApplicationStatus.OFFERED || this.status == ApplicationStatus.REJECTED){
            throw new DomainException(DomainErrorCode.JOB_APPLICATION_OFFERED_OR_REJECTED);
        }
        this.status = ApplicationStatus.REJECTED;
        this.rejectReason = reason;
        registerEvent(new JobRejectEvent(this));
    }

    public void offer() {
        if(hasInterview()) {
            throw new DomainException(DomainErrorCode.CANNOT_OFFER_WHEN_HAVING_INTERVIEW);
        }
        if(this.status == ApplicationStatus.OFFERED || this.status == ApplicationStatus.REJECTED){
            throw new DomainException(DomainErrorCode.JOB_APPLICATION_OFFERED_OR_REJECTED);
        }
        this.status = ApplicationStatus.OFFERED;
        registerEvent(new JobOfferEvent(this));
    }

    private boolean hasInterview() {
        return interviews.stream()
                .anyMatch(interview -> interview.getStatus() != InterviewStatus.COMPLETED
                        && interview.getStatus() != InterviewStatus.CANCELLED);
    }

    public Interview addInterview(LocalDateTime startTime, Integer duration, boolean isOnline, String link) {

        if(this.status != ApplicationStatus.PENDING) {
            throw new DomainException(DomainErrorCode.CANNOT_SCHEDULE_INTERVIEW);
        }

        // check time
        if(LocalDateTime.now().isAfter(startTime.minusHours(1))) {
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
        registerEvent(new InterviewCreationEvent(interview, this.candidate));
        return interview;
    }

    public Interview getInterview(UUID id) {
        return interviews.stream()
                .filter(interview -> interview.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new DomainException(DomainErrorCode.INTERVIEW_NOT_FOUND));
    }

    public void updateInterview(UUID interviewId, LocalDateTime startTime, Integer duration, boolean isOnline, String link) {
        Interview interview = this.getInterview(interviewId);
        if(interview == null) {
            throw new DomainException(DomainErrorCode.INTERVIEW_NOT_FOUND);
        }
        boolean sendNotification = !startTime.isEqual(interview.getStartTime());
        interview.updateInterview(startTime, duration, isOnline, link);
        if(sendNotification) {
            registerEvent(new InterviewReScheduleEvent(interview, this.candidate));
        }
    }

    public void cancelInterview(Interview interview) {
        if(interview == null) {
            throw new DomainException(DomainErrorCode.INTERVIEW_NOT_FOUND);
        }
        interview.cancel();
    }

    public void conductInterview(Interview interview) {
        if(interview == null) {
            throw new DomainException(DomainErrorCode.INTERVIEW_NOT_FOUND);
        }
        interview.conduct();
    }

    public void completeInterview(Interview interview) {
        if(interview == null) {
            throw new DomainException(DomainErrorCode.INTERVIEW_NOT_FOUND);
        }
        interview.complete();
    }

}
