package com.vhkhai.aggrerates.company;

import com.vhkhai.enumerations.JobPostingStatus;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class JobPosting {
    private UUID id;
    private String title;
    private String requirement;
    private JobPostingStatus status;
    private UUID companyId;

    public void changeStatus(JobPostingStatus newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        if (this.status == JobPostingStatus.CLOSED && newStatus != JobPostingStatus.REOPENED) {
            throw new IllegalStateException("Cannot change status from CLOSED to anything other than REOPENED");
        }
        this.status = newStatus;
    }
}
