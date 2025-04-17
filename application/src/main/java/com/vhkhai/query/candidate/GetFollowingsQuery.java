package com.vhkhai.query.candidate;

import com.vhkhai.aggrerates.candidate.Candidate;
import com.vhkhai.dto.company.CompanyResponseDto;
import com.vhkhai.exception.ApplicationErrorCode;
import com.vhkhai.exception.ApplicationException;
import com.vhkhai.mapper.CompanyMapper;
import com.vhkhai.port.UserAuthentication;
import com.vhkhai.query.iquery.Query;
import com.vhkhai.repositories.CandidateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

public class GetFollowingsQuery implements Query<List<CompanyResponseDto>> {
}



@Component
@RequiredArgsConstructor
class GetFollowingsQueryHandler implements Query.Handler<GetFollowingsQuery, List<CompanyResponseDto>> {

    private final UserAuthentication userAuthentication;
    private final CandidateRepository candidateRepository;
    private final CompanyMapper companyMapper;

    @Override
    public List<CompanyResponseDto> handle(GetFollowingsQuery command) {

        Candidate candidate = candidateRepository.findByAccountId(userAuthentication.getAuthenticatedUser().getId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.CANDIDATE_NOT_FOUND));
        if (candidate.getFollowings() == null || candidate.getFollowings().isEmpty()) {
            return List.of();
        }

        List<CompanyResponseDto> companyResponseDtos = candidate.getFollowings().stream()
                .map(f -> companyMapper.toDto(f.getCompany()))
                .toList();

        return companyResponseDtos;
    }
}

