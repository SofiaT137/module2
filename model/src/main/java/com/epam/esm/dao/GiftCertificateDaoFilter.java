package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.util.MultiValueMap;

import java.util.List;

public interface GiftCertificateDaoFilter {

    List<GiftCertificate> findGiftCertificatesByTransferredConditions(MultiValueMap<String,
            String> mapWithFilters);
}
