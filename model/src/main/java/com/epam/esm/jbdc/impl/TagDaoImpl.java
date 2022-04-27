//package com.epam.esm.jbdc.impl;
//
//import com.epam.esm.jbdc.TagDao;
//import com.epam.esm.entity.Tag;
//import com.epam.esm.exceptions.DaoException;
//import com.epam.esm.jbdc.mapper.TagMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataAccessException;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.support.GeneratedKeyHolder;
//import org.springframework.jdbc.support.KeyHolder;
//import org.springframework.stereotype.Repository;
//
//import java.sql.PreparedStatement;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//
//import static com.epam.esm.jbdc.sql_queries.TagSQLQueries.*;
//import static com.epam.esm.exceptions.ExceptionErrorCode.*;
//
///**
// * Class TagDaoImpl is implementation of interface TagDao
// * This class is intended for work with 'tag' and 'gift_certificate_tag' tables
// */
//@Repository
//public class TagDaoImpl implements TagDao {
//
//    private final JdbcTemplate jdbcTemplate;
//    private final TagMapper tagMapper;
//
//    private static final String INSERT_TAG_ERROR_MESSAGE = "Cannot insert this tag!";
//    private static final String DELETE_TAG_ERROR_MESSAGE = "Cannot delete this tag!";
//    private static final String CANNOT_FIND_TAG_ERROR_MESSAGE = "Cannot find this tag!";
//    private static final String CANNOT_FIND_ANY_TAG_ERROR_MESSAGE = "Cannot find any tag!";
//    private static final String TAG_ID = "tag_id";
//
//    @Autowired
//    public TagDaoImpl(JdbcTemplate jdbcTemplate,TagMapper tagMapper) {
//        this.jdbcTemplate = jdbcTemplate;
//        this.tagMapper = tagMapper;
//    }
//
//
//    @Override
//    public void insert(Tag entity) {
//        try{
//            jdbcTemplate.update(INSERT_TAG,entity.getName());
//        }catch (DataAccessException exception){
//            throw new DaoException(exception.getLocalizedMessage(),DATASOURCE_SAVING_ERROR);
//        }
//    }
//
//
//    @Override
//    public Tag getById(long id){
//        List<Tag> tags;
//        try{
//            tags = jdbcTemplate.query(GET_TAG_BY_ID, tagMapper,id);
//        }catch (DataAccessException exception){
//            throw new DaoException(exception.getLocalizedMessage(),DATASOURCE_NOT_FOUND_BY_ID);
//        }
//        if (tags.isEmpty()){
//            throw new DaoException(CANNOT_FIND_TAG_ERROR_MESSAGE,DATASOURCE_NOT_FOUND_BY_ID);
//        }
//        return tags.get(0);
//    }
//
//
//    @Override
//    public List<Tag> getAll(){
//        List<Tag> tagList;
//        try{
//            tagList = jdbcTemplate.query(GET_TAG_TABLE_ALL_INFO, tagMapper);
//        }catch (DataAccessException exception){
//            throw new DaoException(exception.getLocalizedMessage(),DATASOURCE_NOT_FOUND);
//        }
//        if (tagList.isEmpty()){
//            throw new DaoException(CANNOT_FIND_ANY_TAG_ERROR_MESSAGE,DATASOURCE_NOT_FOUND);
//        }
//        return tagList;
//    }
//
//
//    @Override
//    public void deleteByID(long id){
//        int rows;
//        try{
//            rows = jdbcTemplate.update(DELETE_TAG,id);
//        }catch (DataAccessException exception){
//            throw new DaoException(exception.getLocalizedMessage(),DATASOURCE_NOT_FOUND_BY_ID);
//        }
//        if (rows == 0){
//            throw new DaoException(DELETE_TAG_ERROR_MESSAGE,DATASOURCE_NOT_FOUND_BY_ID);
//        }
//    }
//
//    @Override
//    public Tag getTagByName(String name) {
//        List<Tag> tagList;
//        try{
//            tagList = jdbcTemplate.query(GET_TAG_BY_NAME, tagMapper,name);
//        }catch (DataAccessException exception){
//            throw new DaoException(exception.getLocalizedMessage(),DATASOURCE_NOT_FOUND_BY_NAME);
//        }
//        if (tagList.size()!=1){
//            throw new DaoException(CANNOT_FIND_TAG_ERROR_MESSAGE,DATASOURCE_NOT_FOUND_BY_NAME);
//        }
//        return tagList.get(0);
//    }
//
//    @Override
//    public List<Long> getListWithTagsId(List<Tag> tagList) {
//        List<Long> getTagsId = new ArrayList<>();
//        for (Tag tag : tagList) {
//            KeyHolder keyHolder = new GeneratedKeyHolder();
//            try{
//                jdbcTemplate.update(
//                        connection -> {
//                            PreparedStatement ps = connection.prepareStatement(GET_TAG_BY_ID_OR_INSERT_TAG_AND_GET_ID, new String[]{TAG_ID});
//                            ps.setString(1,tag.getName());
//                            return ps;
//                        }, keyHolder);
//                getTagsId.add(Objects.requireNonNull(keyHolder.getKey()).longValue());
//            }catch (DataAccessException exception){
//                throw new DaoException(exception.getLocalizedMessage(),DATASOURCE_SAVING_ERROR);
//            }
//        }
//        return getTagsId;
//    }
//}
