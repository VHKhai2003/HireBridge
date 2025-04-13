package com.vhkhai.aggrerates.account;

import com.vhkhai.enumerations.AccountType;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {
    private UUID id;
    private String email;
    private String password;
    private AccountType type;

    public Account(String email, String password, AccountType type) {
        this.id = UUID.randomUUID();
        this.email = email;
        this.password = password;
        this.type = type;
    }

    public boolean login(String email, String hashedPassword) {
        return this.email.equals(email) && this.password.equals(hashedPassword);
    }

    public boolean isCompany() {
        return this.type == AccountType.COMPANY;
    }

    public boolean isCandidate() {
        return this.type == AccountType.CANDIDATE;
    }
}
