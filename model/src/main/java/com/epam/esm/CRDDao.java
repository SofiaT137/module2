package com.epam.esm;

import java.util.List;

public interface CRDDao<T>{
    void insert(T entity);
    T getById(long id);
    List<T> getAll();
    void deleteByID(long id);
}
