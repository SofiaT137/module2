package com.epam.esm.dao.sql_queries;

/**
 * The final class TagSQLQueries presents all SQL queries connected with 'tag' table
 */
public final class TagSQLQueries {

   private TagSQLQueries() {}

   public static final String INSERT_TAG = "INSERT INTO tag(tag_name) values(?)";
   public static final String GET_TAG_TABLE_ALL_INFO = "SELECT tag_id, tag_name FROM tag";
   public static final String GET_TAG_BY_ID = "SELECT tag_id, tag_name FROM tag WHERE tag_id = ?";
   public static final String GET_TAG_BY_NAME = "SELECT tag_id, tag_name FROM tag WHERE tag_name=?";
   public static final String DELETE_TAG = "DELETE FROM tag WHERE tag_id=?";
   public static final String DELETE_TAG_FROM_GIFT_CERTIFICATE_BY_TAG_ID = "DELETE FROM gift_certificate_tag WHERE tag_id=?";
   public static final String GET_TAG_BY_ID_OR_INSERT_TAG_AND_GET_ID = "INSERT INTO tag(tag_name) VALUES (?) ON DUPLICATE KEY UPDATE tag_id=LAST_INSERT_ID(tag_id)";
}
