package com.epam.esm.controllers;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * TagController class presents REST controller for tag entity
 */
@RestController
@RequestMapping("/tags")
public class TagController {

    private final TagDao tagDao;

    @Autowired
    public TagController(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    private static final String CREATED_MESSAGE = "Created!";
    private static final String DELETED_MESSAGE = "Deleted!";


    /**
     * Method insertTag insert the Tag entity
     * @param tag Tag entity
     * @return Response entity with HttpStatus "CREATED"
     */
    @PostMapping
    public ResponseEntity<Object> insertTag(@RequestBody Tag tag) {
        tagDao.insert(tag);
        return ResponseEntity.status(HttpStatus.OK).body(CREATED_MESSAGE);
    }

    /**
     * Method getTagById returns Tag entity by its id
     * @param id Long id
     * @return Tag entity
     */
    @GetMapping("/{id}")
    public Optional<Tag> getTagById(@PathVariable Long id) {
        return tagDao.getById(id);
    }


    /**
     * Method getAllTags returns all the Tag entity
     *
     * @return List of Tag entity
     */
    @GetMapping("/filter")
    public Optional<Tag> findTheMostWidelyUsedUserTagWithHighersOrderCost(@RequestParam Long userId) {
        return tagDao.findTheMostWidelyUsedUserTagWithHighersOrderCost(userId);
    }

    @GetMapping
    public List<Tag> getAllTags(@RequestParam int pageSize, @RequestParam int pageNumber) {
        return tagDao.getAll(pageSize,pageNumber);
    }

    /**
     * Method deleteTagByID deletes Tag entity by its id
     * @param id Long id
     * @return Response entity with HttpStatus "FOUND"
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTagByID(@PathVariable long id) {
        tagDao.deleteByID(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(DELETED_MESSAGE);
    }
}
