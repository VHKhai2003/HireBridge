package com.vhkhai.command.company;

import an.awesome.pipelinr.Command;
import com.vhkhai.aggrerates.company.Company;
import com.vhkhai.exception.ApplicationErrorCode;
import com.vhkhai.exception.ApplicationException;
import com.vhkhai.mapper.JobPostingMapper;
import com.vhkhai.port.UserAuthentication;
import com.vhkhai.repositories.CompanyRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Getter
public class AddJobPostingCommand implements Command<Boolean> {
    private final String title;
    private final String requirement;
}

@Service
@RequiredArgsConstructor
class AddJobPostingCommandHandler implements Command.Handler<AddJobPostingCommand, Boolean> {

    private final UserAuthentication userAuthentication;
    private final CompanyRepository companyRepository;

    @Override
    public Boolean handle(AddJobPostingCommand command) {
        Company company = companyRepository.findByAccountId(userAuthentication.getAuthenticatedUser().getId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.COMPANY_NOT_FOUND));
        company.addJobPosting(command.getTitle(), command.getRequirement());
        companyRepository.update(company);

        return true;
    }
}

