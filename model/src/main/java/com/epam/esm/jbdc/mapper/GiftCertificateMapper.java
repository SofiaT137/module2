package com.epam.esm.jbdc.mapper;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;


public class GiftCertificateMapper implements ResultSetExtractor<GiftCertificate> {

    @Override
    public GiftCertificate extractData(ResultSet rs) throws SQLException, DataAccessException {
        return null;
    }
}
