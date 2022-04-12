package com.epam.esm.service;

import com.epam.esm.exceptions.DaoException;
import com.epam.esm.exceptions.ServiceException;

import java.util.List;

public interface CRDService<T> {
    void insert(T entity) throws ServiceException, DaoException;
    T getById(long id) throws DaoException, ServiceException;
    List<T> getAll() throws DaoException;
    void deleteByID(long id) throws DaoException, ServiceException;
}
