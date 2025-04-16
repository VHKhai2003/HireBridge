package com.vhkhai.dto.account;

import com.vhkhai.enumerations.AccountType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccountRequestDto {
    private String email;
    private String password;
}
