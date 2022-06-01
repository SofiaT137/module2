package com.epam.esm.pagination;

import com.epam.esm.exceptions.NoSuchEntityException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class Pagination<T> {

    public Page<T> checkHasContent(Page<T> page){
        if (!page.hasContent()){
            throw new NoSuchEntityException("No content!");
        }
        return page;
    }
}
