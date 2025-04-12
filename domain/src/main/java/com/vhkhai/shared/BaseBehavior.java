package com.vhkhai.shared;

import java.util.Optional;

public interface BaseBehavior <T, ID> {
    T create(T entity);

    T update(T entity);

    void delete(ID id);

    Optional<T> getById(ID id);
}
