package com.vhkhai.command.job_application;

import an.awesome.pipelinr.Command;
import com.vhkhai.dto.job_application.JobApplicationResponseDto;
import com.vhkhai.exception.ApplicationErrorCode;
import com.vhkhai.exception.ApplicationException;
import com.vhkhai.mapper.JobApplicationMapper;
import com.vhkhai.repositories.CandidateRepository;
import com.vhkhai.repositories.CompanyRepository;
import com.vhkhai.services.JobApplicationDomainService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public record ApplyJobCommand(UUID accountId, UUID companyId,
                              UUID jobPostingId) implements Command<JobApplicationResponseDto> {
}


@Service
@RequiredArgsConstructor
class ApplyJobCommandHandler implements Command.Handler<ApplyJobCommand, JobApplicationResponseDto> {

    private final JobApplicationDomainService jobApplicationDomainService;
    private final CandidateRepository candidateRepository;
    private final CompanyRepository companyRepository;
    private final JobApplicationMapper mapper;

    @PreAuthorize("hasRole('CANDIDATE')")
    @Transactional
    @Override
    public JobApplicationResponseDto handle(ApplyJobCommand command) {

        var candidate = candidateRepository.findByAccountId(command.accountId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.CANDIDATE_NOT_FOUND));

        var company = companyRepository.getById(command.companyId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.COMPANY_NOT_FOUND));

        var jobPosting = company.getJobPosting(command.jobPostingId());
        if(jobPosting == null) {
            throw new ApplicationException(ApplicationErrorCode.JOB_POSTING_NOT_FOUND);
        }

        var jobApplication = jobApplicationDomainService.applyJob(candidate, jobPosting);
        return mapper.toDto(jobApplication);
    }
}