package com.epam.esm.service;

import com.epam.esm.dto.impl.GiftCertificateDto;
import com.epam.esm.exceptions.DaoException;
import com.epam.esm.exceptions.ValidatorException;

import java.util.List;
import java.util.Map;

/**
 * GiftCertificateService interface features CRUD Service functionality and extends CRUDService
 */
public interface GiftCertificateService extends CRUDService<GiftCertificateDto> {
    /**
     * The method provides service layer logic for searching the GiftCertificateDto entity by passing values
     * @param mapWithFilters Map(key=filter, value=passed value)
     * @return The GiftCertificateDto object
     */
    List<GiftCertificateDto> getQueryWithConditions(Map<String,String> mapWithFilters) throws DaoException, ValidatorException;
}
