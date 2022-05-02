package com.epam.esm.service.logic_service;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.ValidatorException;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Class TagServiceImpl is implementation of interface TagService
 * The class presents service layer logic for Tag entity
 */
@Service
public class TagLogicService implements TagService<Tag> {

    private final TagDao tagDao;
    private final TagValidator tagValidator;

    @Autowired
    public TagLogicService(TagDao tagDao, TagValidator tagValidator) {
        this.tagDao = tagDao;
        this.tagValidator = tagValidator;
    }

    @Override
    public void insert(Tag entity) throws ValidatorException {
        tagValidator.validate(entity);
        tagDao.insert(entity);
    }

    @Override
    public Tag getById(long id) {
        Optional<Tag> byID = tagDao.getById(id);
        if (!byID.isPresent()){
            throw new RuntimeException("No such tag!");
        }
        return byID.get();
    }

    @Override
    public List<Tag> getAll(int pageSize,int pageNumber){
        return tagDao.getAll(pageSize,pageNumber);
    }

    @Override
    public void deleteByID(long id) {
        tagValidator.checkID(id);
        tagDao.deleteByID(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return null;
    }
}
