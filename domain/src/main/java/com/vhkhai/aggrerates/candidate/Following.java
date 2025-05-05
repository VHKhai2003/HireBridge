package com.vhkhai.aggrerates.candidate;

import com.vhkhai.aggrerates.company.Company;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "followings")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Following {
    @EmbeddedId
//    @AttributeOverrides({
//            @AttributeOverride(name = "candidateId", column = @Column(name = "candidate_id")),
//            @AttributeOverride(name = "companyId", column = @Column(name = "company_id"))
//    })
    private FollowingId id;

    @ManyToOne
    @MapsId("candidateId")
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    @ManyToOne
    @MapsId("companyId")
    @JoinColumn(name = "company_id")
    private Company company;

    public Following(Candidate candidate, Company company) {
        this.id = new FollowingId(candidate.getId(), company.getId());
        this.candidate = candidate;
        this.company = company;
    }

    public Following(UUID candidateId, UUID companyId) {
        this.id = new FollowingId(candidateId, companyId);
    }
}
