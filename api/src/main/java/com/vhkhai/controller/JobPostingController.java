package com.vhkhai.controller;

import an.awesome.pipelinr.Pipeline;
import com.vhkhai.query.company.GetJobPostingQuery;
import com.vhkhai.query.company.GetJobPostingsOfACompanyQuery;
import com.vhkhai.utils.RestResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class JobPostingController {

    private final Pipeline pipeline;

    @GetMapping("/job-postings")
    public ResponseEntity getJobPostings(@NotNull @RequestParam(name = "company") UUID companyId) {

        return new RestResponse<>()
                .withData(pipeline.send(new GetJobPostingsOfACompanyQuery(companyId)))
                .withStatus(200)
                .withMessage("Get job postings successfully")
                .buildHttpResponseEntity();
    }

    @GetMapping("/job-postings/{id}")
    public ResponseEntity getJobPosting(@NotNull @PathVariable UUID id, @NotNull @RequestParam(name = "company") UUID companyId) {

        return new RestResponse<>()
                .withData(pipeline.send(new GetJobPostingQuery(companyId, id)))
                .withStatus(200)
                .withMessage("Get job posting successfully")
                .buildHttpResponseEntity();
    }

}
