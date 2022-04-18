package com.epam.esm.controllers;

import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;
import com.epam.esm.exceptions.ServiceException;
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

    private final TagService tagService;
    private static final String CREATED_MESSAGE = "Created!";
    private static final String DELETED_MESSAGE = "Deleted!";

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    public ResponseEntity<Object> insertTag(@RequestBody Tag tag) throws DaoException, ServiceException {
        tagService.insert(tag);
        return ResponseEntity.status(HttpStatus.OK).body(CREATED_MESSAGE);
    }

    @GetMapping("/{id}")
    public Tag getTagById(@PathVariable Long id) throws DaoException, ServiceException {
        return tagService.getById(id);
    }

    @GetMapping
    public List<Tag> getAllTags() throws DaoException {
        return tagService.getAll();
    }

    @GetMapping("/filter")
    public Tag getTagByName(String name) throws DaoException {
        return tagService.getTagByName(name);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTagByID(@PathVariable long id) throws DaoException, ServiceException {
        tagService.deleteByID(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(DELETED_MESSAGE);
    }

}
