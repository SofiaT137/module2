package com.epam.esm.dto.impl;

public class AbstractDto<T>{

    protected T id;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }
}

