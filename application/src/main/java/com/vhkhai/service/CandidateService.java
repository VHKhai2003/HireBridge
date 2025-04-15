package com.vhkhai.service;

import com.vhkhai.dto.candidate.CandidateResponseDto;
import com.vhkhai.dto.candidate.CandidateUpdateProfileRequestDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface CandidateService {
    CandidateResponseDto getCandidateById(UUID id);
    CandidateResponseDto getCandidateByAccountId(UUID accountId);
    CandidateResponseDto updateProfile(UUID id, CandidateUpdateProfileRequestDto requestDto);
    CandidateResponseDto uploadCV(UUID id, MultipartFile file);
}
