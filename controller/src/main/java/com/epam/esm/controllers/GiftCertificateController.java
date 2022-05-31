package com.epam.esm.controllers;

import com.epam.esm.dto.GiftCertificateDto;

import com.epam.esm.hateoas.Hateoas;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * GiftCertificateController class presents REST controller for the GiftCertificate entity
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
    @Autowired
    @Qualifier("giftCertificateBusinessService")
    public void setGiftCertificateService(GiftCertificateService<GiftCertificateDto> giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    /**
     * Method getCertificateByID returns ResponseEntity with GiftCertificateDto entity and HttpStatus "OK"
     * @param id GiftCertificate id(Long value)
     * @return GiftCertificateDTO entity and HttpStatus "OK"
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getCertificateByID(@PathVariable Long id){
       GiftCertificateDto giftCertificateDto = giftCertificateService.getById(id);
       giftCertificateDtoHateoas.addLinks(giftCertificateDto);
       return new ResponseEntity<>(giftCertificateDto,HttpStatus.OK);
    }

    /**
     * Method getAllGiftCertificates returns all the GiftCertificateDTO entity
     * @param pageSize Number of GiftCertificateDTO entities per page (default value is 5)
     * @param pageNumber Number of the page with GiftCertificateDTO entities (default value is 1)
     * @return List of GiftCertificateDTO entity and HttpStatus "OK"
     */
    @GetMapping
    public ResponseEntity<Object> getAllGiftCertificates(@RequestParam(defaultValue = "1",required = false)
                                                                     int pageNumber,
                                                         @RequestParam (defaultValue = "5", required = false)
                                                                 int pageSize){
        Page<GiftCertificateDto> giftCertificates =  giftCertificateService.getAll(pageNumber,pageSize);
        giftCertificates.forEach(giftCertificateDtoHateoas::addLinks);
        return new ResponseEntity<>(giftCertificates,HttpStatus.OK);
    }

    /**
     * Method insertCertificate inserts the GiftCertificateDTO entity
     * @param giftCertificate  GiftCertificateDto giftCertificate
     * @return Response entity with HttpStatus "CREATED"
     */
    @PostMapping
    public ResponseEntity<Object> insertCertificate(@RequestBody GiftCertificateDto giftCertificate){
        giftCertificateService.insert(giftCertificate);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Method giftCertificatesByParameter returns the list of GiftCertificateDTO entity by all the
     * transferred parameters
     * @param allRequestParams Map with transferred parameters
     * @return Response entity with list of GiftCertificateDTO entity and HttpStatus "OK"
     */
    @GetMapping("/filter")
    public ResponseEntity<Object> giftCertificatesByParameter(@RequestParam MultiValueMap<String,
                                                                            String> allRequestParams){
        Page<GiftCertificateDto> giftCertificates =  giftCertificateService.getQueryWithConditions(allRequestParams);
        giftCertificates.forEach(giftCertificateDtoHateoas::addLinks);
        return new ResponseEntity<>(giftCertificates,HttpStatus.OK);
    }

    /**
     * Method deleteGiftCertificateByID deletes GiftCertificateDTO entity by its id
     * @param id GiftCertificate id(Long value)
     * @return Response entity with HttpStatus "NO_CONTENT"
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteGiftCertificateByID(@PathVariable long id){
        giftCertificateService.deleteByID(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Method updateGiftCertificate updates GiftCertificateDTO entity by its id
     * @param id GiftCertificate id(Long value)
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

