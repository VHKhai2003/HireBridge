package com.vhkhai.aggrerates.candidate;

import com.vhkhai.aggrerates.company.Company;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "candidate_followings")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
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
}
