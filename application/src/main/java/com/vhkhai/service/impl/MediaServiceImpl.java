package com.vhkhai.service.impl;

import com.vhkhai.port.file.Uploader;
import com.vhkhai.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService {

    private final Uploader uploader;

    @Override
    public String signedUrl(String publicId) {
        return uploader.generateSignedUrl(publicId, Instant.now().getEpochSecond() + 3600);
    }
}
