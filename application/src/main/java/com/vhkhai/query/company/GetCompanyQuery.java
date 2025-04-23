package com.vhkhai.query.company;

import com.vhkhai.dto.company.CompanyResponseDto;
import com.vhkhai.exception.ApplicationErrorCode;
import com.vhkhai.exception.ApplicationException;
import com.vhkhai.mapper.CompanyMapper;
import com.vhkhai.query.iquery.Query;
import com.vhkhai.repositories.CompanyRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class GetCompanyQuery implements Query<CompanyResponseDto> {
    private final UUID companyId;
}

@Component
@RequiredArgsConstructor
class GetCompanyQueryHandler implements Query.Hanldler<GetCompanyQuery, CompanyResponseDto> {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @Override
    public CompanyResponseDto handle(GetCompanyQuery query) {
        var company = companyRepository.getById(query.getCompanyId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.COMPANY_NOT_FOUND));
        return companyMapper.toDto(company);
    }
}
