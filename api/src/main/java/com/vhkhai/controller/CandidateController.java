package com.vhkhai.controller;

import com.vhkhai.port.UserAuthentication;
import com.vhkhai.utils.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/candidate")
public class CandidateController {
    private final UserAuthentication authenticatedUserProvider;

    @GetMapping("/me")
    public ResponseEntity getMe() {
        return new RestResponse<>()
                .withData(authenticatedUserProvider.getAuthenticatedUser())
                .withStatus(200)
                .withMessage("Get candidate successfully")
                .buildHttpResponseEntity();
    }
}
