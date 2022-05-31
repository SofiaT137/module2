package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.data.domain.Page;
import org.springframework.util.MultiValueMap;

import java.util.Optional;

public interface GiftCertificateCustomRepo {

    Page<GiftCertificate> findGiftCertificatesByTransferredConditions(MultiValueMap<String,
            String> mapWithFilters, int pageNumber, int pageSize);

    Optional<GiftCertificate> update(int duration, GiftCertificate entity);
}
