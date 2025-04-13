package com.vhkhai.provider;

import com.vhkhai.aggrerates.account.Account;

public interface JwtProvider {
    String generateToken(Account account, String type);

    String getEmailFromToken(String token);

    boolean validateToken(String token);

    String refreshToken(String token);
}
