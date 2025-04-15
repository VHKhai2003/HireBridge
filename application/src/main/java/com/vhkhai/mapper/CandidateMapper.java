package com.vhkhai.mapper;

import com.vhkhai.aggrerates.candidate.Candidate;
import com.vhkhai.dto.candidate.CandidateResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CandidateMapper {
    CandidateResponseDto toDto(Candidate candidate);
}
