package com.epam.esm.jbdc.impl.sql_creator;

import com.epam.esm.TagDao;
import com.epam.esm.exceptions.DaoException;
import com.epam.esm.jbdc.impl.TagDaoImpl;

import java.util.Map;
import static com.epam.esm.jbdc.sql_queries.GiftCertificateQueries.*;

public class SQLCreator {

    private static final String TAG_NAME = "tagName";
    private static final String PART_OF_CERTIFICATE_NAME = "partName";
    private static final String PART_OF_CERTIFICATE_DESCRIPTION = "partDescription";
    private static final String SORT_BY_NAME = "sortByName";
    private static final String SORT_BY_DATE = "sortByCreationDate";
    private static final String SORT_BY_NAME_DESC = "sortByNameDESC";
    private static final String SORT_BY_DATE_DESC = "sortByCreationDateDESC";


    public String createGetCertificateQuery(Map<String,String> queryParts){

        StringBuilder query = new StringBuilder(GET_GIFT_CERTIFICATES);

        for (Map.Entry<String, String> addToQuery : queryParts.entrySet()) {
            String key = addToQuery.getKey();
            String value = addToQuery.getValue();
            if(key.equals(TAG_NAME)) {
                 addJoinQuery(query, value);
            }else if (key.equals(PART_OF_CERTIFICATE_NAME)) {
                 addWhere(query, "name", value);
             }else if(key.equals(PART_OF_CERTIFICATE_DESCRIPTION)){
                addWhere(query,"description",value);
             }else if (key.equals(SORT_BY_NAME)){
                addSortASC(query,"name",value);
             }else if (key.equals(SORT_BY_DATE)){
                 addSortASC(query,"date",value);
             }else if (key.equals(SORT_BY_NAME_DESC)){
                addSortDESC(query,"name",value);
             }else if(key.equals(SORT_BY_DATE_DESC)) {
                addSortDESC(query,"date",value);
            }
        }
        return query.toString();
    }

    private void addJoinQuery(StringBuilder query, String tagName){
        TagDao tagDao = new TagDaoImpl();
        try {
            query.append(JOIN_BY_TAG_ID).append(tagDao.getTagByName(tagName).getId());
        } catch (DaoException exception) {
           exception.getErrorCode();
        }
    }

    private void addWhere(StringBuilder query, String parameter,String value){
        if (query.toString().contains("WHERE")) {
            query.append(" AND ");
        }else {
            query.append("WHERE");
        }
        query.append(parameter).append(" LIKE '%").append(value).append("%'");
    }

    private void addSortASC(StringBuilder query, String parameter,String value){
        if (query.toString().contains("ORDER BY")) {
            query.append(", ");
        } else {
            query.append(" ORDER BY ");
        }
        query.append(parameter).append(" ").append(value);
    }

    private void addSortDESC(StringBuilder query, String parameter,String value){
        if (query.toString().contains("ORDER BY")) {
            query.append(", ");
        } else {
            query.append(" ORDER BY ");
        }
        query.append(parameter).append(" ").append(value).append("DESC");
    }

}








