package com.epam.esm.dto;

/**
 * Class AbstractDto helps to create AbstractDto entity
 * @param <T>
 */
public class AbstractDto<T>{

    protected T id;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }
}

