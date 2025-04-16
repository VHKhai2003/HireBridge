package com.vhkhai.command.candidate.handler;

import an.awesome.pipelinr.Command;
import com.vhkhai.aggrerates.account.Account;
import com.vhkhai.aggrerates.candidate.Candidate;
import com.vhkhai.command.candidate.UpdateCandidateProfileCommand;
import com.vhkhai.dto.candidate.CandidateResponseDto;
import com.vhkhai.exception.ApplicationErrorCode;
import com.vhkhai.exception.ApplicationException;
import com.vhkhai.mapper.CandidateMapper;
import com.vhkhai.port.UserAuthentication;
import com.vhkhai.repositories.CandidateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UpdateCandidateProfileCommandHandler implements Command.Handler<UpdateCandidateProfileCommand, CandidateResponseDto> {

    private final CandidateRepository candidateRepository;
    private final CandidateMapper candidateMapper;
    private final UserAuthentication userAuthentication;

    @Override
    @Transactional
    public CandidateResponseDto handle(UpdateCandidateProfileCommand command) {
        Account account = userAuthentication.getAuthenticatedUser();

        Candidate candidate = candidateRepository.getById(command.getCandidateId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.CANDIDATE_NOT_FOUND));

        if (!account.equals(candidate.getAccount())) {
            throw new ApplicationException(ApplicationErrorCode.ACCESS_DENIED);
        }

        candidate.setFullName(command.getRequestDto().getFullName());
        candidate.setPhone(command.getRequestDto().getPhone());

        Candidate savedCandidate = candidateRepository.update(candidate);

        return candidateMapper.toDto(savedCandidate);
    }
}
