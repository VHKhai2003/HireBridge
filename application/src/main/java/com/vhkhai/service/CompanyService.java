package com.vhkhai.service;

import com.vhkhai.dto.company.CompanyResponseDto;
import com.vhkhai.dto.company.CompanyUpdateProfileRequestDto;

import java.util.UUID;

public interface CompanyService {
    CompanyResponseDto getCompanyById(UUID id);
    CompanyResponseDto getCompanyByAccountId(UUID accountId);
    CompanyResponseDto updateCompanyProfile(UUID id, CompanyUpdateProfileRequestDto requestDto);
}
