package com.epam.esm.jbdc.sql_queries;

public interface GiftCertificateQueries {

    String INSERT_GIFT_CERTIFICATE = "INSERT INTO gift_certificate(name, description, price, duration, create_date, " +
            "last_update_date) VALUES(?, ?, ?, ?, ?, ?)";
    String ADD_CERTIFICATE_TAGS = "INSERT INTO gift_certificate_tag(gift_certificate_id, tag_id) VALUES(?, ?)";
    String GET_GIFT_CERTIFICATE_BY_ID = "SELECT * FROM gift_certificate WHERE id=?";
    String GET_GIFT_CERTIFICATES = "SELECT name,description,price,duration,create_date,last_update_date FROM gift_certificate";
    String DELETE_GIFT_CERTIFICATE= "DELETE FROM gift_certificate WHERE id=?";
    String DELETE_TAG_FROM_GIFT_CERTIFICATE_BY_CERTIFICATE_ID = "DELETE FROM gift_certificate_tag WHERE gift_certificate_id=?";
    String UPDATE_GIFT_CERTIFICATE_SET = "UPDATE gift_certificate SET ";
    String WHERE_ID = "WHERE id=";
    String JOIN_BY_TAG_ID = "INNER JOIN gift_certificate_tag WHERE tag_id=?";

}
