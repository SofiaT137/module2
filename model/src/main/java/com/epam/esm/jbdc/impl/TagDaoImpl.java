package com.epam.esm.jbdc.impl;

import com.epam.esm.jbdc.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;
import com.epam.esm.jbdc.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

import static com.epam.esm.jbdc.sql_queries.GiftCertificateQueries.DELETE_TAG_FROM_GIFT_CERTIFICATE_BY_CERTIFICATE_ID;
import static com.epam.esm.jbdc.sql_queries.TagSQLQueries.*;
import static com.epam.esm.exceptions.ExceptionErrorCode.*;

@Repository
public class TagDaoImpl implements TagDao {

    private JdbcTemplate jdbcTemplate;

    private static final String INSERT_TAG_ERROR_MESSAGE = "Cannot insert this tag!";
    private static final String DELETE_TAG_ERROR_MESSAGE = "Cannot delete this tag!";
    private static final String CANNOT_FIND_TAG_ERROR_MESSAGE = "Cannot find this tag!";
    private static final String CANNOT_FIND_ANY_TAG_ERROR_MESSAGE = "Cannot find any tag!";

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insert(Tag entity) throws DaoException {
        int rows = jdbcTemplate.update(INSERT_TAG,entity.getName());
        if (rows == 0){
            throw new DaoException(INSERT_TAG_ERROR_MESSAGE,DATASOURCE_SAVING_ERROR);
        }
    }

    @Override
    public Tag getById(long id) throws DaoException {
        List<Tag> tagList = jdbcTemplate.query(GET_TAG_BY_ID, new TagMapper());
        if (tagList.size()!=1){
            throw new DaoException(CANNOT_FIND_TAG_ERROR_MESSAGE,DATASOURCE_NOT_FOUND_BY_ID);
        }
        return tagList.get(0);
    }

    @Override
    public List<Tag> getAll() throws DaoException {
        List<Tag> tagList = jdbcTemplate.query(GET_TAG_TABLE_ALL_INFO, new TagMapper());
        if (tagList.isEmpty()){
            throw new DaoException(CANNOT_FIND_ANY_TAG_ERROR_MESSAGE,DATASOURCE_NOT_FOUND);
        }
        return tagList;
    }

    @Override
    @Transactional
    public void deleteByID(long id) throws DaoException {
        deleteListOfCertificateTags(id);
        int rows = jdbcTemplate.update(DELETE_TAG,id);
        if (rows == 0){
            throw new DaoException(DELETE_TAG_ERROR_MESSAGE,DATASOURCE_NOT_FOUND_BY_ID);
        }
    }

    public void deleteListOfCertificateTags(long id) throws DaoException {
        try {
            jdbcTemplate.update(DELETE_TAG_FROM_GIFT_CERTIFICATE_BY_CERTIFICATE_ID, id);
        }catch (DataAccessException exception){
            throw new DaoException(exception.getMessage(),DATASOURCE_NOT_FOUND_BY_ID);
        }
    }

    @Override
    public Tag getTagByName(String name) throws DaoException {
        List<Tag> tagList = jdbcTemplate.query(GET_TAG_BY_NAME, new TagMapper());
        if (tagList.size()!=1){
            throw new DaoException(CANNOT_FIND_TAG_ERROR_MESSAGE,DATASOURCE_NOT_FOUND_BY_NAME);
        }
        return tagList.get(0);
    }
}
