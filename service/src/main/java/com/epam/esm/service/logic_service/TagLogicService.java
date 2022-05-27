package com.epam.esm.service.logic_service;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.exceptions.CannotInsertEntityException;
import com.epam.esm.exceptions.NoSuchEntityException;
import com.epam.esm.exceptions.ValidatorException;
import com.epam.esm.service.TagService;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static com.epam.esm.exceptions.ExceptionErrorCode.*;

/**
 * Class TagLogicService is implementation of interface TagService
 * The class presents service logic layer for Tag entity
 */
@Service("tagLogicService")
public class TagLogicService implements TagService<Tag> {

    private final TagDao tagDao;
    private UserService<User> userLogicService;
//    private final TagValidator tagValidator;

    private static final String CANNOT_INSERT_THIS_TAG_MESSAGE = "cannotInsertThisTag";
    private static final String NOT_UNIQUE_TAG_NAME_MESSAGE = "notUniqueTagName";
    private static final String CANNOT_FIND_THIS_TAG_BY_ID_MESSAGE = "noTagWithThatId";
    private static final String CANNOT_FIND_THIS_TAG_BY_NAME_MESSAGE = "noTagWithThatName";
    private static final String CANNOT_FIND_THIS_USER_MESSAGE = "noUserWithId";
    private static final String CANNOT_FIND_THE_MOST_WIDELY_USED_USER_TAG = "cannotFindTheMostWidelyUsedUserTagWithHigherOrderCost";

    @Autowired
    public TagLogicService(TagDao tagDao){
        this.tagDao = tagDao;
    }

    @Autowired
    @Qualifier("userLogicService")
    public void setUserLogicService(UserService<User> userLogicService) {
        this.userLogicService = userLogicService;
    }

    @Override
    @Transactional
    public Tag insert(Tag entity) throws ValidatorException {
        if (checkIfTagNameExists(entity.getTagName())){
            throw new CannotInsertEntityException(NOT_UNIQUE_TAG_NAME_MESSAGE,CANNOT_INSERT_ENTITY_CODE);
        }
        Tag insertedTag = tagDao.save(entity);
        if (insertedTag == null){
            throw new CannotInsertEntityException(CANNOT_INSERT_THIS_TAG_MESSAGE,CANNOT_INSERT_ENTITY_CODE);
        }
        return insertedTag;
    }

    @Override
    public Tag getById(long id) {
        Optional<Tag> receivedTagById = tagDao.findById(id);
        if (!receivedTagById.isPresent()){
            throw new NoSuchEntityException(CANNOT_FIND_THIS_TAG_BY_ID_MESSAGE,NO_SUCH_ENTITY_CODE);
        }
        return receivedTagById.get();
    }

    @Override
    public Page<Tag> getAll(int pageNumber,int pageSize){
        return tagDao.findAll(PageRequest.of(pageNumber,pageSize));
    }

    @Override
    @Transactional
    public void deleteByID(long id) {
        Optional<Tag> receivedTagById = tagDao.findById(id);
        if (!receivedTagById.isPresent()){
            throw new NoSuchEntityException(CANNOT_FIND_THIS_TAG_BY_ID_MESSAGE,NO_SUCH_ENTITY_CODE);
        }
        tagDao.delete(receivedTagById.get());
    }

    @Override
    public Tag findTheMostWidelyUsedUserTagWithHigherOrderCost(Long userId) {
        User receivedUserById = userLogicService.getById(userId);
        List<Tag> resultTagList = tagDao.
                findTheMostWidelyUsedUserTagWithHighersOrderCost(receivedUserById.getId());
        if (resultTagList.isEmpty()){
            throw new NoSuchEntityException(CANNOT_FIND_THE_MOST_WIDELY_USED_USER_TAG,NO_SUCH_ENTITY_CODE);
        }
        return resultTagList.get(0);
    }
    @Override
    public Tag findTagByTagName(String name) {
        Optional<Tag> tag = tagDao.findByTagName(name);
        if (!tag.isPresent()){
            throw new NoSuchEntityException(CANNOT_FIND_THIS_TAG_BY_NAME_MESSAGE,NO_SUCH_ENTITY_CODE);
        }
        return tag.get();
    }

    @Override
    public List<Tag> getCertificateTagList(List<Tag> tagList){
        List<Tag> tags = new ArrayList<>();
        for (Tag entityHasTag : tagList) {
            Optional<Tag> currentTag = tagDao.findByTagName(entityHasTag.getTagName());
            if (!currentTag.isPresent()){
                currentTag = Optional.of(tagDao.save(entityHasTag));
                if (!currentTag.isPresent()){
                    throw new CannotInsertEntityException(CANNOT_INSERT_THIS_TAG_MESSAGE,CANNOT_INSERT_ENTITY_CODE);
                }
            }
            tags.add(currentTag.get());
        }
        return tags;
    }

    private boolean checkIfTagNameExists(String name){
        try{
            findTagByTagName(name);
        }catch (NoSuchEntityException exception){
            return false;
        }
        return true;
    }
}
