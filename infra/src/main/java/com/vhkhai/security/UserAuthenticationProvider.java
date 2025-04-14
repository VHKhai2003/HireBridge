package com.vhkhai.security;

import com.vhkhai.aggrerates.account.Account;
import com.vhkhai.port.UserAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class UserAuthenticationProvider implements UserAuthentication {

    @Override
    public Account getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Unauthorized");
        }

//        String username = authentication.getName();
//        List<String> roles = authentication.getAuthorities()
//                .stream()
//                .map(GrantedAuthority::getAuthority)
//                .toList();
//
//        log.info("Authenticated user: {} with roles: {}", username, roles);
//        // username is Account object, research later :>

        return (Account) authentication.getPrincipal();
    }
}
