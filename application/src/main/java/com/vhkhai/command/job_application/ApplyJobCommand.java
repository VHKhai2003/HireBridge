package com.vhkhai.command.job_application;

import an.awesome.pipelinr.Command;
import com.vhkhai.aggrerates.account.Account;
import com.vhkhai.aggrerates.candidate.Candidate;
import com.vhkhai.aggrerates.job_application.JobApplication;
import com.vhkhai.dto.job_application.JobApplicationResponseDto;
import com.vhkhai.enumerations.ApplicationStatus;
import com.vhkhai.exception.ApplicationErrorCode;
import com.vhkhai.exception.ApplicationException;
import com.vhkhai.mapper.JobApplicationMapper;
import com.vhkhai.port.UserAuthentication;
import com.vhkhai.repositories.CandidateRepository;
import com.vhkhai.repositories.CompanyRepository;
import com.vhkhai.repositories.JobApplicationRepository;
import com.vhkhai.services.JobApplicationDomainService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class ApplyJobCommand implements Command<JobApplicationResponseDto> {
    private final UUID jobPostingId;
}


@Service
@RequiredArgsConstructor
class ApplyJobCommandHandler implements Command.Handler<ApplyJobCommand, JobApplicationResponseDto> {

    private final UserAuthentication userAuthentication;
    private final JobApplicationDomainService jobApplicationDomainService;
    private final CandidateRepository candidateRepository;
    private final CompanyRepository companyRepository;
    private final JobApplicationMapper mapper;

    @Transactional
    @Override
    public JobApplicationResponseDto handle(ApplyJobCommand command) {

        var candidate = candidateRepository.findByAccountId(userAuthentication.getAuthenticatedUser().getId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.CANDIDATE_NOT_FOUND));

        var jobPosting = companyRepository.findJobPostingById(command.getJobPostingId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.JOB_POSTING_NOT_FOUND));

        var jobApplication = jobApplicationDomainService.applyJob(candidate, jobPosting);

        return mapper.toDto(jobApplication);
    }
}