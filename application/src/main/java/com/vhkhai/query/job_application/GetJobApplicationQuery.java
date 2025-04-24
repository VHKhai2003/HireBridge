package com.vhkhai.query.job_application;

import com.vhkhai.aggrerates.account.Account;
import com.vhkhai.dto.job_application.JobApplicationResponseDto;
import com.vhkhai.exception.ApplicationErrorCode;
import com.vhkhai.exception.ApplicationException;
import com.vhkhai.mapper.JobApplicationMapper;
import com.vhkhai.query.iquery.Query;
import com.vhkhai.repositories.CandidateRepository;
import com.vhkhai.repositories.CompanyRepository;
import com.vhkhai.repositories.JobApplicationRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class GetJobApplicationQuery implements Query<JobApplicationResponseDto> {
    private final Account account;
    private final UUID jobApplicationId;
}

@Service
@RequiredArgsConstructor
class GetJobApplicationQueryHandler
        implements Query.Handler<GetJobApplicationQuery, JobApplicationResponseDto> {

    private final CandidateRepository candidateRepository;
    private final CompanyRepository companyRepository;
    private final JobApplicationRepository jobApplicationRepository;
    private final JobApplicationMapper jobApplicationMapper;

    @Override
    public JobApplicationResponseDto handle(GetJobApplicationQuery query) {

        var jobApplication = jobApplicationRepository.findById(query.getJobApplicationId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.JOB_APPLICATION_NOT_FOUND));

        if(query.getAccount().isCandidate()) {
            var candidate = candidateRepository.findByAccountId(query.getAccount().getId())
                    .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.CANDIDATE_NOT_FOUND));
            if(!jobApplication.getCandidate().equals(candidate)) {
                throw new ApplicationException(ApplicationErrorCode.ACCESS_DENIED);
            }
        }
        else {
            var company = companyRepository.findByAccountId(query.getAccount().getId())
                    .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.COMPANY_NOT_FOUND));
            if(!jobApplication.getJobPosting().getCompany().equals(company)) {
                throw new ApplicationException(ApplicationErrorCode.ACCESS_DENIED);
            }
        }

        return jobApplicationMapper.toDto(jobApplication);
    }
}
