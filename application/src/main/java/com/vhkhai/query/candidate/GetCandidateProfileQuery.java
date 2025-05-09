package com.vhkhai.query.candidate;

import com.vhkhai.dto.candidate.CandidateResponseDto;
import com.vhkhai.exception.ApplicationErrorCode;
import com.vhkhai.exception.ApplicationException;
import com.vhkhai.mapper.CandidateMapper;
import com.vhkhai.query.iquery.Query;
import com.vhkhai.repositories.CandidateRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class GetCandidateProfileQuery implements Query<CandidateResponseDto> {
    private final UUID accountId;
}

@Component
@RequiredArgsConstructor
class GetCandidateProfileQueryHandler implements Query.Hanldler<GetCandidateProfileQuery, CandidateResponseDto> {

    private final CandidateRepository candidateRepository;
    private final CandidateMapper candidateMapper;

    @Override
    @PreAuthorize("hasRole('CANDIDATE')")
    public CandidateResponseDto handle(GetCandidateProfileQuery query) {
        var candidate = candidateRepository.findByAccountId(query.getAccountId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.CANDIDATE_NOT_FOUND));

        return candidateMapper.toDto(candidate);
    }
}