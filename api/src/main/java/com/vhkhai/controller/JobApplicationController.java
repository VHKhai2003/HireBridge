package com.vhkhai.controller;

import an.awesome.pipelinr.Pipeline;
import com.vhkhai.command.job_application.AddInterviewCommand;
import com.vhkhai.command.job_application.ChangeInterviewStatusCommand;
import com.vhkhai.command.job_application.UpdateInterviewCommand;
import com.vhkhai.dto.job_application.ChangeInterviewStatusRequestDto;
import com.vhkhai.dto.job_application.InterviewRequestDto;
import com.vhkhai.dto.job_application.InterviewResponseDto;
import com.vhkhai.dto.job_application.JobApplicationResponseDto;
import com.vhkhai.port.UserAuthentication;
import com.vhkhai.query.job_application.GetInterviewQuery;
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
                .withMessage("Get job application successfully")
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
                .withMessage("Add new interview successfully")
                .buildHttpResponseEntity();
    }

    @GetMapping("/interviews/{interviewId}")
    public ResponseEntity<InterviewResponseDto> getInterview(
            @PathVariable(name = "id") UUID jobApplicationId,
            @PathVariable UUID interviewId) {

        return new RestResponse<>()
                .withData(pipeline.send(new GetInterviewQuery(
                        userAuthentication.getAuthenticatedUser(), jobApplicationId, interviewId)))
                .withStatus(200)
                .withMessage("Get interview successfully")
                .buildHttpResponseEntity();
    }

    @PutMapping("/interviews/{interviewId}")
    public ResponseEntity updateInterview(
            @PathVariable(name = "id") UUID jobApplicationId,
            @PathVariable UUID interviewId,
            @RequestBody InterviewRequestDto requestDto) {
        pipeline.send(new UpdateInterviewCommand(userAuthentication.getAuthenticatedUser(), jobApplicationId, interviewId, requestDto));
        return new RestResponse<>()
                .withStatus(200)
                .withMessage("Update interview successfully")
                .buildHttpResponseEntity();
    }

    @PatchMapping("/interviews/{interviewId}")
    public ResponseEntity changeInterviewStatus(
            @PathVariable(name = "id") UUID jobApplicationId,
            @PathVariable UUID interviewId,
            @RequestBody ChangeInterviewStatusRequestDto requestDto) {

        pipeline.send(
                new ChangeInterviewStatusCommand(userAuthentication.getAuthenticatedUser(),
                        jobApplicationId, interviewId, requestDto.getStatus())
        );
        return new RestResponse<>()
                .withStatus(200)
                .withMessage("Change interview status successfully")
                .buildHttpResponseEntity();
    }

}
