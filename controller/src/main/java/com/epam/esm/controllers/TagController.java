package com.epam.esm.controllers;

import com.epam.esm.entity.Tag;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.awt.print.Pageable;
import java.util.List;

/**
 * TagController class presents REST controller for tag entity
 */
@RestController
@RequestMapping("/tags")
public class TagController {

    @PersistenceContext
    private EntityManager entityManager;

//    @Autowired
//    public TagController(TagService<Tag> tagService) {
//        this.tagService = tagService;
//    }
//
//    /**
//     * Method insertTag insert the Tag entity
//     * @param tag Tag entity
//     * @return Response entity with HttpStatus "CREATED"
//     */
//    @PostMapping
//    public ResponseEntity<Object> insertTag(@RequestBody Tag tag) throws DaoException, ValidatorException {
//        tagService.insert(tag);
//        return ResponseEntity.status(HttpStatus.OK).body(CREATED_MESSAGE);
//    }
//
//    /**
//     * Method getTagById returns Tag entity by its id
//     * @param id Long id
//     * @return Tag entity
//     */
//    @GetMapping("/{id}")
//    public Tag getTagById(@PathVariable Long id) throws DaoException, ValidatorException {
//        return tagService.getById(id);
//    }
//

    /**
     * Method getAllTags returns all the Tag entity
     *
     * @return List of Tag entity
     */
    @GetMapping
    public List<Tag> getAllTags() {
        return entityManager.createQuery("Select t from Tag t", Tag.class)
                            .getResultList();
    }
}
//
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
//    /**
//     * Method deleteTagByID deletes Tag entity by its id
//     * @param id Long id
//     * @return Response entity with HttpStatus "FOUND"
//     */
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Object> deleteTagByID(@PathVariable long id) throws DaoException, ValidatorException {
//        tagService.deleteByID(id);
//        return ResponseEntity.status(HttpStatus.FOUND).body(DELETED_MESSAGE);
//    }
//}
