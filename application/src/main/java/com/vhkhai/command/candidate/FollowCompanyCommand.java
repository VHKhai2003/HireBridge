package com.vhkhai.command.candidate;

import an.awesome.pipelinr.Command;
import com.vhkhai.exception.ApplicationErrorCode;
import com.vhkhai.exception.ApplicationException;
import com.vhkhai.repositories.CandidateRepository;
import com.vhkhai.repositories.CompanyRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class FollowCompanyCommand implements Command<Void> {
    private final UUID companyId;
    private final UUID accountId;
}


@RequiredArgsConstructor
@Component
class FollowCompanyCommandHandler implements Command.Handler<FollowCompanyCommand, Void> {

    private final CandidateRepository candidateRepository;
    private final CompanyRepository companyRepository;

    @Transactional
    @PreAuthorize("hasRole('CANDIDATE')")
    @Override
    public Void handle(FollowCompanyCommand command) {
        var company = companyRepository.getById(command.getCompanyId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.COMPANY_NOT_FOUND));
        var candidate = candidateRepository.findByAccountId(command.getAccountId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.CANDIDATE_NOT_FOUND));
        candidate.followCompany(company);
        candidateRepository.update(candidate);
        return null;
    }

}
