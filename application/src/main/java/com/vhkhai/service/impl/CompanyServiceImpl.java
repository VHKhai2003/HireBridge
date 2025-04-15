package com.vhkhai.service.impl;

import com.vhkhai.aggrerates.account.Account;
import com.vhkhai.aggrerates.company.Company;
import com.vhkhai.dto.company.CompanyResponseDto;
import com.vhkhai.dto.company.CompanyUpdateProfileRequestDto;
import com.vhkhai.exception.ApplicationErrorCode;
import com.vhkhai.exception.ApplicationException;
import com.vhkhai.exception.CompanyMapper;
import com.vhkhai.port.UserAuthentication;
import com.vhkhai.repositories.CompanyRepository;
import com.vhkhai.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final UserAuthentication userAuthenticationProvider;
    private final CompanyMapper mapper;

    @Override
    public CompanyResponseDto getCompanyById(UUID id) {
        Company company = companyRepository.getById(id)
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.COMPANY_NOT_FOUND));
        return mapper.toDto(company);
    }

    @Override
    public CompanyResponseDto getCompanyByAccountId(UUID accountId) {
        Company company = companyRepository.findByAccountId(accountId)
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.COMPANY_NOT_FOUND));
        return mapper.toDto(company);
    }

    @Override
    public CompanyResponseDto updateCompanyProfile(UUID id, CompanyUpdateProfileRequestDto requestDto) {
        Account account = userAuthenticationProvider.getAuthenticatedUser();

        Company company = companyRepository.getById(id)
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.COMPANY_NOT_FOUND));

        if (!account.equals(company.getAccount())) {
            throw new ApplicationException(ApplicationErrorCode.ACCESS_DENIED);
        }

        company.setName(requestDto.getName());
        company.setPhone(requestDto.getPhone());
        company.setAddress(requestDto.getAddress());

        Company savedCompany = companyRepository.update(company);

        return mapper.toDto(savedCompany);
    }
}
