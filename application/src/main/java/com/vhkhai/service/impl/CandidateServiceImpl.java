package com.vhkhai.service.impl;

import com.vhkhai.aggrerates.account.Account;
import com.vhkhai.aggrerates.candidate.Candidate;
import com.vhkhai.dto.candidate.CandidateResponseDto;
import com.vhkhai.dto.candidate.CandidateUpdateProfileRequestDto;
import com.vhkhai.exception.ApplicationErrorCode;
import com.vhkhai.exception.ApplicationException;
import com.vhkhai.mapper.CandidateMapper;
import com.vhkhai.port.UserAuthentication;
import com.vhkhai.repositories.CandidateRepository;
import com.vhkhai.service.CandidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository candidateRepository;
    private final CandidateMapper candidateMapper;
    private final UserAuthentication authenticatedUserProvider;

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
}
