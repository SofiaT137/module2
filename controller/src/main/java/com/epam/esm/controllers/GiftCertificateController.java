package com.epam.esm.controllers;

import com.epam.esm.dto.GiftCertificateDto;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.hateoas.Hateoas;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * GiftCertificateController class presents REST controller for GiftCertificate entity
 */
@RestController
@RequestMapping("/gift_certificates")
public class GiftCertificateController {

    private GiftCertificateService<GiftCertificateDto> giftCertificateService;
    private final Hateoas<GiftCertificateDto> giftCertificateDtoHateoas;

    @Autowired
    public GiftCertificateController(Hateoas<GiftCertificateDto> giftCertificateDtoHateoas) {
        this.giftCertificateDtoHateoas = giftCertificateDtoHateoas;
    }

    private static final String CREATED_MESSAGE = "Created!";
    private static final String UPDATED_MESSAGE = "Updated!";
    private static final String DELETED_MESSAGE = "Deleted!";

    @Autowired
    @Qualifier("giftCertificateBusinessService")
    public void setGiftCertificateService(GiftCertificateService<GiftCertificateDto> giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    /**
     * Method getCertificateByID returns GiftCertificateDTO entity by its id
     * @param id Long id
     * @return GiftCertificateDTO entity
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getCertificateByID(@PathVariable Long id){
       GiftCertificateDto giftCertificateDto = giftCertificateService.getById(id);
       giftCertificateDtoHateoas.addLinks(giftCertificateDto);
       return new ResponseEntity<>(giftCertificateDto,HttpStatus.OK);
    }

    /**
     * Method getAllGiftCertificates returns all the GiftCertificateDTO entity
     * @return List of GiftCertificateDTO entity
     */
    @GetMapping
    public ResponseEntity<Object> getAllGiftCertificates(@RequestParam int pageSize, @RequestParam int pageNumber) {
        List<GiftCertificateDto> giftCertificates =  giftCertificateService.getAll(pageSize,pageNumber);
        giftCertificates.forEach(giftCertificateDtoHateoas::addLinks);
        return new ResponseEntity<>(giftCertificates,HttpStatus.OK);
    }

    /**
     * Method insertCertificate insert the GiftCertificateDTO entity
     * @param giftCertificate  GiftCertificateDto giftCertificate
     * @return Response entity with HttpStatus "CREATED"
     */
    @PostMapping
    public ResponseEntity<Object> insertCertificate(@RequestBody GiftCertificateDto giftCertificate){
        giftCertificateService.insert(giftCertificate);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Method giftCertificatesByParameter returns GiftCertificateDTO entity by all the transferred parameters
     * @param allRequestParams Map with parameters
     * @return List of GiftCertificateDTO entity
     */
    @GetMapping("/filter")
    public ResponseEntity<Object> giftCertificatesByParameter(@RequestParam MultiValueMap<String, String> allRequestParams){
        List<GiftCertificateDto> giftCertificates =  giftCertificateService.getQueryWithConditions(allRequestParams);
        giftCertificates.forEach(giftCertificateDtoHateoas::addLinks);
        return new ResponseEntity<>(giftCertificates,HttpStatus.OK);
    }

    /**
     * Method deleteGiftCertificateByID deletes GiftCertificateDTO entity by its id
     * @param id Long id
     * @return Response entity with HttpStatus "OK"
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteGiftCertificateByID(@PathVariable long id){
        giftCertificateService.deleteByID(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Method updateGiftCertificate updates GiftCertificateDTO entity by its id
     * @param id Long id
     * @param giftCertificate GiftCertificateDto entity
     * @return Response entity with HttpStatus "OK"
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateGiftCertificate(@PathVariable long id,
                                                        @RequestBody GiftCertificateDto giftCertificate){
        giftCertificateService.update(id, giftCertificate);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

