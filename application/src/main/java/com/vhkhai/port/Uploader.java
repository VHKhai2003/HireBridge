package com.vhkhai.port;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface Uploader {
    String uploadPublicFile(MultipartFile file, String folder);

    String uploadPrivateFile(MultipartFile file, String folder);

    String deleteFile(String publicId);

    String generateSignedUrl(String publicId, long expirationTime);
}
