package com.vhkhai.dto.candidate;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class FollowCompanyRequestDto {
    private UUID companyId;
}
