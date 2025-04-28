package com.vhkhai.command.company;

import an.awesome.pipelinr.Command;
import com.vhkhai.aggrerates.company.Company;
import com.vhkhai.dto.company.CompanyResponseDto;
import com.vhkhai.dto.company.CompanyUpdateInfoRequestDto;
import com.vhkhai.exception.ApplicationErrorCode;
import com.vhkhai.exception.ApplicationException;
import com.vhkhai.mapper.CompanyMapper;
import com.vhkhai.port.auth.UserAuthentication;
import com.vhkhai.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public record UpdateCompanyInfoCommand(
        UUID companyId,
        CompanyUpdateInfoRequestDto requestDto) implements Command<CompanyResponseDto> {
}


@Component
@RequiredArgsConstructor
class UpdateCompanyInfoCommandHandler implements Command.Handler<UpdateCompanyInfoCommand, CompanyResponseDto> {

    private final UserAuthentication userAuthentication;
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @Override
    @Transactional
    @PreAuthorize("hasRole('COMPANY')")
    @CachePut(value = "company", key = "#command.companyId")
    public CompanyResponseDto handle(UpdateCompanyInfoCommand command) {

        var account = userAuthentication.getAuthenticatedUser();

        var company = companyRepository.findById(command.companyId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.COMPANY_NOT_FOUND));

        if (!account.equals(company.getAccount())) {
            throw new ApplicationException(ApplicationErrorCode.ACCESS_DENIED);
        }

        company.updateInfo(command.requestDto().getName(),
                command.requestDto().getPhone(),
                command.requestDto().getAddress());

        Company savedCompany = companyRepository.update(company);

        return companyMapper.toDto(savedCompany);
    }
}

