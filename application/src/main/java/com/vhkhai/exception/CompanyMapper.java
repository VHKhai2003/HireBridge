package com.vhkhai.exception;

import com.vhkhai.aggrerates.company.Company;
import com.vhkhai.dto.company.CompanyResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    CompanyResponseDto toDto(Company company);
}
