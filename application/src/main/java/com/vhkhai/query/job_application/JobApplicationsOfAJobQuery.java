package com.vhkhai.query.job_application;

import com.vhkhai.dto.job_application.JobApplicationResponseDto;
import com.vhkhai.exception.ApplicationErrorCode;
import com.vhkhai.exception.ApplicationException;
import com.vhkhai.mapper.JobApplicationMapper;
import com.vhkhai.query.iquery.Query;
import com.vhkhai.repositories.CompanyRepository;
import com.vhkhai.repositories.JobApplicationRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class JobApplicationsOfAJobQuery implements Query<List<JobApplicationResponseDto>> {
    private final UUID accountId;
    private final UUID comapanyId;
    private final UUID jobPostingId;
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
        var company = companyRepository.findByAccountId(query.getAccountId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.COMPANY_NOT_FOUND));
        if(!company.getId().equals(query.getComapanyId())) {
            throw new ApplicationException(ApplicationErrorCode.ACCESS_DENIED);
        }
        var jobPosting = company.getJobPosting(query.getJobPostingId());
        if(jobPosting == null) {
            throw new ApplicationException(ApplicationErrorCode.JOB_POSTING_NOT_FOUND);
        }
        var jobApplications = jobApplicationRepository.findByJobPostingId(jobPosting.getId());
        return jobApplications.stream().map(jobApplicationMapper::toDto).toList();
    }
}
