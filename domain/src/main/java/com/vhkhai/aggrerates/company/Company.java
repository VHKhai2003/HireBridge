package com.vhkhai.aggrerates.company;

import com.vhkhai.aggrerates.account.Account;
import com.vhkhai.enumerations.JobPostingStatus;
import com.vhkhai.events.JobPostingCreationEvent;
import com.vhkhai.exception.DomainErrorCode;
import com.vhkhai.exception.DomainException;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.AbstractAggregateRoot;

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
public class Company extends AbstractAggregateRoot<Company> {
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

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<JobPosting> jobPostings;


    public void addJobPosting(String title, String requirement) {
        JobPosting jobPosting = new JobPosting(UUID.randomUUID(), title, requirement, JobPostingStatus.OPENED, this);
        this.jobPostings.add(jobPosting);
        registerEvent(new JobPostingCreationEvent(jobPosting));
    }

    public JobPosting getJobPosting(UUID id) {
        return jobPostings.stream().filter(jobPosting -> jobPosting.getId().equals(id)).findFirst().orElse(null);
    }

    public void updateProfile(String name, String phone, String address) {
        if(checkName(name) == false) {
            throw new DomainException(DomainErrorCode.INVALID_COMPANY_NAME);
        }
        if(checkPhone(phone) == false) {
            throw new DomainException(DomainErrorCode.INVALID_COMPANY_PHONE);
        }
        if(checkAddress(address) == false) {
            throw new DomainException(DomainErrorCode.INVALID_COMPANY_ADDRESS);
        }
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    private boolean checkName(String name) {
        if (name == null || name.isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean checkPhone(String phone) {
        String regex = "^\\+?[0-9]{9,15}$";
        if (phone == null || phone.isEmpty() || !phone.matches(regex)) {
            return false;
        }
        return true;
    }

    private boolean checkAddress(String address) {
        if (address == null || address.isEmpty()) {
            return false;
        }
        return true;
    }
}
