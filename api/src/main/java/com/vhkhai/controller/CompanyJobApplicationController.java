package com.vhkhai.controller;

import an.awesome.pipelinr.Pipeline;
import com.vhkhai.command.job_application.ApplyJobCommand;
import com.vhkhai.query.company.GetJobApplicationsQuery;
import com.vhkhai.utils.RestResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/company/job-application")
@RequiredArgsConstructor
public class CompanyJobApplicationController {
    private final Pipeline pipeline;

    @GetMapping("")
    public ResponseEntity getJobApplications(@NotNull @RequestParam(name = "jobId") UUID jobId) {

        return new RestResponse<>()
                .withData(pipeline.send(new GetJobApplicationsQuery(jobId)))
                .withStatus(200)
                .withMessage("Apply for job successfully")
                .buildHttpResponseEntity();
    }

}
