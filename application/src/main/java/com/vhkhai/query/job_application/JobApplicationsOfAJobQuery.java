package com.vhkhai.query.job_application;

import com.vhkhai.dto.job_application.JobApplicationResponseDto;
import com.vhkhai.exception.ApplicationErrorCode;
import com.vhkhai.exception.ApplicationException;
import com.vhkhai.mapper.JobApplicationMapper;
import com.vhkhai.query.iquery.Query;
import com.vhkhai.repositories.CompanyRepository;
import com.vhkhai.repositories.JobApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

public record JobApplicationsOfAJobQuery(UUID accountId, UUID companyId,
                                         UUID jobPostingId) implements Query<List<JobApplicationResponseDto>> {
}

@Service
@RequiredArgsConstructor
class JobApplicationsOfAJobQueryHandler implements Query.Handler<JobApplicationsOfAJobQuery, List<JobApplicationResponseDto>> {

    private final CompanyRepository companyRepository;
    private final JobApplicationRepository jobApplicationRepository;
    private final JobApplicationMapper jobApplicationMapper;

    @PreAuthorize("hasRole('COMPANY')")
    @Override
    public List<JobApplicationResponseDto> handle(JobApplicationsOfAJobQuery query) {
        var company = companyRepository.findById(query.companyId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.COMPANY_NOT_FOUND));
        if(!company.getAccount().getId().equals(query.accountId())) {
            throw new ApplicationException(ApplicationErrorCode.ACCESS_DENIED);
        }
        var jobPosting = company.getJobPosting(query.jobPostingId());
        if(jobPosting == null) {
            throw new ApplicationException(ApplicationErrorCode.JOB_POSTING_NOT_FOUND);
        }
        var jobApplications = jobApplicationRepository.findByJobPostingId(jobPosting.getId());
        return jobApplications.stream().map(jobApplicationMapper::toDto).toList();
    }
}
