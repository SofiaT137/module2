package com.epam.esm.service.logic_service;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.CannotInsertEntityException;
import com.epam.esm.exceptions.NoSuchEntityException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validator.GiftCertificateValidator;
import com.epam.esm.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.epam.esm.exceptions.ExceptionErrorCode.CANNOT_INSERT_ENTITY_CODE;
import static com.epam.esm.exceptions.ExceptionErrorCode.NO_SUCH_ENTITY_CODE;

@Service("giftCertificateLogicService")
public class GiftCertificateLogicService implements GiftCertificateService<GiftCertificate> {

    private final GiftCertificateDao giftCertificateDao;
    private final GiftCertificateValidator certificateValidator;
    private final TagValidator tagValidator;

    @Autowired
    public GiftCertificateLogicService(GiftCertificateDao giftCertificateDao, GiftCertificateValidator certificateValidator,TagValidator tagValidator) {
        this.giftCertificateDao = giftCertificateDao;
        this.certificateValidator = certificateValidator;
        this.tagValidator = tagValidator;
    }

    @Override
    @Transactional
    public void insert(GiftCertificate entity) {
        certificateValidator.validate(entity);
        List<Tag> entityHasTags = entity.getTags();
        entityHasTags.forEach(tagValidator::validate);
        entity.setTags(entityHasTags);
        Optional<GiftCertificate> insertedTag = giftCertificateDao.insert(entity);
        if (!insertedTag.isPresent()){
            throw new CannotInsertEntityException("Cannot insert this tag!",CANNOT_INSERT_ENTITY_CODE);
        }
    }

    @Override
    public GiftCertificate getById(long id) {
        certificateValidator.checkID(id);
        Optional<GiftCertificate> receivedGiftCertificateById = giftCertificateDao.getById(id);
        if (!receivedGiftCertificateById.isPresent()){
            throw new NoSuchEntityException("No tag with that id!",NO_SUCH_ENTITY_CODE);
        }
        return receivedGiftCertificateById.get();
    }

    @Override
    public List<GiftCertificate> getAll(int pageSize,int pageNumber) {
        return giftCertificateDao.getAll(pageSize, pageNumber);
    }

    @Override
    @Transactional
    public void update(Long id, List<Tag> tags, GiftCertificate entity) {
        certificateValidator.validate(entity);
        if (tags != null) {
            tags.forEach(tagValidator::validate);
            entity.setTags(new ArrayList<>());
            entity.setTags(tags);
        }
        giftCertificateDao.update(entity);
    }

    @Override
    @Transactional
    public void deleteByID(long id) {
        Optional<GiftCertificate> receivedGiftCertificateById = giftCertificateDao.getById(id);
        if (!receivedGiftCertificateById.isPresent()){
            throw new NoSuchEntityException("No tag with that id!",NO_SUCH_ENTITY_CODE);
        }
        giftCertificateDao.deleteByID(id);
    }

    @Override
    public List<GiftCertificate> getQueryWithConditions(MultiValueMap<String, String> mapWithFilters) {
        certificateValidator.validateMapKeys(mapWithFilters);
        return giftCertificateDao.findGiftCertificatesByTransferredConditions(mapWithFilters);
    }
}
