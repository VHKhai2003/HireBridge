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
    private enum AccessMode {
        PUBLIC("public"),
        PRIVATE("private");

        private final String value;

        AccessMode(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
    

    @Override
    public String uploadPublicFile(MultipartFile file, String folder) {
       return uploadFile(file, folder, AccessMode.PUBLIC);
    }

    @Override
    public String uploadPrivateFile(MultipartFile file, String folder) {
        return uploadFile(file, folder, AccessMode.PRIVATE);
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


    @Override
    public String generateSignedUrl(String publicId, long expirationTime) {
        return cloudinary.url()
                .resourceType(determineResourceType(publicId))
                .type("private")
                .secure(true)
                .signed(true)
                .publicId(publicId)
                .generate();
    }

    private String uploadFile(MultipartFile file, String folder, AccessMode accessMode) {
        try {
            Map<String, Object> options = ObjectUtils.asMap(
                    "folder", folder,
                    "resource_type", determineResourceType(file),
                    "format", extractFormat(file),
                    "type", accessMode.getValue()
            );
            Map result = cloudinary.uploader().upload(file.getBytes(), options);
            if(accessMode == AccessMode.PRIVATE) {
                return result.get("public_id").toString();
            }
            return result.get("secure_url").toString();
        }
        catch (IOException exception) {
            throw new InfrastructureException(ErrorCode.FAILED_TO_UPLOAD_FILE);
        }
    }

    private String extractFormat(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null && originalFilename.contains(".")) {
            return originalFilename.substring(originalFilename.lastIndexOf('.') + 1);
        }
        return "bin";
    }

    private String determineResourceType(MultipartFile file) {
        String mimeType = file.getContentType();
        if (mimeType == null) return "raw";

        if (mimeType.startsWith("image/")) {
            return "image";
        } else if (mimeType.startsWith("video/")) {
            return "video";
        } else {
            return "raw"; // default for pdf, doc, zip, v.v.
        }
    }

    private String determineResourceType(String publicId) {
        String extension = publicId.substring(publicId.lastIndexOf('.') + 1);
        if (extension.equals("jpg") || extension.equals("png") || extension.equals("gif")) {
            return "image";
        } else if (extension.equals("mp4")) {
            return "video";
        } else {
            return "raw"; // default for pdf, doc, zip, v.v.
        }
    }

}
