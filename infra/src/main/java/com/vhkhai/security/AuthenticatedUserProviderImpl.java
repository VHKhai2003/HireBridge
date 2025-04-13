package com.vhkhai.security;

import com.vhkhai.aggrerates.account.Account;
import com.vhkhai.provider.AuthenticatedUserProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthenticatedUserProviderImpl implements AuthenticatedUserProvider {

    @Override
    public Account getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Unauthorized");
        }

        String username = authentication.getName();
        List<String> roles = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        
        return (Account) authentication.getPrincipal();
    }
}
