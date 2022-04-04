package com.epam.esm;

public interface CRUDDao<T> extends CRDDao<T> {
    void update(T entity);
}
