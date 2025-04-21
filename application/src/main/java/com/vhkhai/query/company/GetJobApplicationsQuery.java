package com.vhkhai.query.company;

import com.vhkhai.dto.job_application.JobApplicationResponseDto;
import com.vhkhai.exception.ApplicationErrorCode;
import com.vhkhai.exception.ApplicationException;
import com.vhkhai.mapper.JobApplicationMapper;
import com.vhkhai.port.UserAuthentication;
import com.vhkhai.query.iquery.Query;
import com.vhkhai.repositories.CompanyRepository;
import com.vhkhai.repositories.JobApplicationRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class GetJobApplicationsQuery implements Query<List<JobApplicationResponseDto>> {
    private final UUID jobPostingId;
}

@Service
@RequiredArgsConstructor
class GetJobApplicationsQueryHandler implements Query.Handler<GetJobApplicationsQuery, List<JobApplicationResponseDto>> {

    private final JobApplicationMapper jobApplicationMapper;
    private final JobApplicationRepository jobApplicationRepository;
    private final CompanyRepository companyRepository;
    private final UserAuthentication userAuthentication;

    @Override
    public List<JobApplicationResponseDto> handle(GetJobApplicationsQuery command) {
        var company = companyRepository.findByAccountId(userAuthentication.getAuthenticatedUser().getId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.COMPANY_NOT_FOUND));

        var jobPosting = company.getJobPosting(command.getJobPostingId());
        if(jobPosting == null) {
            throw new ApplicationException(ApplicationErrorCode.JOB_POSTING_NOT_FOUND);
        }

        return jobApplicationRepository.findByJobPostingId(jobPosting.getId())
                .stream().map(jobApplicationMapper::toDto).toList();
    }
}
