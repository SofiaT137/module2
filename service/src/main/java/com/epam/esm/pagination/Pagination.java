package com.epam.esm.pagination;

import com.epam.esm.exceptions.NoSuchEntityException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

/**
 * Pagination is the utility class that assists in adding verification if its content is not for pagination
 * @param <T>
 */
@Component
public class Pagination<T> {

    private static final String NO_CONTENT = "No content!";

    /**
     * Method checkHasContent checks if it is no content for display on the page
     * @param page Page page
     * @return Page with content
     */
    public Page<T> checkHasContent(Page<T> page){
        if (!page.hasContent()){
            throw new NoSuchEntityException(NO_CONTENT);
        }
        return page;
    }
}
