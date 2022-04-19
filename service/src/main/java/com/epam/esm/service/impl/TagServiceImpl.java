package com.epam.esm.service.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;
import com.epam.esm.exceptions.ServiceException;
import com.epam.esm.jbdc.TagDao;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Class TagServiceImpl is implementation of interface TagService
 * The class presents service layer logic for Tag entity
 */
@Service
public class TagServiceImpl implements TagService {

    private final TagDao tagDao;
    private final TagValidator tagValidator;

    @Autowired
    public TagServiceImpl(TagDao tagDao, TagValidator tagValidator) {
        this.tagDao = tagDao;
        this.tagValidator = tagValidator;
    }

    @Override
    @Transactional(rollbackFor = {DaoException.class,ServiceException.class})
    public void insert(Tag entity) throws ServiceException, DaoException {
        tagValidator.validate(entity);
        tagDao.insert(entity);
    }

    @Override
    public Tag getById(long id) throws DaoException, ServiceException {
        tagValidator.checkID(id);
        return tagDao.getById(id);
    }

    @Override
    public List<Tag> getAll() throws DaoException {
        return tagDao.getAll();
    }

    @Override
    public void deleteByID(long id) throws DaoException, ServiceException {
        tagValidator.checkID(id);
        tagDao.deleteByID(id);
    }

    @Override
    public Tag getTagByName(String name) throws DaoException {
        return tagDao.getTagByName(name);
    }
}
