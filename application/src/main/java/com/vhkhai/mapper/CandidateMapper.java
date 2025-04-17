package com.vhkhai.mapper;

import com.vhkhai.aggrerates.candidate.Candidate;
import com.vhkhai.aggrerates.candidate.Following;
import com.vhkhai.dto.candidate.CandidateResponseDto;
import com.vhkhai.dto.company.CompanyResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CandidateMapper {
    CandidateResponseDto toDto(Candidate candidate);

//    @Mapping(source = "id", target = "candidateId")
//    @Mapping(source = "followings", target = "followings")
//    FollowingListResponseDto toFollowingListResponseDto(Candidate candidate);
//
//    default List<CompanyResponseDto> mapFollowingsToCompanies(List<Following> followings) {
//        return followings.stream()
//                .map(f -> CompanyResponseDto.builder()
//                        .id(f.getCompany().getId())
//                        .name(f.getCompany().getName())
//                        .email(f.getCompany().getEmail())
//                        .phone(f.getCompany().getPhone())
//                        .address(f.getCompany().getAddress())
//                        .build())
//                .toList();
//    }
}
