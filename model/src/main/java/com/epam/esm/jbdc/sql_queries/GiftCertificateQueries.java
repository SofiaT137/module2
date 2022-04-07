package com.epam.esm.jbdc.sql_queries;

public interface GiftCertificateQueries {

    String INSERT_GIFT_CERTIFICATE = "INSERT INTO gift_certificate(gift_certificate_name, description, price, duration, create_date, " +
            "last_update_date) VALUES(?, ?, ?, ?, ?, ?)";
    String ADD_CERTIFICATE_TAGS = "INSERT INTO gift_certificate_tag(gift_certificate_id, tag_id) VALUES(?, ?)";
    String GET_GIFT_CERTIFICATE_BY_ID = "SELECT * FROM gift_certificate WHERE gift_certificate_id=?";
    String GET_GIFT_CERTIFICATES = "SELECT gift_certificate_name,description,price,duration,create_date,last_update_date \nFROM gift_certificate";
    String DELETE_GIFT_CERTIFICATE= "DELETE FROM gift_certificate WHERE id=?";
    String DELETE_TAG_FROM_GIFT_CERTIFICATE_BY_CERTIFICATE_ID = "DELETE FROM gift_certificate_tag WHERE gift_certificate_id=?";
    String UPDATE_GIFT_CERTIFICATE_SET = "UPDATE gift_certificate SET ";
    String WHERE_ID = "WHERE id=";
    String JOIN_BY_TAG_ID = " AS gc " +
            "INNER JOIN gift_certificate_tag " +
            "AS gct ON gc.gift_certificate_id = gct.gift_certificate_id " +
            "LEFT JOIN tag AS t on t.tag_id = gct.tag_id";
    String GET_TAGS_CONNECTED_WITH_CERTIFICATE = "SELECT tag_name FROM gift_certificate AS gs " +
            "INNER JOIN  gift_certificate_tag AS gct ON gs.gift_certificate_id = gct.gift_certificate_id " +
            "INNER JOIN tag AS t ON t.tag_id = gct.tag_id WHERE gs.gift_certificate_id = ?";
    String WHERE = " WHERE ";
    String AND = " AND ";
    String EQUALS_QUOTE = "='";
    String LIKE_QUOTE_PERCENT = " LIKE '%";
    String PERCENT_QUOTE = "%'";
    String QUOTE = "'";
    String ORDER_BY = " ORDER BY ";
    String DESC = " DESC";
    String COMA = ", ";

}
