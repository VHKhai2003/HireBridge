package com.vhkhai.command.candidate;

import an.awesome.pipelinr.Command;
import com.vhkhai.aggrerates.candidate.Candidate;
import com.vhkhai.aggrerates.company.Company;
import com.vhkhai.exception.ApplicationErrorCode;
import com.vhkhai.exception.ApplicationException;
import com.vhkhai.port.UserAuthentication;
import com.vhkhai.repositories.CandidateRepository;
import com.vhkhai.repositories.CompanyRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class FollowCompanyCommand implements Command<Void> {
    private final UUID companyId;
}


@RequiredArgsConstructor
@Component
class FollowCompanyCommandHandler implements Command.Handler<FollowCompanyCommand, Void> {

    private final CandidateRepository candidateRepository;
    private final CompanyRepository companyRepository;
    private final UserAuthentication userAuthentication;

    @Transactional
    @Override
    public Void handle(FollowCompanyCommand command) {
        Candidate candidate = candidateRepository.findByAccountId(userAuthentication.getAuthenticatedUser().getId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.CANDIDATE_NOT_FOUND));
        Company company = companyRepository.getById(command.getCompanyId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.COMPANY_NOT_FOUND));

        candidate.followCompany(company);

        candidateRepository.update(candidate);
        return null;
    }

}
