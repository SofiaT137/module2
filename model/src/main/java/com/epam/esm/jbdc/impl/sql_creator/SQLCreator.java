package com.epam.esm.jbdc.impl.sql_creator;

import org.springframework.stereotype.Component;

import java.util.Map;

import static com.epam.esm.jbdc.sql_queries.GiftCertificateQueries.*;
import static com.epam.esm.entity.table_columns.GiftCertificateTableColumns.*;

/**
 * SQLCreator class helps to create complex SQL queries
 */
@Component
public class SQLCreator {

    private static final String TAG_NAME = "tag_name";
    private static final String PART_OF_CERTIFICATE_NAME = "partName";
    private static final String PART_OF_CERTIFICATE_DESCRIPTION = "partDescription";
    private static final String SORT_BY_NAME = "sortByNameASC";
    private static final String SORT_BY_DATE = "sortByCreationDateASC";
    private static final String SORT_BY_NAME_DESC = "sortByNameDESC";
    private static final String SORT_BY_DATE_DESC = "sortByCreationDateDESC";
    private static final String WHERE = " WHERE ";
    private static final String AND = " AND ";
    private static final String EQUALS_QUOTE = "='";
    private static final String EQUALS = "=";
    private static final String LIKE_QUOTE_PERCENT = " LIKE '%";
    private static final String PERCENT_QUOTE = "%'";
    private static final String QUOTE = "'";
    private static final String ORDER_BY = " ORDER BY ";
    private static final String DESC = " DESC";
    private static final String COMA = ", ";


    /**
     * Method createGetCertificateQuery helps to create complex GET SQL query using the passed parameters
     * @param queryParts Map (key = constant parameter, value = passed parameter)
     * @return The SQL query as String
     */
    public String createGetCertificateQuery(Map<String, String> queryParts) {

        StringBuilder query = new StringBuilder(GET_GIFT_CERTIFICATES);

        StringBuilder joinPart = new StringBuilder();
        StringBuilder partSearchPart = new StringBuilder();
        StringBuilder orderPart = new StringBuilder();

        for (Map.Entry<String, String> mapWithQueryParts : queryParts.entrySet()) {
            String key = mapWithQueryParts.getKey();
            String value = mapWithQueryParts.getValue();
            if (key.equals(TAG_NAME)) {
                addJoinQuery(joinPart, value);
            }else if (key.equals(PART_OF_CERTIFICATE_NAME)) {
                addPartSearchParameter(partSearchPart,NAME,value);
            }else if (key.equals(PART_OF_CERTIFICATE_DESCRIPTION)) {
                addPartSearchParameter(partSearchPart,DESCRIPTION,value);
            }else if (key.equals(SORT_BY_NAME)){
                addSort(orderPart,NAME,false);
            }else if (key.equals(SORT_BY_DATE)){
                addSort(orderPart,CREATE_DATE,false);
            }else if (key.equals(SORT_BY_NAME_DESC)){
                addSort(orderPart,NAME,true);
            }else if (key.equals(SORT_BY_DATE_DESC)){
                addSort(orderPart,CREATE_DATE,true);
            }
        }
        StringBuilder checkQuery = query.append(joinPart);
        return getFinalQuery(checkQuery,checkForDoubleWhere(checkQuery,partSearchPart),orderPart);
    }

    private void addJoinQuery(StringBuilder joinPart, String tagName) {
        joinPart.append(JOIN_BY_TAG_ID).append(WHERE).append(TAG_NAME).append(EQUALS_QUOTE).append(tagName).append(QUOTE);
    }

    private void addPartSearchParameter(StringBuilder query, String parameter, String value) {
        if (query.toString().contains(WHERE)) {
            query.append(AND);
        } else {
            query.append(WHERE);
        }
        query.append(parameter).append(LIKE_QUOTE_PERCENT).append(value).append(PERCENT_QUOTE);
    }

    private void addSort(StringBuilder query, String value, boolean isDESC){
        if (query.toString().contains(ORDER_BY)) {
            query.append(COMA);
        } else {
            query.append(ORDER_BY);
        }
        query.append(value);
        if (isDESC){
            query.append(DESC);
        }
    }

    private StringBuilder checkForDoubleWhere(StringBuilder currentSB,StringBuilder destinationSB){
        if (currentSB.toString().contains(WHERE)){
           return new StringBuilder(destinationSB.toString().replace(WHERE,AND));
        }
        return destinationSB;
    }

    private String getFinalQuery(StringBuilder...sb){
        if (sb.length == 0){
            return "";
        }
        StringBuilder finalString = new StringBuilder();
        for (StringBuilder sbEntity:
             sb) {
            finalString.append(sbEntity);
        }
        return finalString.toString();
    }
}








