package com.vhkhai.query.job_application;

import com.vhkhai.aggrerates.account.Account;
import com.vhkhai.dto.job_application.InterviewResponseDto;
import com.vhkhai.exception.ApplicationErrorCode;
import com.vhkhai.exception.ApplicationException;
import com.vhkhai.mapper.InterviewMapper;
import com.vhkhai.query.iquery.Query;
import com.vhkhai.repositories.CandidateRepository;
import com.vhkhai.repositories.CompanyRepository;
import com.vhkhai.repositories.JobApplicationRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

public record GetInterviewQuery(Account account, UUID jobApplicationId,
                                UUID interviewId) implements Query<InterviewResponseDto> {
}

@Service
@RequiredArgsConstructor
class GetInterviewQueryHandler implements Query.Hanldler<GetInterviewQuery, InterviewResponseDto> {

    private final JobApplicationRepository jobApplicationRepository;
    private final CompanyRepository companyRepository;
    private final CandidateRepository candidateRepository;
    private final InterviewMapper interviewMapper;

    @Override
    public InterviewResponseDto handle(GetInterviewQuery query) {
        var jobApplication = jobApplicationRepository.getById(query.jobApplicationId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.JOB_APPLICATION_NOT_FOUND));
        if(query.account().isCandidate()) {
            var candidate = candidateRepository.findByAccountId(query.account().getId())
                    .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.CANDIDATE_NOT_FOUND));
            if(!jobApplication.getCandidate().equals(candidate)) {
                throw new ApplicationException(ApplicationErrorCode.ACCESS_DENIED);
            }
        }
        else {
            var company = companyRepository.findByAccountId(query.account().getId())
                    .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.COMPANY_NOT_FOUND));
            if(!jobApplication.getJobPosting().getCompany().equals(company)) {
                throw new ApplicationException(ApplicationErrorCode.ACCESS_DENIED);
            }
        }
        var interview = jobApplication.getInterview(query.interviewId());
        if(interview == null) {
            throw new ApplicationException(ApplicationErrorCode.INTERVIEW_NOT_FOUND);
        }
        return interviewMapper.toDto(interview);
    }
}
