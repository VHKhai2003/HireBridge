package com.vhkhai.port.auth;

import java.util.UUID;

public interface Jwt {
    String generateAccessToken(UUID id);

    String generateRefreshToken(UUID id);

    UUID getAccountIdFromToken(String token);

    boolean validateToken(String token);
}
