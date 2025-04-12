package com.vhkhai.aggrerates.candidate;

import lombok.*;

import java.util.Collections;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Candidate {
    private UUID id;
    private String fullName;
    private String email;
    private String phone;
    private String cvLink;
    private UUID accountId;
    private CandidateFollowing candidateFollowing;

    public void followCompany(UUID companyId) {
        if (candidateFollowing == null) {
            candidateFollowing = new CandidateFollowing(Collections.singletonList(companyId));
        } else if (!candidateFollowing.getCompanyIds().contains(companyId)) {
            candidateFollowing.getCompanyIds().add(companyId);
        }
    }

    public void unfollowCompany(UUID companyId) {
        if (candidateFollowing != null) {
            candidateFollowing.getCompanyIds().remove(companyId);
        }
    }

    public boolean isFollowing(UUID companyId) {
        return candidateFollowing != null &&
                candidateFollowing.getCompanyIds().contains(companyId);
    }

    public void updateCV(String newCVLink) {
        this.cvLink = newCVLink;
    }

    public boolean hasValidCV() {
        return cvLink != null && !cvLink.isEmpty();
    }
}
