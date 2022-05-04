package com.epam.esm.service.logic_service;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.CannotInsertEntityException;
import com.epam.esm.exceptions.NoPermissionException;
import com.epam.esm.exceptions.NoSuchEntityException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validator.GiftCertificateValidator;
import com.epam.esm.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Optional;

import static com.epam.esm.exceptions.ExceptionErrorCode.CANNOT_INSERT_ENTITY_CODE;
import static com.epam.esm.exceptions.ExceptionErrorCode.NO_SUCH_ENTITY_CODE;
import static com.epam.esm.exceptions.ExceptionErrorCode.YOU_NOT_HAVE_PERMISSION_ENTITY_CODE;

@Service("giftCertificateLogicService")
public class GiftCertificateLogicService implements GiftCertificateService<GiftCertificate> {

    private final GiftCertificateDao giftCertificateDao;
    private final TagDao tagDao;
    private final GiftCertificateValidator certificateValidator;
    private final TagValidator tagValidator;

    @Autowired
    public GiftCertificateLogicService(GiftCertificateDao giftCertificateDao, TagDao tagDao, GiftCertificateValidator certificateValidator, TagValidator tagValidator) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagDao = tagDao;
        this.certificateValidator = certificateValidator;
        this.tagValidator = tagValidator;
    }

    @Override
    @Transactional
    public void insert(GiftCertificate entity) {
        certificateValidator.validate(entity);
        entity.setTags(null);
        addTagsToCertificate(entity);
        Optional<GiftCertificate> insertedTag = giftCertificateDao.insert(entity);
        if (!insertedTag.isPresent()){
            throw new CannotInsertEntityException("Cannot insert this tag!",CANNOT_INSERT_ENTITY_CODE);
        }
    }

    private void addTagsToCertificate(GiftCertificate entity){
        List<Tag> entityHasTags = entity.getTags();
        entityHasTags.forEach(tagValidator::validate);
        for (Tag entityHasTag : entityHasTags) {
            Optional<Tag> currentTag = tagDao.findTagByTagName(entityHasTag.getName());
            if (!currentTag.isPresent()){
                currentTag = tagDao.insert(entityHasTag);
                if (!currentTag.isPresent()){
                    throw new CannotInsertEntityException("Cannot insert this tag",CANNOT_INSERT_ENTITY_CODE);
                }
            }
            entity.addTagToGiftCertificate(currentTag.get());
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
    public void update(Long id, GiftCertificate entity) {
        Optional<GiftCertificate> foundedCertificateById = giftCertificateDao.getById(id);
        if (!foundedCertificateById.isPresent()){
            throw new NoSuchEntityException("No gift certificate with that id!",NO_SUCH_ENTITY_CODE);
        }
        checkIfEntityHasMoreThatOneTransferredParameter(entity);
        certificateValidator.validate(entity);
        giftCertificateDao.update(entity.getDuration(),foundedCertificateById.get());
    }

    private void checkIfEntityHasMoreThatOneTransferredParameter(GiftCertificate entity){
        if (entity.getPrice() != null || entity.getDescription()!= null
            || entity.getGiftCertificateName()!= null || entity.getCreateDate()!=null
            || entity.getLastUpdateDate()!=null || entity.getTags()!=null){
            throw new NoPermissionException("There is too much transferred parameters! You can update only 'duration' field!"
                    ,YOU_NOT_HAVE_PERMISSION_ENTITY_CODE);
        }
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
