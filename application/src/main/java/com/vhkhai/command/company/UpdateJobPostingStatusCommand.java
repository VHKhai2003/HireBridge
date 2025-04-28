package com.vhkhai.command.company;

import an.awesome.pipelinr.Command;
import com.vhkhai.aggrerates.account.Account;
import com.vhkhai.enumerations.JobPostingStatus;
import com.vhkhai.exception.ApplicationErrorCode;
import com.vhkhai.exception.ApplicationException;
import com.vhkhai.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public record UpdateJobPostingStatusCommand
        (UUID companyId, UUID jobPostingId, JobPostingStatus status, Account account) implements Command<Void> {
}

@Service
@RequiredArgsConstructor
class UpdateJobPostingStatusCommandHandler implements Command.Handler<UpdateJobPostingStatusCommand, Void> {

    private final CompanyRepository companyRepository;

    @Transactional
    @PreAuthorize("hasRole('COMPANY')")
    @CacheEvict(value = "jobPosting", key = "#command.jobPostingId")
    @Override
    public Void handle(UpdateJobPostingStatusCommand command) {
        var company = companyRepository.findById(command.companyId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.COMPANY_NOT_FOUND));
        if(!company.getAccount().equals(command.account())) {
            throw new ApplicationException(ApplicationErrorCode.ACCESS_DENIED);
        }
        company.updateJobPostingStatus(command.jobPostingId(), command.status());
        companyRepository.update(company);
        return null;
    }
}
