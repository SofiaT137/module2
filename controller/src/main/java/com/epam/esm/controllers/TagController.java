package com.epam.esm.controllers;

import com.epam.esm.dto.impl.GiftCertificateDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;
import com.epam.esm.exceptions.ServiceException;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tags")
public class TagController {

    private TagService tagService;

    @Autowired
    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    public ResponseEntity insertTag(@RequestBody Tag tag) throws DaoException, ServiceException {
        tagService.insert(tag);
        return ResponseEntity.status(HttpStatus.CREATED).body("Success");
    }

    @GetMapping("/{id}")
    public Tag getTagById(@PathVariable Long id) throws DaoException, ServiceException {
        return tagService.getById(id);
    }

    @GetMapping
    public List<Tag> getAll() throws DaoException {
        return tagService.getAll();
    }

    @GetMapping("/filter")
    public Tag getTagByName(String name) throws DaoException {
        return tagService.getTagByName(name);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteByID(@PathVariable long id) throws DaoException, ServiceException {
        tagService.deleteByID(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Success");
    }

}
