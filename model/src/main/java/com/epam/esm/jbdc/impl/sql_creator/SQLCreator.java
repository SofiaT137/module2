package com.epam.esm.jbdc.impl.sql_creator;

import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Map;
import java.util.Set;

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
     *
     * @param queryParts Map (key = constant parameter, value = passed parameter)
     * @return The SQL query as String
     */
    public String createGetCertificateQuery(Map<String, String> queryParts) {
        return GET_GIFT_CERTIFICATES + mapCheckTransferredKeys(queryParts);
    }

    private String addJoinPart(String tagName) {
        return JOIN_BY_TAG_ID + WHERE + TAG_NAME + EQUALS_QUOTE + tagName + QUOTE;
    }

    private String addPartSearchPart(String query, String parameter, String value) {
        query = query.contains(WHERE) ? query + AND : query + WHERE;
        return query + parameter + LIKE_QUOTE_PERCENT + value + PERCENT_QUOTE;
    }

    private String addSortPart(String query, String value, boolean isDESC) {
        query = query.contains(ORDER_BY) ? query + COMA : query + ORDER_BY;
        query = query + value;
        return isDESC ? query + DESC : query;
    }

    private String checkForDoubleWhere(String currentString) {
        int index = currentString.lastIndexOf(WHERE);
        if (index != 0){
            String firstPartOfQueryPart = currentString.subSequence(0,index).toString();
            String secondPartOfTheQueryPart = currentString.subSequence(index,currentString.length()).toString().replace(WHERE,AND);
          return  firstPartOfQueryPart + secondPartOfTheQueryPart ;
        }
        return currentString;
    }

    private String mapCheckTransferredKeys(Map<String, String> queryParts) {

        String joinPart = "";
        String partSearchPart = "";
        String orderPart = "";

        for (Map.Entry<String, String> stringStringEntry : queryParts.entrySet()) {

            String key = stringStringEntry.getKey();
            String value = stringStringEntry.getValue();

            switch (key) {
                case TAG_NAME:
                    joinPart = addJoinPart(value);
                    break;
                case PART_OF_CERTIFICATE_NAME:
                    partSearchPart = addPartSearchPart(partSearchPart, NAME, value);
                    break;
                case PART_OF_CERTIFICATE_DESCRIPTION:
                    partSearchPart = addPartSearchPart(partSearchPart, DESCRIPTION, value);
                    break;
                case SORT_BY_NAME:
                    orderPart = addSortPart(orderPart, NAME, false);
                    break;
                case SORT_BY_DATE:
                    orderPart = addSortPart(orderPart, CREATE_DATE, false);
                    break;
                case SORT_BY_NAME_DESC:
                    orderPart = addSortPart(orderPart, NAME, true);
                    break;
                case SORT_BY_DATE_DESC:
                    orderPart = addSortPart(orderPart, CREATE_DATE, true);
                    break;
            }
        }
        return checkForDoubleWhere(joinPart + partSearchPart) + orderPart;
    }
}








