package com.epam.esm.service;

import org.springframework.data.domain.Page;
import org.springframework.util.MultiValueMap;

/**
 * GiftCertificateService interface features GiftCertificate service functionality
 * and extends CRUDService functionality
 * @param <T> The entity object
 */
public interface GiftCertificateService<T> extends CRUDService<T> {
    /**
     * The method provides service layer logic for searching the GiftCertificateDto entity by passing values
     * @param mapWithFilters Map(key=filter, value=passed value)
     * @return The GiftCertificateDto object
     */
    Page<T> getQueryWithConditions(MultiValueMap<String,String> mapWithFilters);

}
