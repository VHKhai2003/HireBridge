package com.vhkhai.aggrerates.candidate;

import com.vhkhai.aggrerates.account.Account;
import com.vhkhai.aggrerates.company.Company;
import com.vhkhai.exception.DomainErrorCode;
import com.vhkhai.exception.DomainException;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "candidates")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = {"id"})
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "full_name")
    private String fullName;

    private String email;

    private String phone;

    @Column(name = "cv_link")
    private String cv;

    @OneToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL)
    private List<Following> followings;

    @OneToMany(mappedBy = "candidate")
    private List<Notification> notifications;


    public void receiveNotification(String title, String content) {
        Notification notification = Notification.builder()
                .title(title)
                .content(content)
                .candidate(this)
                .build();
        this.notifications.add(notification);
    }

    public void followCompany(Company company) {
        // Check if the candidate is already following the company
        if (this.followings.stream().anyMatch(f -> f.getCompany().equals(company))) {
            throw new DomainException(DomainErrorCode.COMPANY_ALREADY_FOLLOWED);
        }
        Following following = new Following(new FollowingId(this.id, company.getId()), this, company);
        this.followings.add(following);
    }

    public void unfollowCompany(Company company) {
        Following following = this.followings.stream()
                .filter(f -> f.getCompany().equals(company))
                .findFirst()
                .orElse(null);
        if (following != null) {
            this.followings.remove(following);
        }
        else {
            throw new DomainException(DomainErrorCode.COMPANY_NOT_FOLLOWED);
        }
    }

    public void updateProfile(String fullName, String phone) {

        if (checkFullName(fullName) == false) {
            throw new DomainException(DomainErrorCode.INVALID_FULLNAME);
        }
        if (checkPhone(phone) == false) {
            throw new DomainException(DomainErrorCode.INVALID_PHONE);
        }

        this.fullName = fullName;
        this.phone = phone;
    }

    public void updateCV(String cv) {
        this.cv = cv;
    }


    private boolean checkFullName(String fullName) {
        if (fullName == null || fullName.isEmpty()) {
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

}
