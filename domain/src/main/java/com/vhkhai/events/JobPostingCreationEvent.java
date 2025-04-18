package com.vhkhai.events;

import com.vhkhai.aggrerates.company.JobPosting;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class JobPostingCreationEvent {
    private JobPosting jobPosting;
}
