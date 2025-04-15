package com.vhkhai.service.impl;

import com.vhkhai.aggrerates.account.Account;
import com.vhkhai.aggrerates.candidate.Candidate;
import com.vhkhai.dto.candidate.CandidateResponseDto;
import com.vhkhai.dto.candidate.CandidateUpdateProfileRequestDto;
import com.vhkhai.exception.ApplicationErrorCode;
import com.vhkhai.exception.ApplicationException;
import com.vhkhai.mapper.CandidateMapper;
import com.vhkhai.port.Uploader;
import com.vhkhai.port.UserAuthentication;
import com.vhkhai.repositories.CandidateRepository;
import com.vhkhai.service.CandidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository candidateRepository;
    private final CandidateMapper candidateMapper;
    private final UserAuthentication authenticatedUserProvider;
    private final Uploader uploader;

    @Override
    public CandidateResponseDto getCandidateById(UUID id) {
        Candidate candidate = candidateRepository.getById(id)
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.CANDIDATE_NOT_FOUND));
        return candidateMapper.toDto(candidate);
    }

    @Override
    public CandidateResponseDto getCandidateByAccountId(UUID accountId) {
        Candidate candidate = candidateRepository.findByAccountId(accountId)
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.CANDIDATE_NOT_FOUND));
        return candidateMapper.toDto(candidate);
    }

    @Override
    @Transactional
    public CandidateResponseDto updateProfile(UUID id, CandidateUpdateProfileRequestDto requestDto) {
        Account account = authenticatedUserProvider.getAuthenticatedUser();

        Candidate candidate = candidateRepository.getById(id)
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.CANDIDATE_NOT_FOUND));

        if (!account.equals(candidate.getAccount())) {
            throw new ApplicationException(ApplicationErrorCode.ACCESS_DENIED);
        }

        candidate.setFullName(requestDto.getFullName());
        candidate.setPhone(requestDto.getPhone());

        Candidate savedCandidate = candidateRepository.update(candidate);

        return candidateMapper.toDto(savedCandidate);
    }

    @Override
    @Transactional
    public CandidateResponseDto uploadCV(UUID id, MultipartFile file) {
        Account account = authenticatedUserProvider.getAuthenticatedUser();
        Candidate candidate = candidateRepository.getById(id)
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.CANDIDATE_NOT_FOUND));
        if (!account.equals(candidate.getAccount())) {
            throw new ApplicationException(ApplicationErrorCode.ACCESS_DENIED);
        }

        if (file.isEmpty()) {
            throw new ApplicationException(ApplicationErrorCode.INVALID_FILE);
        }

        String cvLink = uploader.uploadFile(file, "cv");
        candidate.setCvLink(cvLink);

        candidateRepository.update(candidate);

        return candidateMapper.toDto(candidate);
    }
}
