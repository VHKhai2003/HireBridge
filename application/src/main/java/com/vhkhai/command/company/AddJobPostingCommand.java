package com.vhkhai.command.company;

import an.awesome.pipelinr.Command;
import com.vhkhai.exception.ApplicationErrorCode;
import com.vhkhai.exception.ApplicationException;
import com.vhkhai.port.UserAuthentication;
import com.vhkhai.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public record AddJobPostingCommand(UUID companyId, String title, String requirement) implements Command<Boolean> {
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
        if(!command.companyId().equals(company.getId())) {
            throw new ApplicationException(ApplicationErrorCode.ACCESS_DENIED);
        }
        company.addJobPosting(command.title(), command.requirement());
        companyRepository.update(company);
        return true;
    }
}

