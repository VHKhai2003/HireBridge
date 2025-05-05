package com.vhkhai.command.candidate;

import an.awesome.pipelinr.Command;
import com.vhkhai.aggrerates.account.Account;
import com.vhkhai.aggrerates.candidate.Candidate;
import com.vhkhai.dto.candidate.CandidateResponseDto;
import com.vhkhai.dto.candidate.CandidateUpdateProfileRequestDto;
import com.vhkhai.exception.ApplicationErrorCode;
import com.vhkhai.exception.ApplicationException;
import com.vhkhai.mapper.CandidateMapper;
import com.vhkhai.port.auth.UserAuthentication;
import com.vhkhai.repositories.CandidateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public record UpdateCandidateProfileCommand(UUID candidateId, CandidateUpdateProfileRequestDto requestDto)
        implements Command<CandidateResponseDto> {
}


@Component
@RequiredArgsConstructor
class UpdateCandidateProfileCommandHandler implements Command.Handler<UpdateCandidateProfileCommand, CandidateResponseDto> {

    private final CandidateRepository candidateRepository;
    private final CandidateMapper candidateMapper;
    private final UserAuthentication userAuthentication;

    @Override
    @Transactional
    @PreAuthorize("hasRole('CANDIDATE')")
    public CandidateResponseDto handle(UpdateCandidateProfileCommand command) {
        Account account = userAuthentication.getAuthenticatedUser();

        Candidate candidate = candidateRepository.findById(command.candidateId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.CANDIDATE_NOT_FOUND));

        if (!account.equals(candidate.getAccount())) {
            throw new ApplicationException(ApplicationErrorCode.ACCESS_DENIED);
        }


        candidate.updateProfile(command.requestDto().getFullName(), command.requestDto().getPhone());

        Candidate savedCandidate = candidateRepository.update(candidate);
        return candidateMapper.toDto(savedCandidate);
    }
}
