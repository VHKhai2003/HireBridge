package com.vhkhai.command.job_application;

import an.awesome.pipelinr.Command;
import com.vhkhai.aggrerates.account.Account;
import com.vhkhai.enumerations.InterviewStatus;
import com.vhkhai.exception.ApplicationErrorCode;
import com.vhkhai.exception.ApplicationException;
import com.vhkhai.repositories.CompanyRepository;
import com.vhkhai.repositories.JobApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.UUID;

public record ChangeInterviewStatusCommand(Account account, UUID jobApplicationId,
                                           UUID interviewId, InterviewStatus newStatus) implements Command<Void> {
}

@Service
@RequiredArgsConstructor
class ChangeInterviewStatusCommandHandler implements Command.Handler<ChangeInterviewStatusCommand, Void> {

    private final JobApplicationRepository jobApplicationRepository;
    private final CompanyRepository companyRepository;

    @Override
    @PreAuthorize("hasRole('COMPANY')")
    public Void handle(ChangeInterviewStatusCommand command) {

        var company = companyRepository.findByAccountId(command.account().getId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.COMPANY_NOT_FOUND));
        var jobApplication = jobApplicationRepository.getById(command.jobApplicationId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.JOB_APPLICATION_NOT_FOUND));
        if(!company.equals(jobApplication.getJobPosting().getCompany())) {
            throw new ApplicationException(ApplicationErrorCode.ACCESS_DENIED);
        }

        var interview = jobApplication.getInterview(command.interviewId());

        switch (command.newStatus()) {
            case HAPPENING -> jobApplication.conductInterview(interview);
            case COMPLETED -> jobApplication.completeInterview(interview);
            case CANCELLED -> jobApplication.cancelInterview(interview);
            default -> throw new ApplicationException(ApplicationErrorCode.INVALID_INTERVIEW_STATUS);
        }
        jobApplicationRepository.update(jobApplication);

        return null;
    }
}
