package com.vhkhai.command.job_application;

import an.awesome.pipelinr.Command;
import com.vhkhai.dto.job_application.InterviewRequestDto;
import com.vhkhai.dto.job_application.InterviewResponseDto;
import com.vhkhai.exception.ApplicationErrorCode;
import com.vhkhai.exception.ApplicationException;
import com.vhkhai.mapper.InterviewMapper;
import com.vhkhai.repositories.CompanyRepository;
import com.vhkhai.repositories.JobApplicationRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class AddInterviewCommand implements Command<InterviewResponseDto> {
    private final UUID accountId;
    private final UUID jobApplicationId;
    private final InterviewRequestDto requestDto;
}

@Service
@RequiredArgsConstructor
class AddInterviewCommandHandler implements Command.Handler<AddInterviewCommand, InterviewResponseDto> {

    private final JobApplicationRepository jobApplicationRepository;
    private final CompanyRepository companyRepository;
    private final InterviewMapper interviewMapper;

    @Override
    @Transactional
    @PreAuthorize("hasRole('COMPANY')")
    public InterviewResponseDto handle(AddInterviewCommand command) {
        var jobApplication = jobApplicationRepository.getById(command.getJobApplicationId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.JOB_APPLICATION_NOT_FOUND));
        var company = companyRepository.findByAccountId(command.getAccountId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.COMPANY_NOT_FOUND));
        if(!company.equals(jobApplication.getJobPosting().getCompany())) {
            throw new ApplicationException(ApplicationErrorCode.ACCESS_DENIED);
        }
        var requestDto = command.getRequestDto();
        var interview = jobApplication.addInterview(
                requestDto.getStartTime(),
                requestDto.getDuration(),
                requestDto.isOnline(),
                requestDto.getLink());
        jobApplicationRepository.update(jobApplication);
        return interviewMapper.toDto(interview);
    }
}
