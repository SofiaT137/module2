package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;

/**
 * GiftCertificateDao interface implements CRUDDao functionality and adds a special GiftCertificate DAO functionality
 */
public interface GiftCertificateDao extends CRUDDao<GiftCertificate> {

    /**
     * The method searches the GiftCertificate entity by passing values in a 'gift_certificate' table
     * @param mapWithFilters Map(key=filter, value=passed value)
     * @return list of the GiftCertificate entities
     */
    List<GiftCertificate> findGiftCertificatesByTransferredConditions(MultiValueMap<String,String> mapWithFilters);

}
