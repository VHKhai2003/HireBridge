package com.vhkhai.controller;

import com.vhkhai.aggrerates.account.Account;
import com.vhkhai.dto.candidate.CandidateUpdateProfileRequestDto;
import com.vhkhai.exception.ApplicationErrorCode;
import com.vhkhai.exception.ApplicationException;
import com.vhkhai.port.UserAuthentication;
import com.vhkhai.service.CandidateService;
import com.vhkhai.utils.RestResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/candidate")
public class CandidateController {
    private final UserAuthentication authenticatedUserProvider;
    private final CandidateService candidateService;

    @GetMapping("/me")
    public ResponseEntity getMe() {
        Account account = authenticatedUserProvider.getAuthenticatedUser();
        return new RestResponse<>()
                .withData(candidateService.getCandidateByAccountId(account.getId()))
                .withStatus(200)
                .withMessage("Get candidate successfully")
                .buildHttpResponseEntity();
    }

    @PatchMapping("/{id}")
    public ResponseEntity updateCandidate(@PathVariable UUID id,
                                          @Valid @RequestBody CandidateUpdateProfileRequestDto requestDto) {

        return new RestResponse<>()
                .withData(candidateService.updateProfile(id, requestDto))
                .withStatus(200)
                .withMessage("Update candidate successfully")
                .buildHttpResponseEntity();
    }

    @PostMapping("/{id}/upload-cv")
    public ResponseEntity uploadCV(@PathVariable UUID id,
                                    @RequestParam("file") MultipartFile file) {

        return new RestResponse<>()
                .withData(candidateService.uploadCV(id, file))
                .withStatus(200)
                .withMessage("Upload CV successfully")
                .buildHttpResponseEntity();
    }
}
