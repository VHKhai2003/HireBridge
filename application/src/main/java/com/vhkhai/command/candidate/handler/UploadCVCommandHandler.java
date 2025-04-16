package com.vhkhai.command.candidate.handler;

import an.awesome.pipelinr.Command;
import com.vhkhai.aggrerates.account.Account;
import com.vhkhai.aggrerates.candidate.Candidate;
import com.vhkhai.command.candidate.UploadCVCommand;
import com.vhkhai.dto.candidate.CandidateResponseDto;
import com.vhkhai.exception.ApplicationErrorCode;
import com.vhkhai.exception.ApplicationException;
import com.vhkhai.mapper.CandidateMapper;
import com.vhkhai.port.Uploader;
import com.vhkhai.port.UserAuthentication;
import com.vhkhai.repositories.CandidateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class UploadCVCommandHandler implements Command.Handler<UploadCVCommand, CandidateResponseDto> {

    private final UserAuthentication userAuthentication;
    private final CandidateRepository candidateRepository;
    private final CandidateMapper candidateMapper;
    private final Uploader uploader;

    @Override
    @Transactional
    public CandidateResponseDto handle(UploadCVCommand command) {

        Account account = userAuthentication.getAuthenticatedUser();
        Candidate candidate = candidateRepository.getById(command.getCandidateId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.CANDIDATE_NOT_FOUND));
        if (!account.equals(candidate.getAccount())) {
            throw new ApplicationException(ApplicationErrorCode.ACCESS_DENIED);
        }

        if (command.getFile().isEmpty()) {
            throw new ApplicationException(ApplicationErrorCode.INVALID_FILE);
        }

        String cvLink = uploader.uploadFile(command.getFile(), "cv");
        candidate.setCvLink(cvLink);

        candidateRepository.update(candidate);

        return candidateMapper.toDto(candidate);
    }
}
