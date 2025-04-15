package com.vhkhai.service;

import com.vhkhai.aggrerates.account.Account;
import com.vhkhai.aggrerates.candidate.Candidate;
import com.vhkhai.dto.candidate.CandidateResponseDto;
import com.vhkhai.dto.candidate.CandidateUpdateProfileRequestDto;

import java.util.UUID;

public interface CandidateService {
    CandidateResponseDto getCandidateById(UUID id);
    CandidateResponseDto getCandidateByAccountId(UUID accountId);
    CandidateResponseDto updateProfile(UUID id, CandidateUpdateProfileRequestDto requestDto);
}
