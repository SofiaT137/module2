package com.epam.esm.jbdc.sql_queries;

public interface TagSQLQueries {
   String INSERT_TAG = "INSERT INTO tag(name) values(?)";
   String DELETE_TAG = "DELETE FROM tag WHERE id=?";
   String GET_TAG_TABLE_ALL_INFO = "SELECT * FROM tag";
   String GET_TAG_BY_ID = "SELECT id, name FROM tag WHERE id=?";
   String GET_TAG_BY_NAME = "SELECT id, name FROM tag WHERE name=?";
   String DELETE_TAG_FROM_GIFT_CERTIFICATE_BY_CERTIFICATE_ID = "DELETE FROM gift_certificate_tag WHERE tag_id=?";
}
