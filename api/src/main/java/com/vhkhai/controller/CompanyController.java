package com.vhkhai.controller;

import com.vhkhai.aggrerates.account.Account;
import com.vhkhai.dto.company.CompanyUpdateProfileRequestDto;
import com.vhkhai.port.UserAuthentication;
import com.vhkhai.service.CompanyService;
import com.vhkhai.utils.RestResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyController {
    private final UserAuthentication authenticatedUserProvider;
    private final CompanyService companyService;

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
