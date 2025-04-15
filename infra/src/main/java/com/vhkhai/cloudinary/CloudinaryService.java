package com.vhkhai.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.vhkhai.exception.InfrastructureException;
import com.vhkhai.exception.ErrorCode;
import com.vhkhai.port.Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CloudinaryService implements Uploader {

    private final Cloudinary cloudinary;

    @Override
    public String uploadFile(MultipartFile file, String folder) {
        try {
            Map<String, Object> options = ObjectUtils.asMap(
                    "folder", folder,
                    "resource_type", "raw",
                    "format", "pdf",
                    "overwrite", true,
                    "access_mode", "public"
            );
            Map result = cloudinary.uploader().upload(file.getBytes(), options);
            return result.get("secure_url").toString();
        }
        catch (IOException exception) {
            throw new InfrastructureException(ErrorCode.FAILED_TO_UPLOAD_FILE);
        }
    }

    @Override
    public String deleteFile(String publicId) {
        try {
            Map result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            log.info("Delete file result: {}", result);
            // result just is {result=ok}
            return result.get("result").toString();
        }
        catch (IOException exception) {
            throw new InfrastructureException(ErrorCode.FAILED_TO_DELETE_FILE);
        }
    }
}
