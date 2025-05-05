package com.vhkhai.query.job_application;

import an.awesome.pipelinr.Command;
import com.vhkhai.aggrerates.account.Account;
import com.vhkhai.dto.candidate.CandidateResponseDto;
import com.vhkhai.exception.ApplicationErrorCode;
import com.vhkhai.exception.ApplicationException;
import com.vhkhai.mapper.CandidateMapper;
import com.vhkhai.query.iquery.Query;
import com.vhkhai.repositories.JobApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.UUID;

public record GetCandidateOfJobApplicationQuery(
        Account account, UUID jobApplicationId) implements Query<CandidateResponseDto> {
}

@Service
@RequiredArgsConstructor
class GetCandidateOfJobApplicationQueryHandler implements Command.Handler<GetCandidateOfJobApplicationQuery, CandidateResponseDto> {
    private final JobApplicationRepository jobApplicationRepository;
    private final CandidateMapper candidateMapper;

    @PreAuthorize("hasRole('COMPANY')")
    @Override
    public CandidateResponseDto handle(GetCandidateOfJobApplicationQuery query) {
        var jobApplication = jobApplicationRepository.findById(query.jobApplicationId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.JOB_APPLICATION_NOT_FOUND));
        var company = jobApplication.getJobPosting().getCompany();
        if (!company.getAccount().equals(query.account())) {
            throw new ApplicationException(ApplicationErrorCode.ACCESS_DENIED);
        }
        return candidateMapper.toDto(jobApplication.getCandidate());
    }
}
