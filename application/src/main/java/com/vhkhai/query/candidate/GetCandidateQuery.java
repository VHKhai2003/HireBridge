package com.vhkhai.query.candidate;

import com.vhkhai.dto.candidate.CandidateResponseDto;
import com.vhkhai.exception.ApplicationErrorCode;
import com.vhkhai.exception.ApplicationException;
import com.vhkhai.mapper.CandidateMapper;
import com.vhkhai.query.iquery.Query;
import com.vhkhai.repositories.CandidateRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class GetCandidateQuery implements Query<CandidateResponseDto> {
    private final UUID candidateId;
}

@Component
@RequiredArgsConstructor
class GetCandidateQueryHandler implements Query.Hanldler<GetCandidateQuery, CandidateResponseDto> {

    private final CandidateRepository candidateRepository;
    private final CandidateMapper candidateMapper;

    @Override
    public CandidateResponseDto handle(GetCandidateQuery query) {
        var candidate = candidateRepository.getById(query.getCandidateId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.CANDIDATE_NOT_FOUND));
        return candidateMapper.toDto(candidate);
    }
}