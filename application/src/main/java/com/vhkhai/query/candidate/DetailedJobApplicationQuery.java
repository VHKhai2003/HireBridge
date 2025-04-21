package com.vhkhai.query.candidate;

import an.awesome.pipelinr.Command;
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

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class DetailedJobApplicationQuery implements Query<JobApplicationResponseDto> {
    private final UUID Id;
}

@Service
@RequiredArgsConstructor
class DetailedJobApplicationQueryHandler implements Command.Handler<DetailedJobApplicationQuery, JobApplicationResponseDto> {

    private final JobApplicationRepository jobApplicationRepository;
    private final JobApplicationMapper jobApplicationMapper;
    private final UserAuthentication userAuthentication;

    @Override
    public JobApplicationResponseDto handle(DetailedJobApplicationQuery command) {

        var jobApplication = jobApplicationRepository.getById(command.getId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.JOB_APPLICATION_NOT_FOUND));

        var candidate = jobApplication.getCandidate();
        if(!candidate.getAccount().equals(userAuthentication.getAuthenticatedUser())) {
            throw new ApplicationException(ApplicationErrorCode.ACCESS_DENIED);
        }

        return jobApplicationMapper.toDto(jobApplication);
    }
}

