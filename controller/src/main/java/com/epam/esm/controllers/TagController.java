package com.epam.esm.controllers;

import com.epam.esm.dto.impl.TagDto;
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

    private TagService<TagDto> tagLogicService;

    @Autowired
    @Qualifier("tagBusinessService")
    public void setUserService(TagService<TagDto> tagService) {
        this.tagLogicService = tagService;
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
        tagLogicService.insert(entity);
        return ResponseEntity.status(HttpStatus.OK).body(CREATED_MESSAGE);
    }

    /**
     * Method getTagById returns Tag entity by its id
     * @param id Long id
     * @return Tag entity
     */
    @GetMapping("/{id}")
    public TagDto getTagById(@PathVariable Long id) {
        return tagLogicService.getById(id);
    }


    /**
     * Method getAllTags returns all the Tag entity
     *
     * @return List of Tag entity
     */
    @GetMapping("/filter")
    public TagDto findTheMostWidelyUsedUserTagWithHighersOrderCost(@RequestParam Long userId) {
        return tagLogicService.findTheMostWidelyUsedUserTagWithHigherOrderCost(userId);
    }

    @GetMapping
    public List<TagDto> getAllTags(@RequestParam int pageSize, @RequestParam int pageNumber) {
        return tagLogicService.getAll(pageSize,pageNumber);
    }

    /**
     * Method deleteTagByID deletes Tag entity by its id
     * @param id Long id
     * @return Response entity with HttpStatus "FOUND"
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTagByID(@PathVariable long id) {
        tagLogicService.deleteByID(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(DELETED_MESSAGE);
    }
}
