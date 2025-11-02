package com.vhkhai.audit;

import com.vhkhai.aggrerates.account.Account;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class SecurityAuditorAware implements AuditorAware<UUID> {
    @Override
    public Optional<UUID> getCurrentAuditor() {
        var account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Optional.ofNullable(account.getId());
    }
}
