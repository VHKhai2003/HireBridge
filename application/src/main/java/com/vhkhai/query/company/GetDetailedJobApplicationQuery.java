package com.vhkhai.query.company;

import com.vhkhai.dto.job_application.JobApplicationResponseDto;
import com.vhkhai.exception.ApplicationErrorCode;
import com.vhkhai.exception.ApplicationException;
import com.vhkhai.mapper.JobApplicationMapper;
import com.vhkhai.port.UserAuthentication;
import com.vhkhai.query.iquery.Query;
import com.vhkhai.repositories.JobApplicationRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class GetDetailedJobApplicationQuery implements Query<JobApplicationResponseDto> {
    private final UUID id;
}

@Service
@RequiredArgsConstructor
class GetDetailedJobApplicationQueryHandler implements Query.Handler<GetDetailedJobApplicationQuery, JobApplicationResponseDto> {

    private final JobApplicationMapper jobApplicationMapper;
    private final JobApplicationRepository jobApplicationRepository;
    private final UserAuthentication userAuthentication;

    @Transactional
    @Override
    public JobApplicationResponseDto handle(GetDetailedJobApplicationQuery command) {

        var jobApplication = jobApplicationRepository.getById(command.getId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.JOB_APPLICATION_NOT_FOUND));

        var company = jobApplication.getJobPosting().getCompany();

        // job application must belong to the company
        if(!company.getAccount().equals(userAuthentication.getAuthenticatedUser())) {
            throw new ApplicationException(ApplicationErrorCode.ACCESS_DENIED);
        }

        return jobApplicationMapper.toDto(jobApplication);
    }
}
