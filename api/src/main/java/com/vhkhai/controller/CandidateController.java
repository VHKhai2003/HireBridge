package com.vhkhai.controller;

import an.awesome.pipelinr.Pipeline;
import com.vhkhai.command.candidate.RegisterCandidateCommand;
import com.vhkhai.command.candidate.UpdateCandidateProfileCommand;
import com.vhkhai.command.candidate.UploadCVCommand;
import com.vhkhai.dto.account.AccountResponseDto;
import com.vhkhai.dto.candidate.CandidateResponseDto;
import com.vhkhai.dto.candidate.CandidateUpdateProfileRequestDto;
import com.vhkhai.dto.company.CompanyResponseDto;
import com.vhkhai.dto.job_application.JobApplicationResponseDto;
import com.vhkhai.dto.notification.NotificationResponseDto;
import com.vhkhai.port.UserAuthentication;
import com.vhkhai.query.candidate.GetCandidateProfileQuery;
import com.vhkhai.query.candidate.GetCandidateQuery;
import com.vhkhai.query.candidate.GetFollowedCompaniesQuery;
import com.vhkhai.query.candidate.GetNotificationsQuery;
import com.vhkhai.query.job_application.JobApplicationsOfCandidateQuery;
import com.vhkhai.service.AccountService;
import com.vhkhai.utils.RestResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/candidates")
public class CandidateController {
    private final Pipeline pipeline;
    private final UserAuthentication userAuthentication;
    private final AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<AccountResponseDto> register(@Valid @RequestBody RegisterCandidateCommand command) {
        AccountResponseDto accountResponseDto = pipeline.send(command);
        accountResponseDto.setToken(accountService.generateToken(accountResponseDto.getId()));
        return new RestResponse<AccountResponseDto>()
                .withData(accountResponseDto)
                .withStatus(HttpStatus.CREATED.value())
                .withMessage("Account created successfully")
                .buildHttpResponseEntity();
    }

    @GetMapping("/me")
    public ResponseEntity<CandidateResponseDto> getProfile() {
        var accountId = userAuthentication.getAuthenticatedUser().getId();
        return new RestResponse<>()
                .withData(pipeline.send(new GetCandidateProfileQuery(accountId)))
                .withStatus(200)
                .withMessage("Get candidate successfully")
                .buildHttpResponseEntity();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidateResponseDto> getCandidate(
            @NotNull(message = "Candidate id is required") @PathVariable UUID id) {

        return new RestResponse<>()
                .withData(pipeline.send(new GetCandidateQuery(id)))
                .withStatus(200)
                .withMessage("Get candidate successfully")
                .buildHttpResponseEntity();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CandidateResponseDto> updateCandidate(
            @NotNull(message = "Candidate id is required") @PathVariable UUID id,
            @Valid @RequestBody CandidateUpdateProfileRequestDto requestDto) {

        return new RestResponse<>()
                .withData(pipeline.send(new UpdateCandidateProfileCommand(id, requestDto)))
                .withStatus(200)
                .withMessage("Update candidate successfully")
                .buildHttpResponseEntity();
    }

    @PostMapping("/{id}/upload-cv")
    public ResponseEntity<String> uploadCV(
            @NotNull(message = "Candidate id is required") @PathVariable UUID id,
            @RequestParam("file") MultipartFile file) {

        return new RestResponse<>()
                .withData(pipeline.send(new UploadCVCommand(id, file)))
                .withStatus(200)
                .withMessage("Upload CV successfully")
                .buildHttpResponseEntity();
    }

    @GetMapping("/my-job-applications")
    public ResponseEntity<List<JobApplicationResponseDto>> getMyJobApplications() {
        var accountId = userAuthentication.getAuthenticatedUser().getId();
        return new RestResponse<>()
                .withData(pipeline.send(new JobApplicationsOfCandidateQuery(accountId)))
                .withStatus(200)
                .withMessage("Get my job applications successfully")
                .buildHttpResponseEntity();
    }

    @GetMapping("/my-followed-companies")
    public ResponseEntity<List<CompanyResponseDto>> getJobApplications() {
        var accountId = userAuthentication.getAuthenticatedUser().getId();
        return new RestResponse<>()
                .withData(pipeline.send(new GetFollowedCompaniesQuery(accountId)))
                .withStatus(200)
                .withMessage("Get my job applications successfully")
                .buildHttpResponseEntity();
    }

    @GetMapping("/notifications")
    public ResponseEntity<List<NotificationResponseDto>> getNotifications() {
        return new RestResponse<>()
                .withData(pipeline.send(new GetNotificationsQuery(userAuthentication.getAuthenticatedUser().getId())))
                .withStatus(200)
                .withMessage("Get notifications successfully")
                .buildHttpResponseEntity();
    }

}
