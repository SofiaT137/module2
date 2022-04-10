package com.epam.esm.controllers;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;
import com.epam.esm.exceptions.ServiceException;
import com.epam.esm.jbdc.GiftCertificateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.epam.esm.exceptions.ExceptionErrorCode.DATASOURCE_SAVING_ERROR;
import static com.epam.esm.jbdc.sql_queries.TagSQLQueries.INSERT_TAG;

@RestController
@RequestMapping("/gift_certificates")
public class GiftCertificateController {

    @Autowired
    private GiftCertificateDao giftCertificateDao;

    @GetMapping("/{id}")
    public GiftCertificate getCertificateByID(@PathVariable Long id) throws DaoException {
        return giftCertificateDao.getById(id);
    }

    @GetMapping
    public List<GiftCertificate> allGiftCertificates() throws DaoException {
        return giftCertificateDao.getAll();
    }

    @PostMapping
    public ResponseEntity insertTag(@RequestBody GiftCertificate giftCertificate) throws DaoException{
        giftCertificateDao.insert(giftCertificate);
        return ResponseEntity.status(HttpStatus.CREATED).body("Success");
    }

}
