package com.vhkhai.aggrerates.company;

import com.vhkhai.aggrerates.account.Account;
import com.vhkhai.enumerations.JobPostingStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "companies")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = {"id"})
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private String email;

    private String phone;

    private String address;


    @OneToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @OneToMany(mappedBy = "company")
    private List<JobPosting> jobPostings;


    public void addJobPosting(String title, String requirement) {
        JobPosting jobPosting = JobPosting.builder()
                .title(title)
                .requirement(requirement)
                .company(this)
                .status(JobPostingStatus.OPENED)
                .build();
        this.jobPostings.add(jobPosting);
    }
}
