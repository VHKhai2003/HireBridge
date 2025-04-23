package com.vhkhai.query.candidate;

import com.vhkhai.dto.company.CompanyResponseDto;
import com.vhkhai.exception.ApplicationErrorCode;
import com.vhkhai.exception.ApplicationException;
import com.vhkhai.mapper.CompanyMapper;
import com.vhkhai.query.iquery.Query;
import com.vhkhai.repositories.CandidateRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class GetFollowedCompaniesQuery implements Query<List<CompanyResponseDto>> {
    private final UUID accountId;
}

@Component
@RequiredArgsConstructor
class GetFollowedCompaniesQueryHandler implements Query.Handler<GetFollowedCompaniesQuery, List<CompanyResponseDto>> {

    private final CandidateRepository candidateRepository;
    private final CompanyMapper companyMapper;

    @Override
    @PreAuthorize("hasRole('CANDIDATE')")
    public List<CompanyResponseDto> handle(GetFollowedCompaniesQuery query) {
        var candidate = candidateRepository.findByAccountId(query.getAccountId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.CANDIDATE_NOT_FOUND));
        if (candidate.getFollowings() == null || candidate.getFollowings().isEmpty()) {
            return List.of();
        }
        var companyResponseDtos = candidate.getFollowings().stream()
                .map(f -> companyMapper.toDto(f.getCompany()))
                .toList();
        return companyResponseDtos;
    }
}

