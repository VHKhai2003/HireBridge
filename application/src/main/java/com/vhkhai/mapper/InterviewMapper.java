package com.vhkhai.mapper;

import com.vhkhai.aggrerates.job_application.Interview;
import com.vhkhai.dto.job_application.InterviewResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InterviewMapper {
    InterviewResponseDto toDto(Interview interview);
}
