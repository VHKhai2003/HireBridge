package com.vhkhai.events;

import com.vhkhai.aggrerates.job_application.JobApplication;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class JobRejectEvent extends DomainEvent{
    private final JobApplication jobApplication;
}
