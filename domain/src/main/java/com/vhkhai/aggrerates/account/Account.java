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
@Builder
@ToString
@EqualsAndHashCode(of = {"id"})
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private AccountType type;

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
