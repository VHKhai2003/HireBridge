package com.vhkhai.mapper;

import com.vhkhai.aggrerates.job_application.JobApplication;
import com.vhkhai.dto.job_application.JobApplicationResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {InterviewMapper.class, CandidateMapper.class, JobPostingMapper.class})
public interface JobApplicationMapper {

    JobApplicationResponseDto toDto(JobApplication jobApplication);
//    List<JobApplicationResponseDto> toDtoList(List<JobApplication> jobApplications);
}
