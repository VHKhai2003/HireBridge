package com.vhkhai.controller;

import an.awesome.pipelinr.Pipeline;
import com.vhkhai.dto.job_application.JobApplicationResponseDto;
import com.vhkhai.port.UserAuthentication;
import com.vhkhai.query.job_application.GetJobApplicationQuery;
import com.vhkhai.utils.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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




}
