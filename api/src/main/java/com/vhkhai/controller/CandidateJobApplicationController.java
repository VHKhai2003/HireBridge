package com.vhkhai.controller;

import an.awesome.pipelinr.Pipeline;
import com.vhkhai.command.job_application.ApplyJobCommand;
import com.vhkhai.dto.job_application.JobApplicationRequestDto;
import com.vhkhai.query.candidate.DetailedJobApplicationQuery;
import com.vhkhai.utils.RestResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/candidate/job-application")
@RequiredArgsConstructor
public class CandidateJobApplicationController {
    private final Pipeline pipeline;

    @PostMapping("/apply")
    public ResponseEntity applyJob(@RequestBody JobApplicationRequestDto jobApplicationRequestDto) {

        return new RestResponse<>()
                .withData(pipeline.send(new ApplyJobCommand(jobApplicationRequestDto.getJobPostingId())))
                .withStatus(200)
                .withMessage("Apply for job successfully")
                .buildHttpResponseEntity();
    }

    @GetMapping("/{id}")
    public ResponseEntity getJobApplication(@NotNull @PathVariable UUID id) {

        return new RestResponse<>()
                .withData(pipeline.send(new DetailedJobApplicationQuery(id)))
                .withStatus(200)
                .withMessage("Get job application successfully")
                .buildHttpResponseEntity();
    }

}
