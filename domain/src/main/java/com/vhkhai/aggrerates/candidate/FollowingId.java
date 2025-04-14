package com.vhkhai.aggrerates.candidate;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.UUID;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FollowingId {
    private UUID candidateId;
    private UUID companyId;
}
