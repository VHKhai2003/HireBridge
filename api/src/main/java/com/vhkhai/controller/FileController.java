package com.vhkhai.controller;

import com.vhkhai.service.MediaService;
import com.vhkhai.utils.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class FileController {
    private final MediaService mediaService;

    @GetMapping("signed-url")
    public ResponseEntity<String> getSignedUrl(@RequestParam String publicId) {
        return new RestResponse<>()
                .withData(mediaService.signedUrl(publicId))
                .withStatus(HttpStatus.OK.value())
                .withMessage("Get signed url successfully")
                .buildHttpResponseEntity();
    }
}
