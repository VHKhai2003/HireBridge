package com.vhkhai.command.candidate;

import an.awesome.pipelinr.Command;
import com.vhkhai.exception.ApplicationErrorCode;
import com.vhkhai.exception.ApplicationException;
import com.vhkhai.repositories.CandidateRepository;
import com.vhkhai.repositories.CompanyRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public record UnFollowCompanyCommand(UUID companyId, UUID accountId) implements Command<Void> {
}

@Service
@RequiredArgsConstructor
class UnFollowCompanyCommandHandler implements Command.Handler<UnFollowCompanyCommand, Void> {

    private final CandidateRepository candidateRepository;
    private final CompanyRepository companyRepository;

    @Transactional
    @PreAuthorize("hasRole('CANDIDATE')")
    @Override
    public Void handle(UnFollowCompanyCommand command) {
        var company = companyRepository.findById(command.companyId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.COMPANY_NOT_FOUND));
        var candidate = candidateRepository.findByAccountId(command.accountId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.CANDIDATE_NOT_FOUND));
        candidate.unfollowCompany(company);
        candidateRepository.update(candidate);
        return null;
    }
}

