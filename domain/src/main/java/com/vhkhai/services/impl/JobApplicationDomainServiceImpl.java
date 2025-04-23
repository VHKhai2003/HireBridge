package com.vhkhai.services.impl;

import com.vhkhai.aggrerates.candidate.Candidate;
import com.vhkhai.aggrerates.company.JobPosting;
import com.vhkhai.aggrerates.job_application.JobApplication;
import com.vhkhai.exception.DomainErrorCode;
import com.vhkhai.exception.DomainException;
import com.vhkhai.repositories.JobApplicationRepository;
import com.vhkhai.services.JobApplicationDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobApplicationDomainServiceImpl implements JobApplicationDomainService {

    private final JobApplicationRepository jobApplicationRepository;

    @Override
    public JobApplication applyJob(Candidate candidate, JobPosting jobPosting) {

        if(candidate.getCv() == null || candidate.getCv().isBlank()) {
            throw new DomainException(DomainErrorCode.CANDIDATE_HAS_NO_CV);
        }

        // check if the job application already exists
        if(jobApplicationRepository.existsByCandidateIdAndJobPostingId(candidate.getId(), jobPosting.getId())) {
            throw new DomainException(DomainErrorCode.JOB_APPLICATION_ALREADY_EXISTS);
        }

        //company.ScheduleInterview(candidate, jobPosting);
        //candidate.ScheduleInterview(jobPosting);
        //jobPosting.ScheduleInterview(candidate);

        var jobApplication = new JobApplication(candidate, jobPosting);
        return jobApplicationRepository.create(jobApplication);
    }
}
