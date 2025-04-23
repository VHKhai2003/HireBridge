package com.vhkhai.command.company;

import an.awesome.pipelinr.Command;
import com.vhkhai.exception.ApplicationErrorCode;
import com.vhkhai.exception.ApplicationException;
import com.vhkhai.port.UserAuthentication;
import com.vhkhai.repositories.CompanyRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class AddJobPostingCommand implements Command<Boolean> {
    private final UUID companyId;
    private final String title;
    private final String requirement;
}

@Service
@RequiredArgsConstructor
class AddJobPostingCommandHandler implements Command.Handler<AddJobPostingCommand, Boolean> {

    private final UserAuthentication userAuthentication;
    private final CompanyRepository companyRepository;

    @PreAuthorize("hasRole('COMPANY')")
    @Transactional
    @Override
    public Boolean handle(AddJobPostingCommand command) {
        var company = companyRepository.findByAccountId(userAuthentication.getAuthenticatedUser().getId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.COMPANY_NOT_FOUND));
        if(!command.getCompanyId().equals(company.getId())) {
            throw new ApplicationException(ApplicationErrorCode.ACCESS_DENIED);
        }
        company.addJobPosting(command.getTitle(), command.getRequirement());
        companyRepository.update(company);
        return true;
    }
}

