package com.epam.esm.jbdc.impl.sql_creator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SQLCreatorTest {

    private static Map<String,String> testMap;
    private static SQLCreator sqlCreator;

    @BeforeAll
    static void init(){
        sqlCreator = new SQLCreator();
        testMap = new HashMap<>();
        testMap.put("tag_name","sea");
        testMap.put("sortByNameDESC","gift_certificate_name");
        testMap.put("partDescription","beach");
    }

    @Test
    void createGetCertificateQuery() {
        String actualExpression = sqlCreator.createGetCertificateQuery(testMap);
        String expectedExpression = "SELECT gc.gift_certificate_id, gift_certificate_name, description, price, duration, create_date, last_update_date FROM gift_certificate AS gc INNER JOIN gift_certificate_tag AS gct ON gc.gift_certificate_id = gct.gift_certificate_id INNER JOIN tag AS t on t.tag_id = gct.tag_id WHERE tag_name='sea' AND description LIKE '%beach%' ORDER BY gift_certificate_name DESC";
        assertEquals(expectedExpression,actualExpression);
    }
}