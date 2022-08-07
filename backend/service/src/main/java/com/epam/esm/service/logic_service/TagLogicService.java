package com.epam.esm.service.logic_service;

import com.epam.esm.pagination.Pagination;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.exceptions.CannotInsertEntityException;
import com.epam.esm.exceptions.NoSuchEntityException;
import com.epam.esm.service.TagService;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class TagLogicService is implementation of interface TagService
 * The class presents service logic layer for Tag entity
 */
@Service("tagLogicService")
public class TagLogicService implements TagService<Tag> {

    private final TagRepository tagRepository;
    private UserService<User> userLogicService;
    private final Pagination<Tag> pagination;

    private static final String NOT_UNIQUE_TAG_NAME_MESSAGE = "notUniqueTagName";
    private static final String CANNOT_FIND_THIS_TAG_BY_ID_MESSAGE = "noTagWithThatId";
    private static final String CANNOT_FIND_THIS_TAG_BY_NAME_MESSAGE = "noTagWithThatName";
    private static final String CANNOT_FIND_THE_MOST_WIDELY_USED_USER_TAG =
            "cannotFindTheMostWidelyUsedUserTagWithHigherOrderCost";

    @Autowired
    public TagLogicService(TagRepository tagRepository, Pagination<Tag> pagination){
        this.tagRepository = tagRepository;
        this.pagination = pagination;
    }

    @Autowired
    @Qualifier("userLogicService")
    public void setUserLogicService(UserService<User> userLogicService) {
        this.userLogicService = userLogicService;
    }

    @Override
    @Transactional
    public Tag insert(Tag entity){
        if (checkIfTagNameExists(entity.getName())){
            throw new CannotInsertEntityException(NOT_UNIQUE_TAG_NAME_MESSAGE);
        }
        return tagRepository.save(entity);
    }

    @Override
    public Tag getById(long id) {
        Optional<Tag> receivedTagById = tagRepository.findById(id);
        if (!receivedTagById.isPresent()){
            throw new NoSuchEntityException(CANNOT_FIND_THIS_TAG_BY_ID_MESSAGE);
        }
        return receivedTagById.get();
    }

    @Override
    public Page<Tag> getAll(int pageNumber,int pageSize){
        return pagination.checkHasContent(tagRepository.findAll(PageRequest.of(pageNumber,pageSize)));
    }

    @Override
    @Transactional
    public void deleteByID(long id) {
        Optional<Tag> receivedTagById = tagRepository.findById(id);
        if (!receivedTagById.isPresent()){
            throw new NoSuchEntityException(CANNOT_FIND_THIS_TAG_BY_ID_MESSAGE);
        }
        tagRepository.deleteParentRelationShips(id);
        tagRepository.delete(receivedTagById.get());
    }

    @Override
    public Tag findTheMostWidelyUsedUserTagWithHigherOrderCost(Long userId) {
        User receivedUserById = userLogicService.getById(userId);
        Optional<Tag> resultTag = tagRepository.
                findTheMostWidelyUsedUserTagWithHighersOrderCost(receivedUserById.getId());
        if (!resultTag.isPresent()){
            throw new NoSuchEntityException(CANNOT_FIND_THE_MOST_WIDELY_USED_USER_TAG);
        }
        return resultTag.get();
    }
    @Override
    public Tag findTagByTagName(String name) {
        Optional<Tag> tag = tagRepository.findTagByName(name);
        if (!tag.isPresent()){
            throw new NoSuchEntityException(CANNOT_FIND_THIS_TAG_BY_NAME_MESSAGE);
        }
        return tag.get();
    }

    @Override
    public List<Tag> getCertificateTagList(List<Tag> tagList){
        List<Tag> tags = new ArrayList<>();
        for (Tag entityHasTag : tagList) {
            Optional<Tag> currentTag = tagRepository.findTagByName(entityHasTag.getName());
            if (!currentTag.isPresent()){
                currentTag = Optional.of(tagRepository.save(entityHasTag));
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
