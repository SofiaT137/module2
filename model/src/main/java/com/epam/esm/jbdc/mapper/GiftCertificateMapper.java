package com.epam.esm.jbdc.mapper;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.jdbc.core.RowMapper;
import static com.epam.esm.entity.GiftCertificateTableColumns.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GiftCertificateMapper implements RowMapper<GiftCertificate> {
    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {

        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(rs.getLong(ID));
        giftCertificate.setName(rs.getString(NAME));
        giftCertificate.setPrice(rs.getDouble(PRICE));
        giftCertificate.setDuration(rs.getInt(DURATION));
        giftCertificate.setCreateDate(rs.getString(CREATE_DATE));
        giftCertificate.setLastUpdateDate(rs.getString(LAST_UPDATE_DATE));


        return giftCertificate;

    }
}
