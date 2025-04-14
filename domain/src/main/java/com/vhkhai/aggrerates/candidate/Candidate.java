package com.vhkhai.aggrerates.candidate;

import com.vhkhai.aggrerates.account.Account;
import com.vhkhai.aggrerates.company.Company;
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
    private String cvLink;

    @OneToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @OneToMany(mappedBy = "candidate")
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
        Following following = Following.builder()
                .candidate(this)
                .company(company)
                .build();
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
    }

}
