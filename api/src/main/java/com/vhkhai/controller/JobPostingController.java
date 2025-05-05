package com.vhkhai.controller;

import an.awesome.pipelinr.Pipeline;
import com.vhkhai.command.company.AddJobPostingCommand;
import com.vhkhai.command.company.UpdateJobPostingStatusCommand;
import com.vhkhai.command.job_application.ApplyJobCommand;
import com.vhkhai.dto.job_application.JobApplicationResponseDto;
import com.vhkhai.dto.job_posting.ChangeStatusJobPostingRequestDto;
import com.vhkhai.dto.job_posting.JobPostingRequestDto;
import com.vhkhai.dto.job_posting.JobPostingResponseDto;
import com.vhkhai.enumerations.JobField;
import com.vhkhai.enumerations.JobLevel;
import com.vhkhai.port.auth.UserAuthentication;
import com.vhkhai.query.company.GetJobPostingQuery;
import com.vhkhai.query.company.GetJobPostingsOfACompanyQuery;
import com.vhkhai.query.company.SearchJobPostingQuery;
import com.vhkhai.query.job_application.JobApplicationsOfAJobQuery;
import com.vhkhai.utils.PaginationData;
import com.vhkhai.utils.RestResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class JobPostingController {

    private final Pipeline pipeline;
    private final UserAuthentication userAuthentication;

    @GetMapping("/job-postings")
    public ResponseEntity<List<JobPostingResponseDto>> searchJobPosting(
            @RequestParam(required = false) JobField field,
            @RequestParam(required = false) JobLevel level,
            @Min(1) @RequestParam(defaultValue = "1") int page,
            @Min(1) @Max(50) @RequestParam(defaultValue = "10") int size) {
        var pageData = pipeline.send(new SearchJobPostingQuery(field, level, page, size));
        return new RestResponse<>()
                .withData(pageData.getContent())
                .withPaginationData(PaginationData.fromPage(pageData))
                .withStatus(200)
                .withMessage("Get job postings successfully")
                .buildHttpResponseEntity();

    }

    @GetMapping("/companies/{id}/job-postings")
    public ResponseEntity<List<JobPostingResponseDto>> getJobPostings(@PathVariable(name = "id") UUID companyId) {
        return new RestResponse<>()
                .withData(pipeline.send(new GetJobPostingsOfACompanyQuery(companyId)))
                .withStatus(200)
                .withMessage("Get job postings successfully")
                .buildHttpResponseEntity();
    }

    @PostMapping("/companies/{id}/job-postings")
    public ResponseEntity<Boolean> addJobPosting(
            @PathVariable(name = "id") UUID companyId,
            @Valid @RequestBody JobPostingRequestDto requestDto) {
        var command = new AddJobPostingCommand(companyId,
                requestDto.getTitle(), requestDto.getDescription(), requestDto.getField(), requestDto.getLevel());
        return new RestResponse<>()
                .withData(pipeline.send(command))
                .withStatus(200)
                .withMessage("Add job posting successfully")
                .buildHttpResponseEntity();
    }

    @GetMapping("/companies/{id}/job-postings/{jobId}")
    public ResponseEntity<JobPostingResponseDto> getJobPosting(
            @PathVariable(name = "id") UUID companyId,
            @PathVariable UUID jobId) {
        return new RestResponse<>()
                .withData(pipeline.send(new GetJobPostingQuery(companyId, jobId)))
                .withStatus(200)
                .withMessage("Get job posting successfully")
                .buildHttpResponseEntity();
    }

    @PatchMapping("/companies/{id}/job-postings/{jobId}/status")
    public ResponseEntity<Boolean> updateJobPostingStatus(
            @PathVariable(name = "id") UUID companyId,
            @PathVariable UUID jobId,
            @RequestBody ChangeStatusJobPostingRequestDto requestDto) {

        var command = new UpdateJobPostingStatusCommand(
                companyId, jobId, requestDto.getStatus(), userAuthentication.getAuthenticatedUser());
        return new RestResponse<>()
                .withData(pipeline.send(command))
                .withStatus(200)
                .withMessage("Update job posting status successfully")
                .buildHttpResponseEntity();
    }

    @PostMapping("/companies/{id}/job-postings/{jobId}/apply")
    public ResponseEntity<JobApplicationResponseDto> applyJobPosting(
            @PathVariable(name = "id") UUID companyId,
            @PathVariable UUID jobId) {
        UUID accountId = userAuthentication.getAuthenticatedUser().getId();
        return new RestResponse<>()
                .withData(pipeline.send(new ApplyJobCommand(accountId, companyId, jobId)))
                .withStatus(200)
                .withMessage("Apply for job successfully")
                .buildHttpResponseEntity();
    }

    @GetMapping("/companies/{id}/job-postings/{jobId}/job-applications")
    public ResponseEntity<List<JobPostingResponseDto>> getJobApplicationsOfAJob(
            @PathVariable(name = "id") UUID companyId,
            @PathVariable UUID jobId) {
        UUID accountId = userAuthentication.getAuthenticatedUser().getId();
        return new RestResponse<>()
                .withData(pipeline.send(new JobApplicationsOfAJobQuery(accountId, companyId, jobId)))
                .withStatus(200)
                .withMessage("Get job applications of a job successfully")
                .buildHttpResponseEntity();
    }

}
