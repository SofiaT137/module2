package com.epam.esm.service.logic_service;

import com.epam.esm.pagination.Pagination;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.CannotInsertEntityException;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class GiftCertificateLogicService is implementation of interface GiftCertificateService
 * The class presents service logic layer for GiftCertificate entity
 */
@Service("giftCertificateLogicService")
public class GiftCertificateLogicService implements GiftCertificateService<GiftCertificate> {

    private final GiftCertificateRepository giftCertificateRepository;
    private TagService<Tag> tagLogicService;
    private final Pagination<GiftCertificate> pagination;

    private static final String CANNOT_INSERT_THIS_GIFT_CERTIFICATE_MESSAGE = "giftCertificateNameIsNotUnique";
    private static final String CANNOT_FIND_THIS_GIFT_CERTIFICATE_MESSAGE = "noGiftCertificateWithThatId";
    private static final String CANNOT_UPDATE_THIS_GIFT_CERTIFICATE_MESSAGE = "cannotUpdateThisCertificate";
    private static final String TOO_MUCH_TRANSFERRED_PARAMETERS_MESSAGE = "forbiddenTransferredToMuchParameters";

    @Autowired
    public GiftCertificateLogicService(GiftCertificateRepository giftCertificateRepository,
                                       Pagination<GiftCertificate> pagination) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.pagination = pagination;
    }

    @Autowired
    @Qualifier("tagLogicService")
    public void setTagLogicService(TagService<Tag> tagLogicService) {
        this.tagLogicService = tagLogicService;
    }

    @Override
    @Transactional
    public GiftCertificate insert(GiftCertificate entity) {
        checkIfGiftCertificateNameIsUnique(entity.getName());
        if (!entity.getTagList().isEmpty()){
            addTagsToGiftCertificateList(entity);
        }
        return giftCertificateRepository.save(entity);
    }

    private void addTagsToGiftCertificateList(GiftCertificate entity){
        List<Tag> certificateTags = tagLogicService.getCertificateTagList(entity.getTagList());
        entity.setTagList(new ArrayList<>());
        certificateTags.forEach(entity::addTagToGiftCertificate);
    }

    private void checkIfGiftCertificateNameIsUnique(String giftCertificateName) {
        Optional<GiftCertificate> giftCertificate = giftCertificateRepository.findByName(giftCertificateName);
        if (giftCertificate.isPresent()){
            throw new CannotInsertEntityException(CANNOT_INSERT_THIS_GIFT_CERTIFICATE_MESSAGE);
        }
    }

    @Override
    public GiftCertificate getById(long id) {
        Optional<GiftCertificate> receivedGiftCertificateById = giftCertificateRepository.findById(id);
        if (!receivedGiftCertificateById.isPresent()){
            throw new NoSuchEntityException(CANNOT_FIND_THIS_GIFT_CERTIFICATE_MESSAGE);
        }
        return receivedGiftCertificateById.get();
    }

    @Override
    public Page<GiftCertificate> getAll(int pageNumber, int pageSize) {
        return pagination.checkHasContent(giftCertificateRepository.findAll(PageRequest.of(pageNumber,pageSize)));
    }


    @Override
    @Transactional
    public GiftCertificate update(Long id, GiftCertificate entity) {
        Optional<GiftCertificate> foundedCertificateById = giftCertificateRepository.findById(id);
        GiftCertificate found;
        if (!foundedCertificateById.isPresent()){
            throw new NoSuchEntityException(CANNOT_FIND_THIS_GIFT_CERTIFICATE_MESSAGE);
        }
        found = foundedCertificateById.get();
        if (!entity.getTagList().isEmpty()){
            found.setTagList(entity.getTagList());
            addTagsToGiftCertificateList(found);
        }
        Optional<GiftCertificate> giftCertificate = giftCertificateRepository.update(entity,
                foundedCertificateById.get());
        if (!giftCertificate.isPresent()){
            throw new NoSuchEntityException(CANNOT_UPDATE_THIS_GIFT_CERTIFICATE_MESSAGE);
        }
        return giftCertificate.get();
    }


    @Override
    @Transactional
    public void deleteByID(long id) {
        Optional<GiftCertificate> receivedGiftCertificateById = giftCertificateRepository.findById(id);
        if (!receivedGiftCertificateById.isPresent()){
            throw new NoSuchEntityException(CANNOT_FIND_THIS_GIFT_CERTIFICATE_MESSAGE);
        }
        giftCertificateRepository.delete(receivedGiftCertificateById.get());
    }

    @Override
    public Page<GiftCertificate> getQueryWithConditions(MultiValueMap<String, String> mapWithFilters) {
        int[] pageInfo = getPagination(mapWithFilters);
        Page< GiftCertificate> giftCertificate = giftCertificateRepository.
                findGiftCertificatesByTransferredConditions(mapWithFilters,pageInfo[0],pageInfo[1]);
        return pagination.checkHasContent(giftCertificate);
    }

    private int[] getPagination(MultiValueMap<String, String> mapWithFilters){
        int[] resultMassive = new int[2];
        resultMassive[0] = mapWithFilters.get("pageNumber") == null ?
                0 : Integer.parseInt(mapWithFilters.get("pageNumber").get(0));
        resultMassive[1] = mapWithFilters.get("pageSize") == null ?
                5 : Integer.parseInt(mapWithFilters.get("pageSize").get(0));
        return resultMassive;
    }
}
