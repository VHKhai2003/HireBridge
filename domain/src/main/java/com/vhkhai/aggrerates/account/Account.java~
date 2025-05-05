package com.vhkhai.aggrerates.account;

import com.vhkhai.enumerations.AccountType;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = {"id"})
//TODO: don't dung builder pattern for entity, it lead to break ddd
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private AccountType type;

    public Account(String email, String password, AccountType type) {
        this.id = UUID.randomUUID();
        this.email = email;
        this.password = password;
        this.type = type;
    }

    public boolean isCompany() {
        return this.type == AccountType.COMPANY;
    }

    public boolean isCandidate() {
        return this.type == AccountType.CANDIDATE;
    }
}
