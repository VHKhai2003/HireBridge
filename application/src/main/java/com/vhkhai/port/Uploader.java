package com.vhkhai.port;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface Uploader {
    String uploadFile(MultipartFile file, String folder);

    String deleteFile(String publicId);
}
