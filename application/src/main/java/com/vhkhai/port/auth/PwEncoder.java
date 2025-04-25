package com.vhkhai.port.auth;

public interface PwEncoder {
    String encode(String rawPassword);
    boolean matches(String rawPassword, String encodedPassword);
}
