package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;

import java.util.Optional;

/**
 * CRUDService interface features CRUD Service functionality and extends CRDService
 * @param <T> The entity object
 */
public interface CRUDService<T> extends CRDService<T> {

   T update(Long id, T entity);
}
