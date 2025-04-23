package com.vhkhai.query.job_application;

import com.vhkhai.dto.job_application.JobApplicationResponseDto;
import com.vhkhai.exception.ApplicationErrorCode;
import com.vhkhai.exception.ApplicationException;
import com.vhkhai.mapper.JobApplicationMapper;
import com.vhkhai.query.iquery.Query;
import com.vhkhai.repositories.CandidateRepository;
import com.vhkhai.repositories.JobApplicationRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class JobApplicationsOfCandidateQuery implements Query<List<JobApplicationResponseDto>> {
    private final UUID accountId;
}

@Service
@RequiredArgsConstructor
class JobApplicationsOfCandidateQueryHandler
        implements Query.Handler<JobApplicationsOfCandidateQuery, List<JobApplicationResponseDto>> {

    private final CandidateRepository candidateRepository;
    private final JobApplicationRepository jobApplicationRepository;
    private final JobApplicationMapper mapper;

    @Override
    @PreAuthorize("hasRole('CANDIDATE')")
    public List<JobApplicationResponseDto> handle(JobApplicationsOfCandidateQuery command) {
        var candidate = candidateRepository.findByAccountId(command.getAccountId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.CANDIDATE_NOT_FOUND));
        var jobApplications = jobApplicationRepository.findByCandidateId(candidate.getId());
        return jobApplications.stream().map(mapper::toDto).toList();
    }
}
