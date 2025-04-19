package com.vhkhai.aggrerates.job_application;

import com.vhkhai.enumerations.InterviewStatus;
import com.vhkhai.exception.DomainErrorCode;
import com.vhkhai.exception.DomainException;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Interview {
    private UUID id;
    private LocalDateTime startTime;
    private Integer duration;
    private Boolean isOnline;
    private String link;
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

}
