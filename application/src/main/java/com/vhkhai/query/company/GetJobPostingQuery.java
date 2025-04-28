package com.vhkhai.query.company;

import com.vhkhai.dto.job_posting.JobPostingResponseDto;
import com.vhkhai.exception.ApplicationErrorCode;
import com.vhkhai.exception.ApplicationException;
import com.vhkhai.mapper.JobPostingMapper;
import com.vhkhai.query.iquery.Query;
import com.vhkhai.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

public record GetJobPostingQuery(UUID companyId, UUID jobPostingId) implements Query<JobPostingResponseDto> {
}

@Service
@RequiredArgsConstructor
@Slf4j
class GetJobPostingQueryHandler implements Query.Handler<GetJobPostingQuery, JobPostingResponseDto> {

    private final JobPostingMapper jobPostingMapper;
    private final CompanyRepository companyRepository;

    @Cacheable(value = "jobPosting", key = "#query.jobPostingId")
    @Override
    public JobPostingResponseDto handle(GetJobPostingQuery query) {
        // log.info("Get job posting from db");
        var company = companyRepository.findById(query.companyId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.COMPANY_NOT_FOUND));
        var jobPosting = company.getJobPosting(query.jobPostingId());
        if(jobPosting == null) {
            throw new ApplicationException(ApplicationErrorCode.JOB_POSTING_NOT_FOUND);
        }
        return jobPostingMapper.toDto(jobPosting);
    }
}
