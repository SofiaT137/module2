package com.epam.esm.controllers;

import com.epam.esm.dto.TagDto;
import com.epam.esm.hateoas.Hateoas;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * TagController class presents REST controller for the Tag entity
 */
@RestController
@RequestMapping("/tags")
public class TagController {

    private TagService<TagDto> tagBusinessService;
    private final Hateoas<TagDto> tagDtoHateoas;

    @Autowired
    public TagController(Hateoas<TagDto> tagDtoHateoas) {
        this.tagDtoHateoas = tagDtoHateoas;
    }

    @Autowired
    @Qualifier("tagBusinessService")
    public void setUserService(TagService<TagDto> tagService) {
        this.tagBusinessService = tagService;
    }
    /**
     * Method insertTag inserts the TagDto entity
     * @param entity TagDto entity
     * @return Response entity with HttpStatus "CREATED"
     */
    @PostMapping
    public ResponseEntity<Object> insertTag(@RequestBody TagDto entity) {
        tagBusinessService.insert(entity);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Method getTagById returns TagDto entity by its id
     * @param id Tag id(Long value)
     * @return Response entity with TagDto entity and HttpStatus "OK"
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getTagById(@PathVariable Long id) {
        TagDto tagDto = tagBusinessService.getById(id);
        tagDtoHateoas.addLinks(tagDto);
        return new ResponseEntity<>(tagDto,HttpStatus.OK);
    }

    /**
     * Method findTheMostWidelyUsedUserTagWithHighersOrderCost returns the most widely used TagDto entity
     * with higher order costs
     * @param userId User id(Long value)
     * @return Response entity with TagDto entity and HttpStatus "OK"
     */
    @GetMapping("/filter/{userId}")
    public ResponseEntity<Object> findTheMostWidelyUsedUserTagWithHighersOrderCost(@PathVariable Long userId) {
       TagDto tagDto = tagBusinessService.findTheMostWidelyUsedUserTagWithHigherOrderCost(userId);
        tagDtoHateoas.addLinks(tagDto);
        return new ResponseEntity<>(tagDto, HttpStatus.OK);
    }

    /**
     * Method getAllTags returns all the TagDto entity
     * @param pageSize Number of TagDto entities per page(default value is 5)
     * @param pageNumber Number of the page with TagDto entities(default value is 1)
     * @return List of TagDto entity and HttpStatus "OK"
     */
    @GetMapping
    public ResponseEntity<Object> getAllTags(@RequestParam (defaultValue = "0", required = false) int pageNumber,
                                             @RequestParam(defaultValue = "5",required = false) int pageSize){
        Page<TagDto> tagDtoList = tagBusinessService.getAll(pageNumber,pageSize);
        tagDtoList.forEach(tagDtoHateoas::addLinks);
        return new ResponseEntity<>(tagDtoList, HttpStatus.OK);
    }

    /**
     * Method findTagByName returns TagDto entity by its name
     * @param tagName String tag name
     * @return Response entity with TagDto entity and HttpStatus "OK"
     */
    @GetMapping("/filter")
    public ResponseEntity<Object> findTagByName(@RequestParam String tagName) {
        TagDto tagDto = tagBusinessService.findTagByTagName(tagName);
        tagDtoHateoas.addLinks(tagDto);
        return new ResponseEntity<>(tagDto, HttpStatus.OK);
    }

    /**
     * Method deleteTagByID deletes TagDto entity by its id
     * @param id Tag id(Long value)
     * @return Response entity with HttpStatus "NO_CONTENT"
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTagByID(@PathVariable long id) {
        tagBusinessService.deleteByID(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
