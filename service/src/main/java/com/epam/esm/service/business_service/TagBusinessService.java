package com.epam.esm.service.business_service;

import com.epam.esm.converter.impl.TagConverter;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.ValidationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class TagBusinessService is implementation of the TagService interface
 * The class presents service business logic for Tag entity
 */
@Service("tagBusinessService")
public class TagBusinessService implements TagService<TagDto> {

    private final TagConverter tagConverter;
    private final ValidationFacade validationFacade;
    private TagService<Tag> tagLogicService;

    @Autowired
    public TagBusinessService(TagConverter tagConverter,ValidationFacade validationFacade) {
        this.tagConverter = tagConverter;
        this.validationFacade = validationFacade;
    }

    @Autowired
    @Qualifier("tagLogicService")
    public void setTagLogicService(TagService<Tag> tagLogicService) {
        this.tagLogicService = tagLogicService;
    }

    @Override
    public TagDto insert(TagDto entityDto) {
        validationFacade.validate(entityDto);
        Tag entity = tagConverter.convert(entityDto);
        return tagConverter.convert(tagLogicService.insert(entity));
    }

    @Override
    public void deleteByID(long id) {
        tagLogicService.deleteByID(id);
    }

    @Override
    public TagDto getById(long id) {
        Tag entity = tagLogicService.getById(id);
        return tagConverter.convert(entity);
    }

    @Override
    public Page<TagDto> getAll(int pageNumber,int pageSize) {
        Page<Tag> tagList = tagLogicService.getAll(pageNumber,pageSize);
        return tagList.map(tagConverter::convert);
    }

    @Override
    public TagDto findTheMostWidelyUsedUserTagWithHigherOrderCost(Long userId) {
        Tag tag = tagLogicService.findTheMostWidelyUsedUserTagWithHigherOrderCost(userId);
        return tagConverter.convert(tag);
    }

    @Override
    public TagDto findTagByTagName(String name) {
        Tag tag = tagLogicService.findTagByTagName(name);
        return tagConverter.convert(tag);
    }

    @Override
    public List<Tag> getCertificateTagList(List<Tag> tagList) {
        return tagLogicService.getCertificateTagList(tagList);
    }
}
