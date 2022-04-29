package com.epam.esm.controllers;

import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final String CREATED_MESSAGE = "Created!";
    private static final String DELETED_MESSAGE = "Deleted!";

    private final TagService<Tag> tagService;

    @Autowired
    public TagController(TagService<Tag> tagService) {
        this.tagService = tagService;
    }

    /**
     * Method insertTag insert the Tag entity
     * @param tag Tag entity
     * @return Response entity with HttpStatus "CREATED"
     */
    @PostMapping
    public ResponseEntity<Object> insertTag(@RequestBody Tag tag) {
        tagService.insert(tag);
        return ResponseEntity.status(HttpStatus.OK).body(CREATED_MESSAGE);
    }

    /**
     * Method getTagById returns Tag entity by its id
     * @param id Long id
     * @return Tag entity
     */
    @GetMapping("/{id}")
    public Tag getTagById(@PathVariable Long id) {
        return tagService.getById(id);
    }


    /**
     * Method getAllTags returns all the Tag entity
     *
     * @return List of Tag entity
     */
    @GetMapping
    public List<Tag> getAllTags() {
        return tagService.getAll();
    }


//    /**
//     * Method getTagByName returns Tag entity by its name
//     * @param name Tag name
//     * @return the Tag entity
//     */
//    @GetMapping("/filter")
//    public Tag getTagByName(String name) throws DaoException {
//        return tagService.getTagByName(name);
//    }
//
    /**
     * Method deleteTagByID deletes Tag entity by its id
     * @param id Long id
     * @return Response entity with HttpStatus "FOUND"
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTagByID(@PathVariable long id) {
        tagService.deleteByID(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(DELETED_MESSAGE);
    }
}
