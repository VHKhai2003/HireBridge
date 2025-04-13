package com.vhkhai.provider;

public interface PasswordEncoderProvider {
    String encode(String rawPassword);
    boolean matches(String rawPassword, String encodedPassword);
}
