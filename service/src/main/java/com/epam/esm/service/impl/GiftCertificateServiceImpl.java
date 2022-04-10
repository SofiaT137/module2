package com.epam.esm.service.impl;

import com.epam.esm.jbdc.GiftCertificateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service //// @ наследник компонент рекомендуется использовать в тех случаях, когда вы можете отнести аннотируемый класс к определенному слою
public class GiftCertificateServiceImpl implements com.epam.esm.service.GiftCertificateService {

    private GiftCertificateDao giftCertificateDao;

    @Autowired
    public void setGiftCertificateDao(GiftCertificateDao giftCertificateDao) {
        this.giftCertificateDao = giftCertificateDao;
    }

    @Override
    public void insert(com.epam.esm.entity.GiftCertificate entity) {

    }

    @Override
    public com.epam.esm.entity.GiftCertificate getById(long id) {
        return null;
    }

    @Override
    public List<com.epam.esm.entity.GiftCertificate> getAll() {
        return null;
    }

    @Override
    public void deleteByID(long id) {

    }

    @Override
    public void update(com.epam.esm.entity.GiftCertificate entity) {

    }

    @Override
    public List<com.epam.esm.entity.GiftCertificate> getQueryWithConditions(Map<String, String> mapWithFilters) {
        return null;
    }
}
