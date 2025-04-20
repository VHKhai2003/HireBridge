package com.vhkhai.controller;

import an.awesome.pipelinr.Pipeline;
import com.vhkhai.command.job_application.ApplyJobCommand;
import com.vhkhai.dto.job_application.JobApplicationRequestDto;
import com.vhkhai.utils.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/candidate/job-applications")
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

}
