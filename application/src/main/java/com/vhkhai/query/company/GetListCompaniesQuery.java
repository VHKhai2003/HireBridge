package com.vhkhai.query.company;

import com.vhkhai.dto.company.CompanyResponseDto;
import com.vhkhai.mapper.CompanyMapper;
import com.vhkhai.query.iquery.Query;
import com.vhkhai.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

public class GetListCompaniesQuery implements Query<List<CompanyResponseDto>> {
}

@Service
@RequiredArgsConstructor
class GetListCompaniesQueryHandler implements Query.Hanldler<GetListCompaniesQuery, List<CompanyResponseDto>> {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @Override
    public List<CompanyResponseDto> handle(GetListCompaniesQuery command) {
        return companyRepository.findAll().stream().map(companyMapper::toDto).toList();
    }
}
