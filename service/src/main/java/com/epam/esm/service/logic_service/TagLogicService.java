package com.epam.esm.service.logic_service;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.exceptions.CannotInsertEntityException;
import com.epam.esm.exceptions.NoSuchEntityException;
import com.epam.esm.exceptions.ValidatorException;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final UserDao userDao;
    private final TagValidator tagValidator;

    private static final String CANNOT_INSERT_THIS_TAG_MESSAGE = "cannotInsertThisTag";
    private static final String CANNOT_FIND_THIS_TAG_MESSAGE = "noTagWithThatId";
    private static final String CANNOT_FIND_THIS_USER_MESSAGE = "noUserWithId";
    private static final String CANNOT_FIND_THE_MOST_WIDELY_USED_USER_TAG = "cannotFindTheMostWidelyUsedUserTagWithHigherOrderCost";

    @Autowired
    public TagLogicService(TagDao tagDao, UserDao userDao, TagValidator tagValidator) {
        this.tagDao = tagDao;
        this.userDao = userDao;
        this.tagValidator = tagValidator;
    }

    @Override
    @Transactional
    public void insert(Tag entity) throws ValidatorException {
        tagValidator.validate(entity);
        Optional<Tag> insertedTag = tagDao.insert(entity);
        if (!insertedTag.isPresent()){
            throw new CannotInsertEntityException(CANNOT_INSERT_THIS_TAG_MESSAGE,CANNOT_INSERT_ENTITY_CODE);
        }
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
        Optional<User> receivedUserById = userDao.getById(userId);
        if (!receivedUserById.isPresent()){
            throw new NoSuchEntityException(CANNOT_FIND_THIS_USER_MESSAGE,NO_SUCH_ENTITY_CODE);
        }
        Optional<Tag> receivedTagByConditions = tagDao.findTheMostWidelyUsedUserTagWithHighersOrderCost(userId);
        if (!receivedTagByConditions.isPresent()){
            throw new NoSuchEntityException(CANNOT_FIND_THE_MOST_WIDELY_USED_USER_TAG,NO_SUCH_ENTITY_CODE);
        }
        return receivedTagByConditions.get();
    }
}
