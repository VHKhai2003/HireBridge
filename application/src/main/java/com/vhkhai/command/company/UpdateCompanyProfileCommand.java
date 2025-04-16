package com.vhkhai.command.company;

import an.awesome.pipelinr.Command;
import com.vhkhai.dto.company.CompanyResponseDto;
import com.vhkhai.dto.company.CompanyUpdateProfileRequestDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class UpdateCompanyProfileCommand implements Command<CompanyResponseDto> {
    private final UUID companyId;
    private final CompanyUpdateProfileRequestDto requestDto;
}
