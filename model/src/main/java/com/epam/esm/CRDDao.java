package com.epam.esm;

import com.epam.esm.exceptions.DaoException;

import java.util.List;

public interface CRDDao<T>{
    void insert(T entity) throws DaoException;
    T getById(long id) throws DaoException;
    List<T> getAll() throws DaoException;
    void deleteByID(long id) throws DaoException;
}
