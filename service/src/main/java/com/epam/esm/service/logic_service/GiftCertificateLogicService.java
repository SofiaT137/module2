//package com.epam.esm.service.logic_service;
//
//import com.epam.esm.entity.GiftCertificate;
//import com.epam.esm.entity.Tag;
//import com.epam.esm.exceptions.ValidatorException;
//import com.epam.esm.service.GiftCertificateService;
//import com.epam.esm.validator.GiftCertificateValidator;
//import com.epam.esm.validator.TagValidator;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Map;
//
//@Service("giftCertificateLogicService")
//public class GiftCertificateLogicService implements GiftCertificateService<GiftCertificate> {
//
//    private final GiftCertificateDao giftCertificateDao;
//    private final GiftCertificateValidator certificateValidator;
//    private final TagValidator tagValidator;
//
//    @Autowired
//    public GiftCertificateLogicService(GiftCertificateDao giftCertificateDao, GiftCertificateValidator certificateValidator,TagValidator tagValidator) {
//        this.giftCertificateDao = giftCertificateDao;
//        this.certificateValidator = certificateValidator;
//        this.tagValidator = tagValidator;
//    }
//
//    @Override
//    @Transactional(rollbackFor = {DaoException.class, ValidatorException.class})
//    public void insert(GiftCertificate entity) {
//        certificateValidator.validate(entity);
//        List<Tag> entityHasTags = entity.getTags();
//        entityHasTags.forEach(tagValidator::validate);
//        giftCertificateDao.insert(entity);
//    }
//
//    @Override
//    public GiftCertificate getById(long id) {
//        certificateValidator.checkID(id);
//        return giftCertificateDao.getById(id);
//    }
//
//    @Override
//    public List<GiftCertificate> getAll() {
//        return giftCertificateDao.getAll();
//    }
//
//    @Override
//    @Transactional(rollbackFor = {DaoException.class, ValidatorException.class})
//    public void update(Long id, List<Tag> tags, GiftCertificate entity) {
//        certificateValidator.validate(entity);
//        giftCertificateDao.update(entity);
//        if (tags != null) {
//            tags.forEach(tagValidator::validate);
//            giftCertificateDao.deleteListOfCertificateTags(entity.getId());
//            giftCertificateDao.addTagsToCertificate(entity.getId(),tags);
//        }
//    }
//
//    @Override
//    public void deleteByID(long id) {
//        certificateValidator.checkID(id);
//        giftCertificateDao.deleteByID(id);
//    }
//
//    @Override
//    public List<GiftCertificate> getQueryWithConditions(Map<String, String> mapWithFilters) {
//        certificateValidator.validateMapKeys(mapWithFilters);
//        return giftCertificateDao.getQueryWithConditions(mapWithFilters);
//    }
//}