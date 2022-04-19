package com.epam.esm.jbdc.mapper;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import static com.epam.esm.entity.table_columns.GiftCertificateTableColumns.*;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class GiftCertificateMapper implements RowMapper.
 * This class helps JdbcTemplate for mapping rows of a ResultSet on a per-row basis.
 */
@Component
public class GiftCertificateMapper implements RowMapper<GiftCertificate> {

    /**
     * The mapRow method maps each row of data in the ResultSet.
     * @param rs ResultSet rs
     * @param rowNum Integer row number
     * @return GiftCertificate entity
     */
    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        GiftCertificate certificate = new GiftCertificate();
        certificate.setId(rs.getLong(ID));
        certificate.setGiftCertificateName(rs.getString(NAME));
        certificate.setDescription(rs.getString(DESCRIPTION));
        certificate.setPrice(rs.getDouble(PRICE));
        certificate.setDuration(rs.getInt(DURATION));
        certificate.setCreateDate(rs.getTimestamp(CREATE_DATE).toLocalDateTime());
        certificate.setLastUpdateDate(rs.getTimestamp(LAST_UPDATE_DATE).toLocalDateTime());
        return certificate;
    }
}
