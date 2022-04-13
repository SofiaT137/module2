package com.epam.esm.controllers;

import com.epam.esm.dto.impl.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exceptions.DaoException;
import com.epam.esm.exceptions.ServiceException;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/gift_certificates")
public class GiftCertificateController {

    private GiftCertificateService giftCertificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping("/{id}")
    public GiftCertificateDto getCertificateByID(@PathVariable Long id) throws DaoException, ServiceException {
        return giftCertificateService.getById(id);
    }

    @GetMapping
    public List<GiftCertificateDto> allGiftCertificates() throws DaoException {
        return giftCertificateService.getAll();
    }

    @PostMapping
    public ResponseEntity insertCertificate(@RequestBody GiftCertificateDto giftCertificate) throws DaoException, ServiceException {
        giftCertificateService.insert(giftCertificate);
        return ResponseEntity.status(HttpStatus.CREATED).body("Success");
    }

    @GetMapping("/filter")
    public List<GiftCertificateDto> giftCertificatesByParameter(@RequestParam Map<String, String> allRequestParams) throws DaoException {
          return giftCertificateService.getQueryWithConditions(allRequestParams);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteByID(@PathVariable long id) throws DaoException, ServiceException {
        giftCertificateService.deleteByID(id);
        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateGiftCertificate(@PathVariable long id,
                                                        @RequestBody GiftCertificateDto giftCertificate) throws DaoException, ServiceException {
        giftCertificateService.update(id, giftCertificate);
        return ResponseEntity.status(HttpStatus.CREATED).body("Success");
    }

}
