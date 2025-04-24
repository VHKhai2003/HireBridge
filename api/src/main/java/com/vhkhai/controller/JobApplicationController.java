package com.vhkhai.controller;

import an.awesome.pipelinr.Pipeline;
import com.vhkhai.command.job_application.AddInterviewCommand;
import com.vhkhai.dto.job_application.InterviewRequestDto;
import com.vhkhai.dto.job_application.InterviewResponseDto;
import com.vhkhai.dto.job_application.JobApplicationResponseDto;
import com.vhkhai.port.UserAuthentication;
import com.vhkhai.query.job_application.GetJobApplicationQuery;
import com.vhkhai.utils.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/job-applications/{id}")
public class JobApplicationController {

    private final Pipeline pipeline;
    private final UserAuthentication userAuthentication;

    @GetMapping("")
    public ResponseEntity<JobApplicationResponseDto> getJobApplication(@PathVariable UUID id) {
        return new RestResponse<>()
                .withData(pipeline.send(new GetJobApplicationQuery(userAuthentication.getAuthenticatedUser(), id)))
                .withStatus(200)
                .withMessage("Apply for job successfully")
                .buildHttpResponseEntity();
    }

    @PostMapping("/interviews")
    public ResponseEntity<InterviewResponseDto> addInterview(
            @PathVariable UUID id,
            @RequestBody InterviewRequestDto requestDto) {
        UUID accountId = userAuthentication.getAuthenticatedUser().getId();
        return new RestResponse<>()
                .withData(pipeline.send(new AddInterviewCommand(accountId, id, requestDto)))
                .withStatus(200)
                .withMessage("Apply for job successfully")
                .buildHttpResponseEntity();
    }


}
