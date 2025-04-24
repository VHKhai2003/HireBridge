package com.vhkhai.controller;

import an.awesome.pipelinr.Pipeline;
import com.vhkhai.command.candidate.FollowCompanyCommand;
import com.vhkhai.command.candidate.UnFollowCompanyCommand;
import com.vhkhai.command.company.CreateCompanyCommand;
import com.vhkhai.command.company.UpdateCompanyProfileCommand;
import com.vhkhai.dto.account.AccountResponseDto;
import com.vhkhai.dto.company.CompanyResponseDto;
import com.vhkhai.dto.company.CompanyUpdateProfileRequestDto;
import com.vhkhai.port.UserAuthentication;
import com.vhkhai.query.company.GetCompanyQuery;
import com.vhkhai.utils.RestResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/companies")
public class CompanyController {

    private final Pipeline pipeline;
    private final UserAuthentication userAuthentication;

    @PostMapping("/create")
    public ResponseEntity<AccountResponseDto> register(@Valid @RequestBody CreateCompanyCommand command) {
        return new RestResponse<>()
                .withData(pipeline.send(command))
                .withStatus(HttpStatus.CREATED.value())
                .withMessage("Account created successfully")
                .buildHttpResponseEntity();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponseDto> getCompany(@PathVariable UUID id) {
        return new RestResponse<>()
                .withData(pipeline.send(new GetCompanyQuery(id)))
                .withStatus(200)
                .withMessage("Get company successfully")
                .buildHttpResponseEntity();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CompanyResponseDto> updateProfile(
            @PathVariable(name = "id") UUID companyId,
            @Valid @RequestBody CompanyUpdateProfileRequestDto requestDto) {
        return new RestResponse<>()
                .withData(pipeline.send(new UpdateCompanyProfileCommand(companyId, requestDto)))
                .withStatus(200)
                .withMessage("Update company successfully")
                .buildHttpResponseEntity();
    }

    @PostMapping("/{id}/follow")
    public ResponseEntity<CompanyResponseDto> followCompany(@PathVariable(name = "id" ) UUID companyId) {
        UUID accountId = userAuthentication.getAuthenticatedUser().getId();
        return new RestResponse<>()
                .withData(pipeline.send(new FollowCompanyCommand(companyId, accountId)))
                .withStatus(200)
                .withMessage("Follow company successfully")
                .buildHttpResponseEntity();
    }

    @PostMapping("/{id}/unfollow")
    public ResponseEntity<CompanyResponseDto> unfollowCompany(@PathVariable(name = "id" ) UUID companyId) {
        UUID accountId = userAuthentication.getAuthenticatedUser().getId();
        return new RestResponse<>()
                .withData(pipeline.send(new UnFollowCompanyCommand(companyId, accountId)))
                .withStatus(200)
                .withMessage("Unfollow company successfully")
                .buildHttpResponseEntity();
    }

}
