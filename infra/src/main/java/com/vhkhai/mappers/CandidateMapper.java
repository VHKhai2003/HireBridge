package com.vhkhai.mappers;

import com.vhkhai.aggrerates.candidate.Candidate;
import com.vhkhai.aggrerates.candidate.CandidateFollowing;
import com.vhkhai.entities.account.AccountEntity;
import com.vhkhai.entities.candidate.CandidateEntity;
import com.vhkhai.entities.candidate.CandidateFollowingEntity;
import com.vhkhai.entities.company.CompanyEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class CandidateMapper {

    public Candidate toCandidate(CandidateEntity entity) {
        if (entity == null) {
            return null;
        }

        List<UUID> companyIds = entity.getCandidateFollowings()
                .stream()
                .map(candidateFollowingEntity -> candidateFollowingEntity.getCompany().getId())
                .toList();

        CandidateFollowing following = new CandidateFollowing(companyIds);
        Candidate candidate = Candidate.builder()
                .id(entity.getId())
                .accountId(entity.getAccount().getId())
                .email(entity.getEmail())
                .fullName(entity.getFullName())
                .phone(entity.getPhone())
                .cvLink(entity.getCvLink())
                .candidateFollowing(following)
                .build();
        return candidate;
    }

    public CandidateEntity toCandidateEntity(Candidate candidate) {
        if (candidate == null) {
            return null;
        }

        AccountEntity accountEntity = AccountEntity.builder()
                .id(candidate.getAccountId())
                .build();

        List<CandidateFollowingEntity> candidateFollowings = candidate.getCandidateFollowing()
                .getCompanyIds()
                .stream()
                .map(companyId ->
                        CandidateFollowingEntity.builder()
                                .company(CompanyEntity.builder().id(companyId).build())
                                .candidate(CandidateEntity.builder().id(candidate.getId()).build())
                                .build()).
                toList();

        CandidateEntity entity = CandidateEntity.builder()
                .id(candidate.getId())
                .account(accountEntity)
                .email(candidate.getEmail())
                .fullName(candidate.getFullName())
                .phone(candidate.getPhone())
                .cvLink(candidate.getCvLink())
                .candidateFollowings(candidateFollowings)
                .build();

        return entity;
    }
}
