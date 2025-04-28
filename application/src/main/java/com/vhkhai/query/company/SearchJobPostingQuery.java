package com.vhkhai.query.company;

import an.awesome.pipelinr.Command;
import com.vhkhai.aggrerates.company.JobPosting;
import com.vhkhai.dto.job_posting.JobPostingResponseDto;
import com.vhkhai.enumerations.JobField;
import com.vhkhai.enumerations.JobLevel;
import com.vhkhai.mapper.JobPostingMapper;
import com.vhkhai.query.iquery.Query;
import com.vhkhai.repositories.CompanyRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

public record SearchJobPostingQuery(JobField field, JobLevel level, int page,
                                    int size) implements Query<Page<JobPostingResponseDto>> {
}

@Service
@RequiredArgsConstructor
class SearchJobPostingQueryHandler implements Command.Handler<SearchJobPostingQuery, Page<JobPostingResponseDto>> {
    private final CompanyRepository companyRepository;
    private final JobPostingMapper jobPostingMapper;

    public Page<JobPostingResponseDto> handle(SearchJobPostingQuery query) {
        Pageable pageable = PageRequest.of(query.page() - 1, query.size());
        Page<JobPosting> pageData = companyRepository
                .findJobPostingsByFieldAndLevelWithPagination(query.field(), query.level(), pageable);
        return pageData.map(jobPostingMapper::toDto);
    }
}
