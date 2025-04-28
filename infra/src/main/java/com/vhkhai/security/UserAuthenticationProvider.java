package com.vhkhai.security;

import com.vhkhai.aggrerates.account.Account;
import com.vhkhai.exception.ErrorCode;
import com.vhkhai.exception.InfrastructureException;
import com.vhkhai.port.auth.UserAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserAuthenticationProvider implements UserAuthentication {

    @Override
    public Account getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new InfrastructureException(ErrorCode.UNAUTHORIZED);
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
