package com.vhkhai.aggrerates.recruitment;

import lombok.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Recruitment {
    private UUID id;
    private UUID jobPostingId;
    private List<UUID> applicationIds;
}
