package com.epam.esm.service;

import com.epam.esm.exceptions.DaoException;
import com.epam.esm.exceptions.ServiceException;

public interface CRUDService<T> extends CRDService<T>{
    void update(Long id,T entity) throws DaoException, ServiceException;
}
