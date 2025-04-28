package com.vhkhai.services;

import com.vhkhai.aggrerates.candidate.Candidate;
import com.vhkhai.aggrerates.company.JobPosting;
import com.vhkhai.aggrerates.job_application.JobApplication;

public interface JobApplicationDomainService {
    JobApplication applyJob(Candidate candidate, JobPosting jobPosting);
}
