package com.epam.esm.controllers;

import com.epam.esm.dto.TagDto;
import com.epam.esm.hateoas.Hateoas;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TagController class presents REST controller for tag entity
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

    private static final String CREATED_MESSAGE = "Created!";
    private static final String DELETED_MESSAGE = "Deleted!";

    /**
     * Method insertTag insert the Tag entity
     * @param entity Tag entity
     * @return Response entity with HttpStatus "CREATED"
     */
    @PostMapping
    public ResponseEntity<Object> insertTag(@RequestBody TagDto entity) {
        tagBusinessService.insert(entity);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Method getTagById returns Tag entity by its id
     * @param id Long id
     * @return Tag entity
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getTagById(@PathVariable Long id) {
        TagDto tagDto = tagBusinessService.getById(id);
        tagDtoHateoas.addLinks(tagDto);
        return new ResponseEntity<>(tagDto,HttpStatus.OK);
    }

    /**
     * Method getAllTags returns all the Tag entity
     *
     * @return List of Tag entity
     */
    @GetMapping("/filter/{userId}")
    public ResponseEntity<Object> findTheMostWidelyUsedUserTagWithHighersOrderCost(@PathVariable Long userId) {
       TagDto tagDto = tagBusinessService.findTheMostWidelyUsedUserTagWithHigherOrderCost(userId);
        tagDtoHateoas.addLinks(tagDto);
        return new ResponseEntity<>(tagDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> getAllTags(@RequestParam(defaultValue = "5",required = false) int pageSize,
                                             @RequestParam (defaultValue = "1", required = false) int pageNumber){
        List<TagDto> tagDtoList = tagBusinessService.getAll(pageSize,pageNumber);
        tagDtoList.forEach(tagDtoHateoas::addLinks);
        return new ResponseEntity<>(tagDtoList, HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<Object> findTagByName(@RequestParam String tagName) {
        TagDto tagDto = tagBusinessService.findTagByTagName(tagName);
        tagDtoHateoas.addLinks(tagDto);
        return new ResponseEntity<>(tagDto, HttpStatus.OK);
    }

    /**
     * Method deleteTagByID deletes Tag entity by its id
     * @param id Long id
     * @return Response entity with HttpStatus "NO_CONTENT"
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTagByID(@PathVariable long id) {
        tagBusinessService.deleteByID(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
