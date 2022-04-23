package com.epam.esm.jbdc.impl;

import com.epam.esm.jbdc.GiftCertificateDao;
import com.epam.esm.jbdc.TagDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;
import com.epam.esm.jbdc.mapper.GiftCertificateMapper;
import com.epam.esm.jbdc.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import com.epam.esm.jbdc.impl.sql_creator.SQLCreator;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.epam.esm.jbdc.sql_queries.GiftCertificateQueries.*;
import static com.epam.esm.exceptions.ExceptionErrorCode.*;
import static com.epam.esm.entity.table_columns.GiftCertificateTableColumns.*;

/**
 * Class GiftCertificateDaoImpl is implementation of interface GiftCertificateDao
 * This class is intended for work with 'gift certificate' and 'gift_certificate_tag' tables
 */
@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    private final JdbcTemplate jdbcTemplate;
    private final GiftCertificateMapper giftCertificateMapper;
    private final SQLCreator sqlCreator;
    private final TagDao tagDao;
    private final TagMapper tagMapper;

    private static final String INSERT_CERTIFICATE_ERROR_MESSAGE = "Cannot add this certificate!";
    private static final String CANNOT_FIND_CERTIFICATE_ERROR_MESSAGE = "Cannot find this certificate!";
    private static final String CANNOT_FIND_ANY_CERTIFICATE_ERROR_MESSAGE = "Cannot find any certificate!";
    private static final String CANNOT_DELETE_CERTIFICATE_ERROR_MESSAGE = "Cannot delete this certificate!";
    private static final String CANNOT_UPDATE_CERTIFICATE_ERROR_MESSAGE = "Cannot update this certificate!";
    private static final String CANNOT_GET_ALL_CERTIFICATE_TAGS_ERROR_MESSAGE = "Cannot get all certificate tags!";
    private static final String SOMETHING_WENT_WRONG_ERROR_MESSAGE = "Something didn't work out when we tried to add a new tag!";
    private static final String EQUALS = "=";
    private static final String QUOTE = "'";
    private static final String COMA = ", ";

    @Autowired
    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate, GiftCertificateMapper giftCertificateRowMapper,
                                  TagMapper tagMapper,SQLCreator queryCreator,TagDao tagDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.giftCertificateMapper = giftCertificateRowMapper;
        this.sqlCreator = queryCreator;
        this.tagDao = tagDao;
        this.tagMapper = tagMapper;
    }

    @Override
    public void insert(GiftCertificate entity) throws DaoException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(
                    connection -> {
                        PreparedStatement ps = connection.prepareStatement(INSERT_GIFT_CERTIFICATE, new String[]{ID});
                        ps.setString(1, entity.getGiftCertificateName());
                        ps.setString(2, entity.getDescription());
                        ps.setString(3, String.valueOf(entity.getPrice()));
                        ps.setString(4, String.valueOf(entity.getDuration()));
                        ps.setString(5, String.valueOf(entity.getCreateDate()));
                        ps.setString(6, String.valueOf(entity.getLastUpdateDate()));
                        return ps;
                    }, keyHolder);
            addTagsToCertificate(Objects.requireNonNull(keyHolder.getKey()).longValue(),entity.getTags());
        }catch (DataAccessException exception){
            throw new DaoException(exception.getLocalizedMessage(),DATASOURCE_SAVING_ERROR);
        }
    }

    public void addTagsToCertificate(long id, List<Tag> tagList) throws DaoException {
        if (tagList == null || tagList.isEmpty()){
            return;
        }
        List<Long> tagsId = tagDao.getListWithTagsId(tagList);
        try {
            tagsId.forEach(tagId -> jdbcTemplate.update(ADD_CERTIFICATE_TAGS,id,tagId));
        }catch (DataAccessException exception){
            throw new DaoException("Cannot add tags to the certificate!");
        }

    }

    private List<Tag> getCertificateTags(long id) throws DaoException {
        try {
            return jdbcTemplate.query(GET_ASSOCIATED_TAGS_QUERY, tagMapper, id);
        } catch (DataAccessException e) {
            throw new DaoException(CANNOT_GET_ALL_CERTIFICATE_TAGS_ERROR_MESSAGE,CANNOT_FIND_GIFT_CERTIFICATE_TAGS);
        }
    }

    @Override
    public GiftCertificate getById(long id) throws DaoException {
        List<GiftCertificate> certificates = jdbcTemplate.query(GET_GIFT_CERTIFICATE_BY_ID, giftCertificateMapper,id);
        if (certificates.isEmpty()){
            throw new DaoException(CANNOT_FIND_CERTIFICATE_ERROR_MESSAGE,DATASOURCE_NOT_FOUND_BY_ID);
        }
        GiftCertificate giftCertificate =  certificates.get(0);
        giftCertificate.setTags(getCertificateTags(id));
        return giftCertificate;
    }

    @Override
    public List<GiftCertificate> getAll() throws DaoException {
        List<GiftCertificate> certificates = jdbcTemplate.query(GET_GIFT_CERTIFICATES, giftCertificateMapper);
        if (certificates.isEmpty()){
            throw new DaoException(CANNOT_FIND_ANY_CERTIFICATE_ERROR_MESSAGE,DATASOURCE_NOT_FOUND);
        }
        for (GiftCertificate certificate : certificates) {
            certificate.setTags(getCertificateTags(certificate.getId()));
        }
        return certificates;
    }


    @Override
    public void update(GiftCertificate entity) throws DaoException {
        Map<String,Object> map = getAllNewDataFields(entity);
        String updateRequest = generateUpdateQuery(map);
        int rows = jdbcTemplate.update(updateRequest);
        if (rows == 0){
            throw new DaoException(CANNOT_UPDATE_CERTIFICATE_ERROR_MESSAGE,DATASOURCE_SAVING_ERROR);
        }
    }

    private String generateUpdateQuery (Map<String,Object> fields){
        StringBuilder setPart = new StringBuilder();
        for (Map.Entry<String, Object> stringObjectEntry : fields.entrySet()) {
            String key = stringObjectEntry.getKey();
            Object value = stringObjectEntry.getValue();
            if (!key.equals(ID) && value != null && !key.equals(CREATE_DATE)){
                setPart.append(key).append(EQUALS).append(QUOTE).append(value).append(QUOTE).append(COMA);
            }
        }
        setPart.deleteCharAt(setPart.length()-2);
        return UPDATE_GIFT_CERTIFICATE_SET + setPart + WHERE_ID + fields.get(ID);
    }

    private Map<String,Object> getAllNewDataFields(GiftCertificate certificate){
        Map<String,Object> newDataFields = new HashMap<>();
        newDataFields.put(ID,certificate.getId());
        newDataFields.put(NAME,certificate.getGiftCertificateName());
        newDataFields.put(DESCRIPTION,certificate.getDescription());
        newDataFields.put(PRICE,certificate.getPrice());
        newDataFields.put(DURATION, certificate.getDuration());
        newDataFields.put(CREATE_DATE,certificate.getCreateDate());
        newDataFields.put(LAST_UPDATE_DATE,certificate.getLastUpdateDate());
        return newDataFields;
    }


    @Override
    public void deleteByID(long id) throws DaoException {
        int rows = jdbcTemplate.update(DELETE_GIFT_CERTIFICATE,id);
        if (rows == 0){
            throw new DaoException(CANNOT_DELETE_CERTIFICATE_ERROR_MESSAGE,DATASOURCE_NOT_FOUND_BY_ID);
        }
    }


    @Override
    public void deleteListOfCertificateTags(long id) throws DaoException {
        int rows;
        try {
            rows = jdbcTemplate.update(DELETE_TAG_FROM_GIFT_CERTIFICATE_BY_CERTIFICATE_ID, id);
        }catch (DataAccessException exception){
            throw new DaoException(exception.getMessage(),DATASOURCE_NOT_FOUND_BY_ID);
        }
        if (rows == 0){
            throw new DaoException(DELETE_TAG_FROM_GIFT_CERTIFICATE_BY_CERTIFICATE_ID,DATASOURCE_NOT_FOUND_BY_ID);
        }
    }


    @Override
    public List<GiftCertificate> getQueryWithConditions(Map<String, String> mapWithFilters) throws DaoException {
        List<GiftCertificate> certificates = jdbcTemplate.query(sqlCreator.createGetCertificateQuery(mapWithFilters), giftCertificateMapper);
        certificates = certificates.stream().distinct().collect(Collectors.toList());
        for (GiftCertificate certificate : certificates) {
            certificate.setTags(getCertificateTags(certificate.getId()));
        }
        return certificates;
    }

}

