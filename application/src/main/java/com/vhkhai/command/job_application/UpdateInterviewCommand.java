package com.vhkhai.command.job_application;

import an.awesome.pipelinr.Command;
import com.vhkhai.aggrerates.account.Account;
import com.vhkhai.dto.job_application.InterviewRequestDto;
import com.vhkhai.exception.ApplicationErrorCode;
import com.vhkhai.exception.ApplicationException;
import com.vhkhai.repositories.CompanyRepository;
import com.vhkhai.repositories.JobApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.UUID;

public record UpdateInterviewCommand(Account account, UUID jobApplicationId, UUID interviewId,
                                     InterviewRequestDto requestDto) implements Command<Void> {
}

@Service
@RequiredArgsConstructor
class UpdateInterviewCommandHandler implements Command.Handler<UpdateInterviewCommand, Void> {

    private final JobApplicationRepository jobApplicationRepository;
    private final CompanyRepository companyRepository;

    @Override
    @PreAuthorize("hasRole('COMPANY')")
    public Void handle(UpdateInterviewCommand command) {
        var company = companyRepository.findByAccountId(command.account().getId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.COMPANY_NOT_FOUND));
        var jobApplication = jobApplicationRepository.getById(command.jobApplicationId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.JOB_APPLICATION_NOT_FOUND));
        if(!company.equals(jobApplication.getJobPosting().getCompany())) {
            throw new ApplicationException(ApplicationErrorCode.ACCESS_DENIED);
        }

        var requestDto = command.requestDto();

        jobApplication.updateInterview(
                command.interviewId(),
                requestDto.getStartTime(),
                requestDto.getDuration(),
                requestDto.isOnline(),
                requestDto.getLink()
        );
        jobApplicationRepository.update(jobApplication);

        return null;
    }
}
