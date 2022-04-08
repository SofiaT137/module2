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
import org.springframework.transaction.annotation.Transactional;
import com.epam.esm.jbdc.impl.sql_creator.SQLCreator;

import java.sql.PreparedStatement;
import java.util.*;
import java.util.stream.Collectors;

import static com.epam.esm.jbdc.sql_queries.GiftCertificateQueries.*;
import static com.epam.esm.exceptions.ExceptionErrorCode.*;
import static com.epam.esm.entity.GiftCertificateTableColumns.*;
@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    private JdbcTemplate jdbcTemplate;

    private static final String INSERT_CERTIFICATE_ERROR_MESSAGE = "Cannot add this certificate!";
    private static final String CANNOT_FIND_CERTIFICATE_ERROR_MESSAGE = "Cannot find this certificate!";
    private static final String CANNOT_FIND_ANY_CERTIFICATE_ERROR_MESSAGE = "Cannot find any certificate!";
    private static final String CANNOT_DELETE_CERTIFICATE_ERROR_MESSAGE = "Cannot delete this certificate!";
    private static final String CANNOT_UPDATE_CERTIFICATE_ERROR_MESSAGE = "Cannot update this certificate!";

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void insert(GiftCertificate entity) throws DaoException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(
                    connection -> {
                        PreparedStatement ps = connection.prepareStatement(INSERT_GIFT_CERTIFICATE, new String[]{"gift_certification_id"});
                        ps.setString(1, entity.getName());
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

    private void addTagsToCertificate(long id, List<Tag> tagList){
        if (tagList.isEmpty()){
            return;
        }
        List<Long> tagsId = getSetWithTagsId(tagList);
        tagsId.forEach(tagId -> jdbcTemplate.update(ADD_CERTIFICATE_TAGS,id,tagId));
    }

    private List<Long> getSetWithTagsId(List<Tag> tagSet){
        List<Long> listOfTagsId = new ArrayList<>();
        TagDao tagDao = new TagDaoImpl();
        tagSet.forEach(tag ->{
            try{
                listOfTagsId.add(tagDao.getTagByName(tag.getName()).getId());
            }catch (DaoException exception){
                exception.getMessage();
                exception.getErrorCode();
            }
        });
        return listOfTagsId;
    }

    @Override
    public GiftCertificate getById(long id) throws DaoException {
        List<GiftCertificate> certificates = jdbcTemplate.query(GET_GIFT_CERTIFICATE_BY_ID, new GiftCertificateMapper());
        if (certificates.size()!=1){
            throw new DaoException(CANNOT_FIND_CERTIFICATE_ERROR_MESSAGE,DATASOURCE_NOT_FOUND_BY_ID);
        }
        return certificates.get(0);
    }

    @Override
    public List<GiftCertificate> getAll() throws DaoException {
        List<GiftCertificate> certificates = jdbcTemplate.query(GET_GIFT_CERTIFICATES, new GiftCertificateMapper());
        if (certificates.isEmpty()){
            throw new DaoException(CANNOT_FIND_ANY_CERTIFICATE_ERROR_MESSAGE,DATASOURCE_NOT_FOUND);
        }
        return certificates;
    }

    @Override
    public void update(GiftCertificate entity) throws DaoException {
        int rows = jdbcTemplate.update(generateUpdateQuery(getAllNewDataFields(entity)), new GiftCertificateMapper());
        if (rows == 0){
            throw new DaoException(CANNOT_UPDATE_CERTIFICATE_ERROR_MESSAGE,DATASOURCE_SAVING_ERROR);
        }
    }

    private String generateUpdateQuery (Map<String,String> fields){
        StringBuilder setPart = new StringBuilder();
        for (Map.Entry<String, String> stringStringEntry : fields.entrySet()) {
            String key = stringStringEntry.getKey();
            String value = stringStringEntry.getValue();
            if (!key.equals("id") && value != null){
                setPart.append(key).append("=").append("'").append(value).append("'");
            }
        }
        return UPDATE_GIFT_CERTIFICATE_SET + setPart + WHERE_ID + fields.get("id");
    }

    private Map<String,String> getAllNewDataFields(GiftCertificate certificate){
        Map<String,String> newDataFields = new HashMap<>();
        newDataFields.put(NAME,certificate.getName());
        newDataFields.put(DESCRIPTION,certificate.getDescription());
        newDataFields.put(PRICE,String.valueOf(certificate.getPrice()));
        newDataFields.put(DURATION, String.valueOf(certificate.getDuration()));
        newDataFields.put(CREATE_DATE,String.valueOf(certificate.getCreateDate()));
        newDataFields.put(LAST_UPDATE_DATE,String.valueOf(certificate.getLastUpdateDate()));
        return newDataFields;
    }

    @Override
    @Transactional
    public void deleteByID(long id) throws DaoException {
        deleteListOfCertificateTags(id);
        int rows = jdbcTemplate.update(DELETE_GIFT_CERTIFICATE,id);
        if (rows == 0){
            throw new DaoException(CANNOT_DELETE_CERTIFICATE_ERROR_MESSAGE,DATASOURCE_NOT_FOUND_BY_ID);
        }
    }


    private void deleteListOfCertificateTags(long id) throws DaoException {
        try {
            jdbcTemplate.update(DELETE_TAG_FROM_GIFT_CERTIFICATE_BY_CERTIFICATE_ID, id);
        }catch (DataAccessException exception){
            throw new DaoException(exception.getMessage(),DATASOURCE_NOT_FOUND_BY_ID);
        }
    }


    @Override
    public List<GiftCertificate> getQueryWithConditions(Map<String, String> mapWithFilters) throws DaoException {
        SQLCreator sqlCreator = new SQLCreator();
        List<GiftCertificate> certificates = jdbcTemplate.query(sqlCreator.createGetCertificateQuery(mapWithFilters), new GiftCertificateMapper());
        certificates = certificates.stream().distinct().collect(Collectors.toList());
        for (GiftCertificate certificate : certificates) {
            List<Tag> tags = jdbcTemplate.query(GET_TAGS_CONNECTED_WITH_CERTIFICATE, new TagMapper(), certificate.getId());
            certificate.setTags(tags);
        }
        return certificates;
    }

}

