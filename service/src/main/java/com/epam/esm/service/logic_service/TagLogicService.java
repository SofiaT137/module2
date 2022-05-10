package com.epam.esm.service.logic_service;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.exceptions.CannotInsertEntityException;
import com.epam.esm.exceptions.NoSuchEntityException;
import com.epam.esm.exceptions.ValidatorException;
import com.epam.esm.service.TagService;
import com.epam.esm.service.UserService;
import com.epam.esm.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static com.epam.esm.exceptions.ExceptionErrorCode.*;

/**
 * Class TagServiceImpl is implementation of interface TagService
 * The class presents service layer logic for Tag entity
 */
@Service("tagLogicService")
public class TagLogicService implements TagService<Tag> {

    private final TagDao tagDao;
    private UserService<User> userLogicService;
    private final TagValidator tagValidator;

    private static final String CANNOT_INSERT_THIS_TAG_MESSAGE = "cannotInsertThisTag";
    private static final String CANNOT_FIND_THIS_TAG_MESSAGE = "noTagWithThatId";
    private static final String CANNOT_FIND_THIS_USER_MESSAGE = "noUserWithId";
    private static final String CANNOT_FIND_THE_MOST_WIDELY_USED_USER_TAG = "cannotFindTheMostWidelyUsedUserTagWithHigherOrderCost";

    @Autowired
    public TagLogicService(TagDao tagDao, TagValidator tagValidator) {
        this.tagDao = tagDao;
        this.tagValidator = tagValidator;
    }

    @Autowired
    @Qualifier("userLogicService")
    public void setUserLogicService(UserService<User> userLogicService) {
        this.userLogicService = userLogicService;
    }

    @Override
    @Transactional
    public Tag insert(Tag entity) throws ValidatorException {
        tagValidator.validate(entity);
        Optional<Tag> insertedTag = tagDao.insert(entity);
        if (!insertedTag.isPresent()){
            throw new CannotInsertEntityException(CANNOT_INSERT_THIS_TAG_MESSAGE,CANNOT_INSERT_ENTITY_CODE);
        }
        return insertedTag.get();
    }

    @Override
    public Tag getById(long id) {
        Optional<Tag> receivedTagById = tagDao.getById(id);
        if (!receivedTagById.isPresent()){
            throw new NoSuchEntityException(CANNOT_FIND_THIS_TAG_MESSAGE,NO_SUCH_ENTITY_CODE);
        }
        return receivedTagById.get();
    }

    @Override
    public List<Tag> getAll(int pageSize,int pageNumber){
        return tagDao.getAll(pageSize,pageNumber);
    }

    @Override
    @Transactional
    public void deleteByID(long id) {
        tagValidator.checkID(id);
        Optional<Tag> receivedTagById = tagDao.getById(id);
        if (!receivedTagById.isPresent()){
            throw new NoSuchEntityException(CANNOT_FIND_THIS_TAG_MESSAGE,NO_SUCH_ENTITY_CODE);
        }
        tagDao.deleteByID(id);
    }

    @Override
    public Tag findTheMostWidelyUsedUserTagWithHigherOrderCost(Long userId) {
        tagValidator.checkID(userId);
        User receivedUserById = userLogicService.getById(userId);
        Optional<Tag> receivedTagByConditions = tagDao.
                findTheMostWidelyUsedUserTagWithHighersOrderCost(receivedUserById.getId());
        if (!receivedTagByConditions.isPresent()){
            throw new NoSuchEntityException(CANNOT_FIND_THE_MOST_WIDELY_USED_USER_TAG,NO_SUCH_ENTITY_CODE);
        }
        return receivedTagByConditions.get();
    }

    @Override
    public Tag findTagByTagName(String name) {
        Optional<Tag> tag = tagDao.findTagByTagName(name);
        if (!tag.isPresent()){
            throw new NoSuchEntityException(CANNOT_FIND_THIS_TAG_MESSAGE,NO_SUCH_ENTITY_CODE);
        }
        return tag.get();
    }

    @Override
    public List<Tag> getCertificateTagList(List<Tag> tagList){
        List<Tag> tags = new ArrayList<>();
        tagList.forEach(tagValidator::validate);
        for (Tag entityHasTag : tagList) {
            Optional<Tag> currentTag = tagDao.findTagByTagName(entityHasTag.getName());
            if (!currentTag.isPresent()){
                currentTag = tagDao.insert(entityHasTag);
                if (!currentTag.isPresent()){
                    throw new CannotInsertEntityException(CANNOT_INSERT_THIS_TAG_MESSAGE,CANNOT_INSERT_ENTITY_CODE);
                }
            }
           tags.add(currentTag.get());
        }
        return tags;
    }
}
