package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.data.domain.Page;
import org.springframework.util.MultiValueMap;

import java.security.cert.Certificate;
import java.util.Optional;

/**
 * GiftCertificateCustomRepo interface contains the GiftCertificateCustomRepo functionality
 * for the GiftCertificate entity
 */
public interface GiftCertificateCustomRepo {

    /**
     * The method findGiftCertificatesByTransferredConditions searches for GiftCertificate entities by passing values
     * @param mapWithFilters Map(key=filter, value=passed value)
     * @param pageNumber The number of the page
     * @param pageSize  The size of the page
     * @return Page of the GiftCertificate entities
     */
    Page<GiftCertificate> findGiftCertificatesByTransferredConditions(MultiValueMap<String,
            String> mapWithFilters, int pageNumber, int pageSize);

    /**
     * The method updates the GiftCertificate entity
     * @param entity The GiftCertificate object from table
     * @param certificateForUpdate The GiftCertificates for update from DB
     * @return Optional of the GiftCertificate entity
     */
    Optional<GiftCertificate> update(GiftCertificate entity, GiftCertificate certificateForUpdate);
}
