package com.epam.esm.service;

import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;

public interface TagService extends CRDService<Tag>{
    Tag getTagByName(String name) throws DaoException;
}
