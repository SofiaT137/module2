package com.epam.esm.jbdc.sql_queries;

/**
 * The final class GiftCertificateQueries presents all SQL queries connected with 'gift_certificate' table
 */
public final class GiftCertificateQueries {

    private GiftCertificateQueries() {}

    public static final String INSERT_GIFT_CERTIFICATE = "INSERT INTO gift_certificate(gift_certificate_name, description, price, duration, create_date,last_update_date) VALUES(?, ?, ?, ?, ?, ?)";
    public static final String ADD_CERTIFICATE_TAGS = "INSERT INTO gift_certificate_tag(gift_certificate_id, tag_id) VALUES(?, ?)";
    public static final String GET_GIFT_CERTIFICATE_BY_ID = "SELECT * FROM gift_certificate WHERE gift_certificate_id=?";
    public static final String GET_GIFT_CERTIFICATES = "SELECT gc.gift_certificate_id, gift_certificate_name, description, price, duration, create_date, last_update_date FROM gift_certificate AS gc ";
    public static final String DELETE_GIFT_CERTIFICATE= "DELETE FROM gift_certificate WHERE gift_certificate_id=?";
    public static final String DELETE_TAG_FROM_GIFT_CERTIFICATE_BY_CERTIFICATE_ID = "DELETE FROM gift_certificate_tag WHERE gift_certificate_id=?";
    public static final String UPDATE_GIFT_CERTIFICATE_SET = "UPDATE gift_certificate SET ";
    public static final String WHERE_ID = " WHERE gift_certificate_id = ";
    public static final String JOIN_BY_TAG_ID = "INNER JOIN gift_certificate_tag AS gct ON gc.gift_certificate_id = gct.gift_certificate_id INNER JOIN tag AS t on t.tag_id = gct.tag_id";
    public static final String GET_ASSOCIATED_TAGS_QUERY = "SELECT t.tag_id,t.tag_name FROM tag t INNER JOIN gift_certificate_tag gct ON t.tag_id = gct.tag_id WHERE gct.gift_certificate_id=?";
}
