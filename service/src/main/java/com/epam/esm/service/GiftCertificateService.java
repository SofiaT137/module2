package com.epam.esm.service;

import com.epam.esm.dto.impl.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exceptions.DaoException;
import com.epam.esm.exceptions.ServiceException;

import java.util.List;
import java.util.Map;

public interface GiftCertificateService extends CRUDService<GiftCertificateDto> {
    List<GiftCertificateDto> getQueryWithConditions(Map<String,String> mapWithFilters) throws DaoException, ServiceException;
}
