package com.vhkhai.controller;

import an.awesome.pipelinr.Pipeline;
import com.vhkhai.command.candidate.FollowCompanyCommand;
import com.vhkhai.command.candidate.UnFollowCompanyCommand;
import com.vhkhai.command.company.CreateCompanyCommand;
import com.vhkhai.command.company.UpdateCompanyInfoCommand;
import com.vhkhai.dto.account.AccountResponseDto;
import com.vhkhai.dto.company.CompanyResponseDto;
import com.vhkhai.dto.company.CompanyUpdateInfoRequestDto;
import com.vhkhai.port.auth.UserAuthentication;
import com.vhkhai.query.company.GetCompanyQuery;
import com.vhkhai.query.company.GetListCompaniesQuery;
import com.vhkhai.service.AuthService;
import com.vhkhai.utils.PaginationData;
import com.vhkhai.utils.RestResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/companies")
public class CompanyController {

    private final Pipeline pipeline;
    private final UserAuthentication userAuthentication;
    private final AuthService authService;

    @GetMapping("")
    public ResponseEntity<List<CompanyResponseDto>> getCompanies(
            @RequestParam(required = false) String keyword,
            @Min(1) @RequestParam(defaultValue = "1") int page,
            @Min(1) @Max(50) @RequestParam(defaultValue = "10") int size) {
        var pageData = pipeline.send(new GetListCompaniesQuery(keyword, page, size));
        return new RestResponse<>()
                .withData(pageData.getContent())
                .withPaginationData(PaginationData.fromPage(pageData))
                .withStatus(200)
                .withMessage("Get companies successfully")
                .buildHttpResponseEntity();
    }

    @PostMapping("/create")
    public ResponseEntity<AccountResponseDto> register(@Valid @RequestBody CreateCompanyCommand command) {
        AccountResponseDto accountResponseDto = pipeline.send(command);
        accountResponseDto.setToken(authService.generateToken(accountResponseDto.getId()));
        return new RestResponse<>()
                .withData(accountResponseDto)
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
    public ResponseEntity<CompanyResponseDto> updateInfo(
            @PathVariable(name = "id") UUID companyId,
            @Valid @RequestBody CompanyUpdateInfoRequestDto requestDto) {
        return new RestResponse<>()
                .withData(pipeline.send(new UpdateCompanyInfoCommand(companyId, requestDto)))
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
