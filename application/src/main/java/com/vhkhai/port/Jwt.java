package com.vhkhai.port;

import com.vhkhai.aggrerates.account.Account;

import java.util.UUID;

public interface Jwt {
    String generateAccessToken(UUID id);

    String generateRefreshToken(UUID id);

    UUID getAccountIdFromToken(String token);

    boolean validateToken(String token);
}
