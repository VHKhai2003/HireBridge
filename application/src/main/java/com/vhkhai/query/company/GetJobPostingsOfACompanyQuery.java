package com.vhkhai.query.company;

import com.vhkhai.dto.job_posting.JobPostingResponseDto;
import com.vhkhai.exception.ApplicationErrorCode;
import com.vhkhai.exception.ApplicationException;
import com.vhkhai.mapper.JobPostingMapper;
import com.vhkhai.query.iquery.Query;
import com.vhkhai.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

public record GetJobPostingsOfACompanyQuery(UUID companyId) implements Query<List<JobPostingResponseDto>> {
}

@Service
@RequiredArgsConstructor
class GetJobPostingsOfACompanyQueryHandler implements Query.Handler<GetJobPostingsOfACompanyQuery, List<JobPostingResponseDto>> {

    private final CompanyRepository companyRepository;
    private final JobPostingMapper jobPostingMapper;

    @Override
    public List<JobPostingResponseDto> handle(GetJobPostingsOfACompanyQuery command) {
        var company = companyRepository.findById(command.companyId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.COMPANY_NOT_FOUND));
        return company.getJobPostings().stream().map(jobPostingMapper::toDto).toList();
    }
}
