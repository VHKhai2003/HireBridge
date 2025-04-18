package com.vhkhai.mapper;

import com.vhkhai.aggrerates.company.JobPosting;
import com.vhkhai.dto.job_posting.JobPostingResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JobPostingMapper {
    JobPostingResponseDto toDto(JobPosting jobPosting);
}
