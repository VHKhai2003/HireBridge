package com.vhkhai.aggrerates.company;

import lombok.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Company {
    private UUID id;

    private String name;

    private String email;

    private String phone;

    private Location location;

    private UUID accountId;

    private List<JobPosting> jobPostings;

    public void addJobPosting(String title, String requirement) {
        JobPosting jobPosting = JobPosting.builder()
                .id(UUID.randomUUID())
                .title(title)
                .requirement(requirement)
                .companyId(this.id)
                .build();
        this.jobPostings.add(jobPosting);
    }
}
