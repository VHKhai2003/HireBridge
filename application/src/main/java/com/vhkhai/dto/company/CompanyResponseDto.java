package com.vhkhai.dto.company;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyResponseDto {
    private UUID id;
    private String name;
    private String email;
    private String phone;
    private String address;
}
