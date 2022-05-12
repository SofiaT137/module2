package com.epam.esm.service;

import com.epam.esm.entity.AbstractEntity;
import com.epam.esm.entity.User;

public interface UserService<T> extends RService<T>{
    T insert(T entity);
}
