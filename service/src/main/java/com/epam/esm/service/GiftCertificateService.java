package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;
import java.util.Map;

public interface GiftCertificateService extends CRUDService<GiftCertificate> {
    List<GiftCertificate> getQueryWithConditions(Map<String,String> mapWithFilters);
}
