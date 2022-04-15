package com.epam.esm.service.impl;

import com.epam.esm.converter.impl.GiftCertificateConverter;
import com.epam.esm.dto.impl.GiftCertificateDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;
import com.epam.esm.exceptions.ServiceException;
import com.epam.esm.jbdc.GiftCertificateDao;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validator.GiftCertificateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service //// @ наследник компонент рекомендуется использовать в тех случаях, когда вы можете отнести аннотируемый класс к определенному слою
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateDao giftCertificateDao;
    private final GiftCertificateConverter giftCertificateConverter;
    private final GiftCertificateValidator certificateValidator;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao, GiftCertificateConverter giftCertificateConverter, GiftCertificateValidator certificateValidator) {
        this.giftCertificateDao = giftCertificateDao;
        this.giftCertificateConverter = giftCertificateConverter;
        this.certificateValidator = certificateValidator;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void insert(GiftCertificateDto entity) throws DaoException, ServiceException {
        certificateValidator.validate(entity,null);
        giftCertificateDao.insert(giftCertificateConverter.convert(entity));
    }

    @Override
    public GiftCertificateDto getById(long id) throws DaoException, ServiceException {
        certificateValidator.validateId(id);
        return giftCertificateConverter.convert(giftCertificateDao.getById(id));
    }

    @Override
    public List<GiftCertificateDto> getAll() throws DaoException {
        return giftCertificateDao.getAll().stream().map(giftCertificateConverter::convert).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void deleteByID(long id) throws DaoException, ServiceException {
        certificateValidator.validateId(id);
        giftCertificateDao.deleteByID(id);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void update(Long id,GiftCertificateDto entity) throws DaoException, ServiceException {
        certificateValidator.validateId(id);
        entity.setId(id);
        entity.setLastUpdateDate(getCurrentDate());
        List<String> tagNames = entity.getTags();
        List<Tag> tags = null;
        if (tagNames != null) {
            tags = getNewTagList(tagNames);
            giftCertificateDao.deleteListOfCertificateTags(id);
        }
        certificateValidator.validate(entity,tags);
        giftCertificateDao.update(giftCertificateConverter.convert(entity));
        giftCertificateDao.addTagsToCertificate(id,tags);
    }

    private String getCurrentDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        return LocalDateTime.now().format(formatter);
    }

    private List<Tag> getNewTagList(List<String> listOfTagNames){
        return listOfTagNames.stream().map(Tag::new).collect(Collectors.toList());
    }

    @Override
    public List<GiftCertificateDto> getQueryWithConditions(Map<String, String> mapWithFilters) throws DaoException {
        return giftCertificateDao.getQueryWithConditions(mapWithFilters).stream().map(giftCertificateConverter::convert).collect(Collectors.toList());
    }
}
