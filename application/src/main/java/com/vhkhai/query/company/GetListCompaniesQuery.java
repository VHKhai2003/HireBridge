package com.vhkhai.query.company;

import com.vhkhai.dto.company.CompanyResponseDto;
import com.vhkhai.mapper.CompanyMapper;
import com.vhkhai.query.iquery.Query;
import com.vhkhai.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

public record GetListCompaniesQuery(String keyword, int page, int size) implements Query<Page<CompanyResponseDto>> {
}

@Service
@RequiredArgsConstructor
@Slf4j
class GetListCompaniesQueryHandler implements Query.Hanldler<GetListCompaniesQuery, Page<CompanyResponseDto>> {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @Override
    public Page<CompanyResponseDto> handle(GetListCompaniesQuery query) {
        var pageable = PageRequest.of(query.page() - 1, query.size());
        if (query.keyword() != null && !query.keyword().isBlank()) {
            return companyRepository.findByNameContaining(query.keyword(), pageable).map(companyMapper::toDto);
        }
        return companyRepository.findAll(pageable).map(companyMapper::toDto);
    }
}
