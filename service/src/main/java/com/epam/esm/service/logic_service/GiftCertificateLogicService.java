package com.epam.esm.service.logic_service;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.CannotInsertEntityException;
import com.epam.esm.exceptions.NoPermissionException;
import com.epam.esm.exceptions.NoSuchEntityException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.GiftCertificateValidator;
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

import static com.epam.esm.exceptions.ExceptionErrorCode.CANNOT_INSERT_ENTITY_CODE;
import static com.epam.esm.exceptions.ExceptionErrorCode.NO_SUCH_ENTITY_CODE;
import static com.epam.esm.exceptions.ExceptionErrorCode.YOU_NOT_HAVE_PERMISSION_ENTITY_CODE;

/**
 * Class GiftCertificateLogicService is implementation of interface GiftCertificateService
 * The class presents service logic layer for GiftCertificate entity
 */
@Service("giftCertificateLogicService")
public class GiftCertificateLogicService implements GiftCertificateService<GiftCertificate> {

    private final GiftCertificateDao giftCertificateDao;
    private final GiftCertificateValidator certificateValidator;
    private TagService<Tag> tagLogicService;

    private static final String CANNOT_INSERT_THIS_GIFT_CERTIFICATE_MESSAGE = "cannotInsertThisCertificate";
    private static final String CANNOT_FIND_THIS_GIFT_CERTIFICATE_MESSAGE = "noGiftCertificateWithThatId";
    private static final String CANNOT_FIND_ANY_CERTIFICATE_BY_CONDITIONS_MESSAGE = "noGiftCertificateWithConditions";
    private static final String CANNOT_UPDATE_THIS_GIFT_CERTIFICATE_MESSAGE = "cannotUpdateThisCertificate";
    private static final String TOO_MUCH_TRANSFERRED_PARAMETERS_MESSAGE = "forbiddenTransferredToMuchParameters";

    @Autowired
    public GiftCertificateLogicService(GiftCertificateDao giftCertificateDao,
                                       GiftCertificateValidator certificateValidator) {
        this.giftCertificateDao = giftCertificateDao;
        this.certificateValidator = certificateValidator;
    }

    @Autowired
    @Qualifier("tagLogicService")
    public void setTagLogicService(TagService<Tag> tagLogicService) {
        this.tagLogicService = tagLogicService;
    }

    @Override
    @Transactional
    public GiftCertificate insert(GiftCertificate entity) {
        certificateValidator.validate(entity);
        if (!entity.getTagList().isEmpty()){
            List<Tag> certificateTags = tagLogicService.getCertificateTagList(entity.getTagList());
            entity.setTagList(new ArrayList<>());
            certificateTags.forEach(entity::addTagToGiftCertificate);
        }
        entity.setCreateDate(LocalDateTime.now());
        entity.setLastUpdateDate(LocalDateTime.now());
        GiftCertificate insertedCertificate = giftCertificateDao.save(entity);
        if (insertedCertificate == null){
            throw new CannotInsertEntityException(CANNOT_INSERT_THIS_GIFT_CERTIFICATE_MESSAGE,CANNOT_INSERT_ENTITY_CODE);
        }
       return insertedCertificate;
    }

    @Override
    public GiftCertificate getById(long id) {
        certificateValidator.checkID(id);
        Optional<GiftCertificate> receivedGiftCertificateById = giftCertificateDao.findById(id);
        if (!receivedGiftCertificateById.isPresent()){
            throw new NoSuchEntityException(CANNOT_FIND_THIS_GIFT_CERTIFICATE_MESSAGE,NO_SUCH_ENTITY_CODE);
        }
        return receivedGiftCertificateById.get();
    }

    @Override
    public Page<GiftCertificate> getAll(int pageSize, int pageNumber) {
        return giftCertificateDao.findAll(PageRequest.of(pageSize,pageNumber));
    }


    @Override
    @Transactional
    public GiftCertificate update(Long id, GiftCertificate entity) {
        certificateValidator.checkID(id);
        Optional<GiftCertificate> foundedCertificateById = giftCertificateDao.findById(id);
        if (!foundedCertificateById.isPresent()){
            throw new NoSuchEntityException(CANNOT_FIND_THIS_GIFT_CERTIFICATE_MESSAGE,NO_SUCH_ENTITY_CODE);
        }
        checkIfEntityHasMoreThatOneTransferredParameter(entity);
        certificateValidator.validateDuration(entity.getDuration());
        int updatedRows = giftCertificateDao.update(entity.getDuration(),
                foundedCertificateById.get().getId());
        if (updatedRows == 0){
            throw new NoSuchEntityException(CANNOT_UPDATE_THIS_GIFT_CERTIFICATE_MESSAGE,NO_SUCH_ENTITY_CODE);
        }
        return giftCertificateDao.findById(id).get();
    }

    private void checkIfEntityHasMoreThatOneTransferredParameter(GiftCertificate entity){
        if (entity.getPrice() != null || entity.getDescription()!= null
            || entity.getGiftCertificateName()!= null || entity.getCreateDate()!=null
            || entity.getLastUpdateDate()!=null || !entity.getTagList().isEmpty()){
           throw new NoPermissionException(TOO_MUCH_TRANSFERRED_PARAMETERS_MESSAGE
                    ,YOU_NOT_HAVE_PERMISSION_ENTITY_CODE);
        }
    }

    @Override
    @Transactional
    public void deleteByID(long id) {
        certificateValidator.checkID(id);
        Optional<GiftCertificate> receivedGiftCertificateById = giftCertificateDao.findById(id);
        if (!receivedGiftCertificateById.isPresent()){
            throw new NoSuchEntityException(CANNOT_FIND_THIS_GIFT_CERTIFICATE_MESSAGE,NO_SUCH_ENTITY_CODE);
        }
        giftCertificateDao.delete(receivedGiftCertificateById.get());
    }

    @Override
    public List<GiftCertificate> getQueryWithConditions(MultiValueMap<String, String> mapWithFilters) {
        certificateValidator.validateMapKeys(mapWithFilters);
        List< GiftCertificate> giftCertificateList = giftCertificateDao
                .findGiftCertificatesByTransferredConditions(mapWithFilters);
        if (giftCertificateList.isEmpty()){
            throw new NoSuchEntityException(CANNOT_FIND_ANY_CERTIFICATE_BY_CONDITIONS_MESSAGE,NO_SUCH_ENTITY_CODE);
        }
        return giftCertificateList;
    }
}
