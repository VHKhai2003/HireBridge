package com.vhkhai.query.candidate;

import com.vhkhai.aggrerates.candidate.Candidate;
import com.vhkhai.dto.candidate.CandidateResponseDto;
import com.vhkhai.exception.ApplicationErrorCode;
import com.vhkhai.exception.ApplicationException;
import com.vhkhai.mapper.CandidateMapper;
import com.vhkhai.port.UserAuthentication;
import com.vhkhai.query.iquery.Query;
import com.vhkhai.repositories.CandidateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

public class GetCandidateProfileQuery implements Query<CandidateResponseDto> {
}

@Component
@RequiredArgsConstructor
class GetCandidateProfileQueryHandler implements Query.Hanldler<GetCandidateProfileQuery, CandidateResponseDto> {

    private final CandidateRepository candidateRepository;
    private final CandidateMapper candidateMapper;
    private final UserAuthentication userAuthentication;

    @Override
    public CandidateResponseDto handle(GetCandidateProfileQuery query) {
        Candidate candidate = candidateRepository.findByAccountId(userAuthentication.getAuthenticatedUser().getId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.CANDIDATE_NOT_FOUND));

        return candidateMapper.toDto(candidate);
    }
}