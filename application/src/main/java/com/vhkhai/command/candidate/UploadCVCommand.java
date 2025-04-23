package com.vhkhai.command.candidate;

import an.awesome.pipelinr.Command;
import com.vhkhai.exception.ApplicationErrorCode;
import com.vhkhai.exception.ApplicationException;
import com.vhkhai.port.Uploader;
import com.vhkhai.port.UserAuthentication;
import com.vhkhai.repositories.CandidateRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class UploadCVCommand implements Command<String> {
    private final UUID candidateId;
    private final MultipartFile file;
}


@RequiredArgsConstructor
@Component
class UploadCVCommandHandler implements Command.Handler<UploadCVCommand, String> {

    private final UserAuthentication userAuthentication;
    private final CandidateRepository candidateRepository;
    private final Uploader uploader;

    private static final List<String> allowedTypes = List.of("application/pdf");

    @Override
    @Transactional
    @PreAuthorize("hasRole('CANDIDATE')")
    public String handle(UploadCVCommand command) {

        var account = userAuthentication.getAuthenticatedUser();
        var candidate = candidateRepository.getById(command.getCandidateId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.CANDIDATE_NOT_FOUND));
        if (!account.equals(candidate.getAccount())) {
            throw new ApplicationException(ApplicationErrorCode.ACCESS_DENIED);
        }

        // validation
        if (!allowedTypes.contains(command.getFile().getContentType())) {
            throw new ApplicationException(ApplicationErrorCode.PDF_FILE_IS_REQUIRED);
        }
        if (command.getFile().isEmpty()) {
            throw new ApplicationException(ApplicationErrorCode.INVALID_FILE);
        }
        var publicId = uploader.uploadPrivateFile(command.getFile(), "cv");
        candidate.updateCV(publicId);
        candidateRepository.update(candidate);
        return publicId;
    }
}

