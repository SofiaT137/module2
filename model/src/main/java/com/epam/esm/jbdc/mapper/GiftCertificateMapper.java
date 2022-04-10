package com.epam.esm.jbdc.mapper;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import static com.epam.esm.entity.GiftCertificateTableColumns.*;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GiftCertificateMapper implements RowMapper<GiftCertificate> {

    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        GiftCertificate certificate = new GiftCertificate();
        certificate.setId(rs.getLong(ID));
        certificate.setGift_certificate_name(rs.getString(NAME));
        certificate.setDescription(rs.getString(DESCRIPTION));
        certificate.setPrice(rs.getDouble(PRICE));
        certificate.setDuration(rs.getInt(DURATION));
        certificate.setCreate_date(rs.getTimestamp(CREATE_DATE).toLocalDateTime());
        certificate.setLast_update_date(rs.getTimestamp(LAST_UPDATE_DATE).toLocalDateTime());
        return certificate;

    }
}
