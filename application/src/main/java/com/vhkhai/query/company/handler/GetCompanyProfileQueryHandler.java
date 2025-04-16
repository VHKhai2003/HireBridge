package com.vhkhai.query.company.handler;

import com.vhkhai.aggrerates.company.Company;
import com.vhkhai.dto.company.CompanyResponseDto;
import com.vhkhai.exception.ApplicationErrorCode;
import com.vhkhai.exception.ApplicationException;
import com.vhkhai.mapper.CompanyMapper;
import com.vhkhai.port.UserAuthentication;
import com.vhkhai.query.company.GetCompanyProfileQuery;
import com.vhkhai.query.iquery.Query;
import com.vhkhai.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetCompanyProfileQueryHandler implements Query.Hanldler<GetCompanyProfileQuery, CompanyResponseDto> {

    private final UserAuthentication userAuthentication;
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @Override
    public CompanyResponseDto handle(GetCompanyProfileQuery query) {
        Company company = companyRepository.findByAccountId(userAuthentication.getAuthenticatedUser().getId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.COMPANY_NOT_FOUND));

        return companyMapper.toDto(company);
    }
}
