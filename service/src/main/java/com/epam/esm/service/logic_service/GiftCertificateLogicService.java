package com.epam.esm.service.logic_service;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.CannotInsertEntityException;
import com.epam.esm.exceptions.NoPermissionException;
import com.epam.esm.exceptions.NoSuchEntityException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class GiftCertificateLogicService is implementation of interface GiftCertificateService
 * The class presents service logic layer for GiftCertificate entity
 */
@Service("giftCertificateLogicService")
public class GiftCertificateLogicService implements GiftCertificateService<GiftCertificate> {

    private final GiftCertificateDao giftCertificateDao;
    private TagService<Tag> tagLogicService;

    private static final String CANNOT_INSERT_THIS_GIFT_CERTIFICATE_MESSAGE = "giftCertificateNameIsNotUnique";
    private static final String CANNOT_FIND_THIS_GIFT_CERTIFICATE_MESSAGE = "noGiftCertificateWithThatId";
    private static final String CANNOT_FIND_ANY_CERTIFICATE_BY_CONDITIONS_MESSAGE = "noGiftCertificateWithConditions";
    private static final String CANNOT_UPDATE_THIS_GIFT_CERTIFICATE_MESSAGE = "cannotUpdateThisCertificate";
    private static final String TOO_MUCH_TRANSFERRED_PARAMETERS_MESSAGE = "forbiddenTransferredToMuchParameters";

    @Autowired
    public GiftCertificateLogicService(GiftCertificateDao giftCertificateDao) {
        this.giftCertificateDao = giftCertificateDao;
    }

    @Autowired
    @Qualifier("tagLogicService")
    public void setTagLogicService(TagService<Tag> tagLogicService) {
        this.tagLogicService = tagLogicService;
    }

    @Override
    @Transactional
    public GiftCertificate insert(GiftCertificate entity) {
        checkIfGiftCertificateNameIsUnique(entity.getGiftCertificateName());
        if (!entity.getTagList().isEmpty()){
            addTagsToGiftCertificateList(entity.getTagList(),entity);
        }
        entity.setCreateDate(LocalDateTime.now());
        entity.setLastUpdateDate(LocalDateTime.now());
        return giftCertificateDao.save(entity);
    }

    private void addTagsToGiftCertificateList(List<Tag> tagList,GiftCertificate entity){
        List<Tag> certificateTags = tagLogicService.getCertificateTagList(entity.getTagList());
        entity.setTagList(new ArrayList<>());
        certificateTags.forEach(entity::addTagToGiftCertificate);
    }

    private void checkIfGiftCertificateNameIsUnique(String giftCertificateName) {
        Optional<GiftCertificate> giftCertificate = giftCertificateDao.findByGiftCertificateName(giftCertificateName);
        if (giftCertificate.isPresent()){
            throw new CannotInsertEntityException(CANNOT_INSERT_THIS_GIFT_CERTIFICATE_MESSAGE);
        }
    }

    @Override
    public GiftCertificate getById(long id) {
        Optional<GiftCertificate> receivedGiftCertificateById = giftCertificateDao.findById(id);
        if (!receivedGiftCertificateById.isPresent()){
            throw new NoSuchEntityException(CANNOT_FIND_THIS_GIFT_CERTIFICATE_MESSAGE);
        }
        return receivedGiftCertificateById.get();
    }

    @Override
    public Page<GiftCertificate> getAll(int pageSize, int pageNumber) {
        return giftCertificateDao.findAll(PageRequest.of(pageSize,pageNumber));
    }


    @Override
    @Transactional
    public int update(Long id, GiftCertificate entity) {
        Optional<GiftCertificate> foundedCertificateById = giftCertificateDao.findById(id);
        if (!foundedCertificateById.isPresent()){
            throw new NoSuchEntityException(CANNOT_FIND_THIS_GIFT_CERTIFICATE_MESSAGE);
        }
        checkIfEntityHasMoreThatOneTransferredParameter(entity);
        int updatedRows = giftCertificateDao.update(entity.getDuration(),
                foundedCertificateById.get().getId());
        if (updatedRows == 0){
            throw new NoSuchEntityException(CANNOT_UPDATE_THIS_GIFT_CERTIFICATE_MESSAGE);
        }
        return updatedRows;
    }

    private void checkIfEntityHasMoreThatOneTransferredParameter(GiftCertificate entity){
        if (entity.getPrice() != null || entity.getDescription()!= null
            || entity.getGiftCertificateName()!= null || entity.getCreateDate()!=null
            || entity.getLastUpdateDate()!=null || !entity.getTagList().isEmpty()){
           throw new NoPermissionException(TOO_MUCH_TRANSFERRED_PARAMETERS_MESSAGE);
        }
    }

    @Override
    @Transactional
    public void deleteByID(long id) {
        Optional<GiftCertificate> receivedGiftCertificateById = giftCertificateDao.findById(id);
        if (!receivedGiftCertificateById.isPresent()){
            throw new NoSuchEntityException(CANNOT_FIND_THIS_GIFT_CERTIFICATE_MESSAGE);
        }
        giftCertificateDao.delete(receivedGiftCertificateById.get());
    }

    @Override
    public List<GiftCertificate> getQueryWithConditions(MultiValueMap<String, String> mapWithFilters) {
        List< GiftCertificate> giftCertificateList = giftCertificateDao
                .findGiftCertificatesByTransferredConditions(mapWithFilters);
        if (giftCertificateList.isEmpty()){
            throw new NoSuchEntityException(CANNOT_FIND_ANY_CERTIFICATE_BY_CONDITIONS_MESSAGE);
        }
        return giftCertificateList;
    }
}
