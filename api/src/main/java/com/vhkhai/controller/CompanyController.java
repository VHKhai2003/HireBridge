package com.vhkhai.controller;

import an.awesome.pipelinr.Pipeline;
import com.vhkhai.command.company.AddJobPostingCommand;
import com.vhkhai.command.company.RegisterCompanyCommand;
import com.vhkhai.command.company.UpdateCompanyProfileCommand;
import com.vhkhai.dto.account.AccountRequestDto;
import com.vhkhai.dto.company.CompanyUpdateProfileRequestDto;
import com.vhkhai.dto.job_posting.JobPostingRequestDto;
import com.vhkhai.query.company.GetCompanyProfileQuery;
import com.vhkhai.utils.RestResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyController {

    private final Pipeline pipeline;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody AccountRequestDto accountRequestDto) {
        return new RestResponse<>()
                .withData(pipeline.send(
                        new RegisterCompanyCommand(
                            accountRequestDto.getEmail(),
                            accountRequestDto.getPassword())))
                .withStatus(HttpStatus.CREATED.value())
                .withMessage("Account created successfully")
                .buildHttpResponseEntity();
    }

    @GetMapping("/me")
    public ResponseEntity getMe() {
        return new RestResponse<>()
                .withData(pipeline.send(new GetCompanyProfileQuery()))
                .withStatus(200)
                .withMessage("Get company successfully")
                .buildHttpResponseEntity();
    }

    @PatchMapping("/{id}")
    public ResponseEntity updateProfile(@PathVariable UUID id,
                                        @Valid @RequestBody CompanyUpdateProfileRequestDto requestDto) {
        return new RestResponse<>()
                .withData(pipeline.send(new UpdateCompanyProfileCommand(id, requestDto)))
                .withStatus(200)
                .withMessage("Update company successfully")
                .buildHttpResponseEntity();
    }

    @PostMapping("add-job-posting")
    public ResponseEntity addJobPosting(@Valid @RequestBody JobPostingRequestDto requestDto) {

        pipeline.send(new AddJobPostingCommand(requestDto.getTitle(), requestDto.getRequirement()));

        return new RestResponse<>()
                .withStatus(200)
                .withMessage("Add job posting successfully")
                .buildHttpResponseEntity();
    }

}
