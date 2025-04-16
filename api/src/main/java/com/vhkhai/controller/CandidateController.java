package com.vhkhai.controller;

import an.awesome.pipelinr.Pipeline;
import com.vhkhai.command.candidate.RegisterCandidateCommand;
import com.vhkhai.command.candidate.UpdateCandidateProfileCommand;
import com.vhkhai.command.candidate.UploadCVCommand;
import com.vhkhai.dto.account.AccountRequestDto;
import com.vhkhai.dto.candidate.CandidateUpdateProfileRequestDto;
import com.vhkhai.query.candidate.GetCandidateProfileQuery;
import com.vhkhai.utils.RestResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/candidate")
public class CandidateController {
    private final Pipeline pipeline;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody AccountRequestDto accountRequestDto) {

        return new RestResponse<>()
                .withData(pipeline.send(
                        new RegisterCandidateCommand(
                                accountRequestDto.getEmail(),
                                accountRequestDto.getPassword())))
                .withStatus(HttpStatus.CREATED.value())
                .withMessage("Account created successfully")
                .buildHttpResponseEntity();
    }

    @GetMapping("/me")
    public ResponseEntity getMe() {
        return new RestResponse<>()
                .withData(pipeline.send(new GetCandidateProfileQuery()))
                .withStatus(200)
                .withMessage("Get candidate successfully")
                .buildHttpResponseEntity();
    }

    @PatchMapping("/{id}")
    public ResponseEntity updateCandidate(@PathVariable UUID id,
                                          @Valid @RequestBody CandidateUpdateProfileRequestDto requestDto) {

        return new RestResponse<>()
                .withData(pipeline.send(new UpdateCandidateProfileCommand(id, requestDto)))
                .withStatus(200)
                .withMessage("Update candidate successfully")
                .buildHttpResponseEntity();
    }

    @PostMapping("/{id}/upload-cv")
    public ResponseEntity uploadCV(@PathVariable UUID id,
                                    @RequestParam("file") MultipartFile file) {

        return new RestResponse<>()
                .withData(pipeline.send(new UploadCVCommand(id, file)))
                .withStatus(200)
                .withMessage("Upload CV successfully")
                .buildHttpResponseEntity();
    }
}
