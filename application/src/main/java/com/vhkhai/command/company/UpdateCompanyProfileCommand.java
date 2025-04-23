package com.vhkhai.command.company;

import an.awesome.pipelinr.Command;
import com.vhkhai.aggrerates.account.Account;
import com.vhkhai.aggrerates.company.Company;
import com.vhkhai.dto.company.CompanyResponseDto;
import com.vhkhai.dto.company.CompanyUpdateProfileRequestDto;
import com.vhkhai.exception.ApplicationErrorCode;
import com.vhkhai.exception.ApplicationException;
import com.vhkhai.mapper.CompanyMapper;
import com.vhkhai.port.UserAuthentication;
import com.vhkhai.repositories.CompanyRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class UpdateCompanyProfileCommand implements Command<CompanyResponseDto> {
    private final UUID companyId;
    private final CompanyUpdateProfileRequestDto requestDto;
}


@Component
@RequiredArgsConstructor
class UpdateCompanyProfileCommandHandler implements Command.Handler<UpdateCompanyProfileCommand, CompanyResponseDto> {

    private final UserAuthentication userAuthentication;
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @Override
    @Transactional
    @PreAuthorize("hasRole('COMPANY')")
    public CompanyResponseDto handle(UpdateCompanyProfileCommand command) {

        var account = userAuthentication.getAuthenticatedUser();

        var company = companyRepository.getById(command.getCompanyId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.COMPANY_NOT_FOUND));

        if (!account.equals(company.getAccount())) {
            throw new ApplicationException(ApplicationErrorCode.ACCESS_DENIED);
        }

        company.updateProfile(command.getRequestDto().getName(),
                command.getRequestDto().getPhone(),
                command.getRequestDto().getAddress());

        Company savedCompany = companyRepository.update(company);

        return companyMapper.toDto(savedCompany);
    }
}

