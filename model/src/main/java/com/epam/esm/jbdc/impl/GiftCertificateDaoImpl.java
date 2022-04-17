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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.epam.esm.jbdc.sql_queries.GiftCertificateQueries.*;
import static com.epam.esm.exceptions.ExceptionErrorCode.*;
import static com.epam.esm.entity.table_columns.GiftCertificateTableColumns.*;

/**
 * Class GiftCertificateDaoImpl is implementation of interface {@link GiftCertificateDao}
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
    private static final String SOMETHING_WENT_WRONG_ERROR_MESSAGE = "Something went wrong when we tried to add new tag";


    @Autowired
    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate, GiftCertificateMapper giftCertificateRowMapper,
                                  TagMapper tagMapper,SQLCreator queryCreator,TagDao tagDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.giftCertificateMapper = giftCertificateRowMapper;
        this.sqlCreator = queryCreator;
        this.tagDao = tagDao;
        this.tagMapper = tagMapper;
    }

    /**
     * Method for creating a query to insert the GiftCertificate entity in a 'gift_certificate' table.
     * @param  entity GiftCertificate entity
     */
    @Override
    public void insert(GiftCertificate entity) throws DaoException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(
                    connection -> {
                        PreparedStatement ps = connection.prepareStatement(INSERT_GIFT_CERTIFICATE, new String[]{CERTIFICATE_ID});
                        ps.setString(1, entity.getGiftCertificateName());
                        ps.setString(2, entity.getDescription());
                        ps.setString(3, String.valueOf(entity.getPrice()));
                        ps.setString(4, String.valueOf(entity.getDuration()));
                        ps.setString(5, String.valueOf(entity.getCreateDate()));
                        ps.setString(6, String.valueOf(entity.getLastUpdateDate()));
                        return ps;
                    }, keyHolder);
            addTagsToCertificate(Objects.requireNonNull(keyHolder.getKey()).longValue(),entity.getTags());
        }catch (Exception exception){
            throw new DaoException(INSERT_CERTIFICATE_ERROR_MESSAGE,DATASOURCE_SAVING_ERROR);
        }
    }

    /**
     * Method for creating a query to add tags to the GiftCertificate entity in a 'gift_certificate' table.
     * @param id GiftCertificate id
     * @param tagList list of tags
     */
    public void addTagsToCertificate(long id, List<Tag> tagList) throws DaoException {
        if (tagList == null || tagList.isEmpty()){
            return;
        }
        List<Long> tagsId = getListWithTagsId(tagList);
        tagsId.forEach(tagId -> jdbcTemplate.update(ADD_CERTIFICATE_TAGS,id,tagId));
    }

    private List<Tag> getCertificateTags(long id) throws DaoException {
        try {
            return jdbcTemplate.query(GET_ASSOCIATED_TAGS_QUERY, tagMapper, id);
        } catch (DataAccessException e) {
            throw new DaoException(CANNOT_GET_ALL_CERTIFICATE_TAGS_ERROR_MESSAGE,CANNOT_FIND_GIFT_CERTIFICATE_TAGS);
        }
    }

    private List<Long> getListWithTagsId(List<Tag> tagList) throws DaoException {
        List<Long> listOfTagsId = new ArrayList<>();
        boolean isTagExist = false;
        for (Tag tag : tagList) {
            try {
                listOfTagsId.add(tagDao.getTagByName(tag.getName()).getId());
                isTagExist = true;
            } catch (DaoException exception) {
                exception.getMessage();
            }
            if (!isTagExist){
                try {
                    tagDao.insert(tag);
                    listOfTagsId.add(tagDao.getTagByName(tag.getName()).getId());
                } catch (DaoException exception) {
                    throw new DaoException(SOMETHING_WENT_WRONG_ERROR_MESSAGE,CANNOT_ADD_NEW_TAG);
                }
            }
            isTagExist = false;
        }
        return listOfTagsId;
    }


    /**
     * Method for creating a query to get the GiftCertificate entity by its id in a 'gift_certificate' table.
     * @param id GiftCertificate id
     * @return GiftCertificate entity
     */
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

    /**
     * Method for creating a query to get all the GiftCertificate entity in a 'gift_certificate' table.
     * @return list of GiftCertificate entities
     */
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

    /**
     * Method for creating a query to update the GiftCertificate entity in a 'gift_certificate' table.
     * @param entity GiftCertificate entity
     */
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
            if (!key.equals(CERTIFICATE_ID) && value != null && !key.equals(CREATE_DATE)){
                setPart.append(key).append(EQUALS).append(QUOTE).append(value).append(QUOTE).append(COMA);
            }
        }
        setPart.deleteCharAt(setPart.length()-2);
        return UPDATE_GIFT_CERTIFICATE_SET + setPart +WHERE_ID + fields.get(CERTIFICATE_ID);
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

    /**
     * Method for creating a query to delete the GiftCertificate entity by its id in a 'gift_certificate' table.
     * @param id GiftCertificate id
     */
    @Override
    public void deleteByID(long id) throws DaoException {
        int rows = jdbcTemplate.update(DELETE_GIFT_CERTIFICATE,id);
        if (rows == 0){
            throw new DaoException(CANNOT_DELETE_CERTIFICATE_ERROR_MESSAGE,DATASOURCE_NOT_FOUND_BY_ID);
        }
    }


    /**
     * Method for creating a query to delete GiftCertificate entity relations in a 'gift_certificate_tag' table
     * @param id GiftCertificate id
     */
    public void deleteListOfCertificateTags(long id) throws DaoException {
        try {
            jdbcTemplate.update(DELETE_TAG_FROM_GIFT_CERTIFICATE_BY_CERTIFICATE_ID, id);
        }catch (DataAccessException exception){
            throw new DaoException(exception.getMessage(),DATASOURCE_NOT_FOUND_BY_ID);
        }
    }

    /**
     * Method for creating a query to get GiftCertificate entity by filters in a 'gift certificate' table
     * @param mapWithFilters Map(key=filter, value=passed value)
     * @return list of GiftCertificate entities
     */
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

