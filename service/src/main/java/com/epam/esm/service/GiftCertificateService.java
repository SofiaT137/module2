package com.epam.esm.service;

import com.epam.esm.dto.impl.GiftCertificateDto;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;

/**
 * GiftCertificateService interface features CRUD Service functionality and extends CRUDService
 */
public interface GiftCertificateService<T> extends CRUDService<T> {
    /**
     * The method provides service layer logic for searching the GiftCertificateDto entity by passing values
     * @param mapWithFilters Map(key=filter, value=passed value)
     * @return The GiftCertificateDto object
     */
    List<T> getQueryWithConditions(MultiValueMap<String,String> mapWithFilters);
}
