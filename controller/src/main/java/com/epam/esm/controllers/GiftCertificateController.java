package com.epam.esm.controllers;

import com.epam.esm.dto.impl.GiftCertificateDto;
import com.epam.esm.exceptions.DaoException;
import com.epam.esm.exceptions.ServiceException;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * GiftCertificateController class presents REST controller for GiftCertificate entity
 */
@RestController
@RequestMapping("/gift_certificates")
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;

    private static final String CREATED_MESSAGE = "Created!";
    private static final String UPDATED_MESSAGE = "Updated!";
    private static final String DELETED_MESSAGE = "Deleted!";

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    /**
     * Method getCertificateByID returns GiftCertificateDTO entity by its id
     * @param id Long id
     * @return GiftCertificateDTO entity
     */
    @GetMapping("/{id}")
    public GiftCertificateDto getCertificateByID(@PathVariable Long id) throws DaoException, ServiceException {
        return giftCertificateService.getById(id);
    }

    /**
     * Method getAllGiftCertificates returns all the GiftCertificateDTO entity
     * @return List of GiftCertificateDTO entity
     */
    @GetMapping
    public List<GiftCertificateDto> getAllGiftCertificates() throws DaoException {
        return giftCertificateService.getAll();
    }

    /**
     * Method insertCertificate insert the GiftCertificateDTO entity
     * @param giftCertificate  GiftCertificateDto giftCertificate
     * @return Response entity with HttpStatus "CREATED"
     */
    @PostMapping
    public ResponseEntity<Object> insertCertificate(@RequestBody GiftCertificateDto giftCertificate) throws DaoException, ServiceException {
        giftCertificateService.insert(giftCertificate);
        return ResponseEntity.status(HttpStatus.CREATED).body(CREATED_MESSAGE);
    }

    /**
     * Method giftCertificatesByParameter returns GiftCertificateDTO entity by all the transferred parameters
     * @param allRequestParams Map with parameters
     * @return List of GiftCertificateDTO entity
     */
    @GetMapping("/filter")
    public List<GiftCertificateDto> giftCertificatesByParameter(@RequestParam Map<String, String> allRequestParams) throws DaoException, ServiceException {
          return giftCertificateService.getQueryWithConditions(allRequestParams);
    }

    /**
     * Method deleteGiftCertificateByID deletes GiftCertificateDTO entity by its id
     * @param id Long id
     * @return Response entity with HttpStatus "OK"
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteGiftCertificateByID(@PathVariable long id) throws DaoException, ServiceException {
        giftCertificateService.deleteByID(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(DELETED_MESSAGE);
    }

    /**
     * Method updateGiftCertificate updates GiftCertificateDTO entity by its id
     * @param id Long id
     * @param giftCertificate GiftCertificateDto entity
     * @return Response entity with HttpStatus "OK"
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateGiftCertificate(@PathVariable long id,
                                                        @RequestBody GiftCertificateDto giftCertificate) throws DaoException, ServiceException {
        giftCertificateService.update(id, giftCertificate);
        return ResponseEntity.status(HttpStatus.CREATED).body(UPDATED_MESSAGE);
    }
}
