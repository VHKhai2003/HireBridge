package com.vhkhai.command.company.handler;

import an.awesome.pipelinr.Command;
import com.vhkhai.aggrerates.account.Account;
import com.vhkhai.aggrerates.company.Company;
import com.vhkhai.command.company.UpdateCompanyProfileCommand;
import com.vhkhai.dto.company.CompanyResponseDto;
import com.vhkhai.exception.ApplicationErrorCode;
import com.vhkhai.exception.ApplicationException;
import com.vhkhai.mapper.CompanyMapper;
import com.vhkhai.port.UserAuthentication;
import com.vhkhai.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UpdateCompanyProfileCommandHandler implements Command.Handler<UpdateCompanyProfileCommand, CompanyResponseDto> {

    private final UserAuthentication userAuthentication;
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @Override
    @Transactional
    public CompanyResponseDto handle(UpdateCompanyProfileCommand command) {

        Account account = userAuthentication.getAuthenticatedUser();

        Company company = companyRepository.getById(command.getCompanyId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.COMPANY_NOT_FOUND));

        if (!account.equals(company.getAccount())) {
            throw new ApplicationException(ApplicationErrorCode.ACCESS_DENIED);
        }

        company.setName(command.getRequestDto().getName());
        company.setPhone(command.getRequestDto().getPhone());
        company.setAddress(command.getRequestDto().getAddress());

        Company savedCompany = companyRepository.update(company);

        return companyMapper.toDto(savedCompany);
    }
}
