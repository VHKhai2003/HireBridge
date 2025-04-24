package com.vhkhai.command.job_application;

import an.awesome.pipelinr.Command;
import com.vhkhai.exception.ApplicationErrorCode;
import com.vhkhai.exception.ApplicationException;
import com.vhkhai.repositories.CompanyRepository;
import com.vhkhai.repositories.JobApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public record JobRejectCommand(UUID jobApplicationId, UUID accountId, String reason) implements Command<Void> {
}

@Service
@RequiredArgsConstructor
class JobRejectCommandHandler implements Command.Handler<JobRejectCommand, Void> {

    private final JobApplicationRepository jobApplicationRepository;
    private final CompanyRepository companyRepository;

    @Override
    @PreAuthorize("hasRole('COMPANY')")
    @Transactional
    public Void handle(JobRejectCommand command) {
        var company = companyRepository.findByAccountId(command.accountId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.COMPANY_NOT_FOUND));
        var jobApplication = jobApplicationRepository.findById(command.jobApplicationId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.JOB_APPLICATION_NOT_FOUND));
        if(!company.equals(jobApplication.getJobPosting().getCompany())) {
            throw new ApplicationException(ApplicationErrorCode.ACCESS_DENIED);
        }
        jobApplication.reject(command.reason());
        jobApplicationRepository.update(jobApplication);
        return null;
    }
}

