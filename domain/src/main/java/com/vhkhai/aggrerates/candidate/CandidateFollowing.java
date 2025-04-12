package com.vhkhai.aggrerates.candidate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
@ToString
public class CandidateFollowing {
    private final List<UUID> companyIds;
}
