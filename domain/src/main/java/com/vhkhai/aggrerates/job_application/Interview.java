package com.vhkhai.aggrerates.job_application;

import com.vhkhai.enumerations.InterviewStatus;
import com.vhkhai.exception.DomainErrorCode;
import com.vhkhai.exception.DomainException;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "interviews")
public class Interview {
    @Id
    private UUID id;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    private Integer duration;

    @Column(name = "is_online")
    private Boolean isOnline;

    private String link;

    @Enumerated(EnumType.STRING)
    private InterviewStatus status;

    @ManyToOne
    @JoinColumn(name = "job_application_id")
    private JobApplication jobApplication;

    public void cancel() {
        if (this.status != InterviewStatus.SCHEDULED) {
            throw new DomainException(DomainErrorCode.CANNOT_CANCEL_INTERVIEW);
        }
        this.status = InterviewStatus.CANCELLED;
    }

    public void complete() {
        if (this.status != InterviewStatus.HAPPENING) {
            throw new DomainException(DomainErrorCode.CANNOT_COMPLETE_INTERVIEW);
        }
        this.status = InterviewStatus.COMPLETED;
    }

    public void conduct() {
        if(this.status != InterviewStatus.SCHEDULED) {
            throw new DomainException(DomainErrorCode.CANNOT_CONDUCT_INTERVIEW);
        }
        this.status = InterviewStatus.HAPPENING;
    }

    public void updateInterview(LocalDateTime startTime, Integer duration, boolean isOnline, String link) {
        if(this.status != InterviewStatus.SCHEDULED
                || LocalDateTime.now().isAfter(this.startTime.minusHours(1))) {
            throw new DomainException(DomainErrorCode.CANNOT_UPDATE_INTERVIEW);
        }
        if(LocalDateTime.now().isAfter(startTime.minusHours(1))) {
            throw new DomainException(DomainErrorCode.INVALID_INTERVIEW_TIME);
        }
        if(duration <= 0) {
            throw new DomainException(DomainErrorCode.INVALID_INTERVIEW_DURATION);
        }
        if(isOnline && (link == null || link.isBlank())) {
            throw new DomainException(DomainErrorCode.INVALID_INTERVIEW_LINK);
        }
        this.startTime = startTime;
        this.duration = duration;
        this.isOnline = isOnline;
        this.link = link;
    }

}
