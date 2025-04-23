package com.vhkhai.events;

import com.vhkhai.aggrerates.candidate.Candidate;
import com.vhkhai.aggrerates.job_application.Interview;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class InterviewScheduledEvent extends DomainEvent {
    private Interview interview;
    private Candidate candidate;
}
