package com.vhkhai.port.file;

import org.springframework.web.multipart.MultipartFile;

public interface Uploader {
    String uploadPublicFile(MultipartFile file, String folder);

    String uploadPrivateFile(MultipartFile file, String folder);

    String deleteFile(String publicId);

    String generateSignedUrl(String publicId, long expirationTime);
}
