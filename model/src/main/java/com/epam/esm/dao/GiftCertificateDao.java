package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Optional;

/**
 * GiftCertificateDao interface implements CRDDao functionality and adds a special GiftCertificate DAO functionality
 */
public interface GiftCertificateDao extends CRDDao<GiftCertificate> {

    /**
     * The method searches the GiftCertificate entity by passing values in a 'gift_certificate' table
     * @param mapWithFilters Map(key=filter, value=passed value)
     * @return list of the GiftCertificate entities
     */
    List<GiftCertificate> findGiftCertificatesByTransferredConditions(MultiValueMap<String,String> mapWithFilters);

    /**
     * The method updates the GiftCertificate object in a 'gift_certificate' table
     * @param duration The GiftCertificates new duration
     * @param entity The GiftCertificate object from table
     * @return The updated GiftCertificate entity object from a 'gift_certificate' table
     */
    Optional<GiftCertificate> update(int duration, GiftCertificate entity);

}
