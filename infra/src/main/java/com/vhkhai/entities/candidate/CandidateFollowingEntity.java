package com.vhkhai.entities.candidate;

import com.vhkhai.entities.company.CompanyEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "candidate_followings")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CandidateFollowingEntity {
    @EmbeddedId
//    @AttributeOverrides({
//            @AttributeOverride(name = "candidateId", column = @Column(name = "candidate_id")),
//            @AttributeOverride(name = "companyId", column = @Column(name = "company_id"))
//    })
    private CandidateFollowingId id;

    @ManyToOne
    @MapsId("candidateId")
    @JoinColumn(name = "candidate_id")
    private CandidateEntity candidate;

    @ManyToOne
    @MapsId("companyId")
    @JoinColumn(name = "company_id")
    private CompanyEntity company;
}
