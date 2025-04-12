package com.vhkhai.entities.candidate;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.UUID;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandidateFollowingId {
    private UUID candidateId;
    private UUID companyId;
}
