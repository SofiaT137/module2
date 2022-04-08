package com.epam.esm.jbdc;

import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;

public interface TagDao extends CRDDao<Tag> {

    Tag getTagByName(String name) throws DaoException;
}
