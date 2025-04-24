package com.vhkhai.events;

import com.vhkhai.aggrerates.job_application.JobApplication;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class JobOfferEvent extends DomainEvent {
    private final JobApplication jobApplication;
}
