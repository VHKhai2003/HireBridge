package com.vhkhai.query.candidate;

import com.vhkhai.aggrerates.account.Account;
import com.vhkhai.dto.candidate.CandidateResponseDto;
import com.vhkhai.exception.ApplicationErrorCode;
import com.vhkhai.exception.ApplicationException;
import com.vhkhai.mapper.CandidateMapper;
import com.vhkhai.query.iquery.Query;
import com.vhkhai.repositories.CandidateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.UUID;

public record GetCandidateQuery(UUID candidateId, Account account) implements Query<CandidateResponseDto> {
}

@Component
@RequiredArgsConstructor
class GetCandidateQueryHandler implements Query.Hanldler<GetCandidateQuery, CandidateResponseDto> {

    private final CandidateRepository candidateRepository;
    private final CandidateMapper candidateMapper;

    @Override
    @PreAuthorize("hasRole('CANDIDATE')")
    public CandidateResponseDto handle(GetCandidateQuery query) {
        var candidate = candidateRepository.findById(query.candidateId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.CANDIDATE_NOT_FOUND));
        var candidateDto = candidateMapper.toDto(candidate);
        if(!candidate.getAccount().equals(query.account())) {
            candidateDto.setCv(null);
        }
        return candidateDto;
    }
}