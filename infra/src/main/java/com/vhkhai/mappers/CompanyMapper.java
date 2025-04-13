package com.vhkhai.mappers;

import com.vhkhai.aggrerates.company.Company;
import com.vhkhai.aggrerates.company.JobPosting;
import com.vhkhai.aggrerates.company.Location;
import com.vhkhai.entities.account.AccountEntity;
import com.vhkhai.entities.company.CompanyEntity;
import com.vhkhai.entities.company.JobPostingEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompanyMapper {
    public Company toCompany(CompanyEntity entity) {
        if (entity == null) {
            return null;
        }
        return Company.builder()
                .id(entity.getId())
                .name(entity.getName())
                .location(new Location(entity.getLatitude(), entity.getLongitude()))
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .accountId(entity.getAccount().getId())
                .jobPostings(entity.getJobPostings()
                        .stream()
                        .map(jobPostingEntity -> new JobPosting(jobPostingEntity.getId(),
                                jobPostingEntity.getTitle(),
                                jobPostingEntity.getRequirement(),
                                jobPostingEntity.getStatus(),
                                entity.getId()))
                        .toList())
                .build();
    }

    public CompanyEntity toCompanyEntity(Company company) {
        if (company == null) {
            return null;
        }

        AccountEntity accountEntity = AccountEntity.builder()
                .id(company.getAccountId())
                .build();

        List<JobPostingEntity> jobPostingEntities = company.getJobPostings().stream()
                .map(jobPosting -> JobPostingEntity.builder()
                        .id(jobPosting.getId())
                        .build())
                .toList();

        return CompanyEntity.builder()
                .id(company.getId())
                .name(company.getName())
                .email(company.getEmail())
                .phone(company.getPhone())
                .latitude(company.getLocation() != null ? company.getLocation().latitude() : null)
                .longitude(company.getLocation() != null ? company.getLocation().longitude() : null)
                .account(accountEntity)
                .jobPostings(jobPostingEntities)
                .build();
    }
}
