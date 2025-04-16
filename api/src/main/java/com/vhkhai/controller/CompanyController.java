package com.vhkhai.controller;

import an.awesome.pipelinr.Pipeline;
import com.vhkhai.aggrerates.account.Account;
import com.vhkhai.command.RegisterCompanyCommand;
import com.vhkhai.dto.account.AccountRequestDto;
import com.vhkhai.dto.company.CompanyUpdateProfileRequestDto;
import com.vhkhai.port.UserAuthentication;
import com.vhkhai.service.CompanyService;
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
    private final UserAuthentication authenticatedUserProvider;
    private final CompanyService companyService;
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
        Account account = authenticatedUserProvider.getAuthenticatedUser();
        return new RestResponse<>()
                .withData(companyService.getCompanyByAccountId(account.getId()))
                .withStatus(200)
                .withMessage("Get company successfully")
                .buildHttpResponseEntity();
    }

    @PutMapping("/{id}")
    public ResponseEntity updateProfile(@PathVariable UUID id,
                                        @Valid @RequestBody CompanyUpdateProfileRequestDto requestDto) {
        return new RestResponse<>()
                .withData(companyService.updateCompanyProfile(id, requestDto))
                .withStatus(200)
                .withMessage("Update company successfully")
                .buildHttpResponseEntity();
    }

}
