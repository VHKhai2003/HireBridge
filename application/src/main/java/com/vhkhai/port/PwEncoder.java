package com.vhkhai.port;

public interface PwEncoder {
    String encode(String rawPassword);
    boolean matches(String rawPassword, String encodedPassword);
}
