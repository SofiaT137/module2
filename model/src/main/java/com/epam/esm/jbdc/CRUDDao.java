package com.epam.esm.jbdc;

import com.epam.esm.exceptions.DaoException;

public interface CRUDDao<T> extends CRDDao<T> {
    void update(T entity) throws DaoException;
}
