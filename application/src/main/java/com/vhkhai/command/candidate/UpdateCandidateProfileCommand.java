package com.vhkhai.command.candidate;

import an.awesome.pipelinr.Command;
import com.vhkhai.dto.candidate.CandidateResponseDto;
import com.vhkhai.dto.candidate.CandidateUpdateProfileRequestDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class UpdateCandidateProfileCommand implements Command<CandidateResponseDto> {
    private final UUID candidateId;
    private final CandidateUpdateProfileRequestDto requestDto;
}
