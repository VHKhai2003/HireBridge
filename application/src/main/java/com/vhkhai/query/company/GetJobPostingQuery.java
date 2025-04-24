package com.vhkhai.query.company;

import com.vhkhai.dto.job_posting.JobPostingResponseDto;
import com.vhkhai.exception.ApplicationErrorCode;
import com.vhkhai.exception.ApplicationException;
import com.vhkhai.mapper.JobPostingMapper;
import com.vhkhai.query.iquery.Query;
import com.vhkhai.repositories.CompanyRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class GetJobPostingQuery implements Query<JobPostingResponseDto> {
    private final UUID companyId;
    private final UUID jobPostingId;
}

@Service
@RequiredArgsConstructor
class GetJobPostingQueryHandler implements Query.Handler<GetJobPostingQuery, JobPostingResponseDto> {

    private final JobPostingMapper jobPostingMapper;
    private final CompanyRepository companyRepository;

    @Override
    public JobPostingResponseDto handle(GetJobPostingQuery command) {

        var company = companyRepository.findById(command.getCompanyId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.COMPANY_NOT_FOUND));

        var jobPosting = company.getJobPosting(command.getJobPostingId());
        if(jobPosting == null) {
            throw new ApplicationException(ApplicationErrorCode.JOB_POSTING_NOT_FOUND);
        }

        return jobPostingMapper.toDto(jobPosting);
    }
}
