package com.epam.esm.jbdc.sql_queries;

public interface TagSQLQueries {
   String INSERT_TAG = "INSERT INTO tag(tag_name) values(?)";
   String GET_TAG_TABLE_ALL_INFO = "SELECT tag_id, tag_name FROM tag";
   String GET_TAG_BY_ID = "SELECT tag_id, tag_name FROM tag WHERE tag_id = ?";
   String GET_TAG_BY_NAME = "SELECT tag_id, tag_name FROM tag WHERE tag_name=?";
   String DELETE_TAG = "DELETE FROM tag WHERE tag_id=?";
   String DELETE_TAG_FROM_GIFT_CERTIFICATE_BY_TAG_ID = "DELETE FROM gift_certificate_tag WHERE tag_id=?";
}
