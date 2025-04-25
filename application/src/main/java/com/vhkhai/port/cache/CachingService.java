package com.vhkhai.port.cache;

public interface CachingService {
    String get(String key);
    <T> T get(String key, Class<T> clazz);
    void delete(String key);
    void set(String key, Object value);
    void set(String key, Object value, long expirationTime);
    boolean hasKey(String key);

}
