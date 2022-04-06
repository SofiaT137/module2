package com.epam.esm;

import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;

import java.util.List;

public interface TagDao extends CRDDao<Tag>{

    Tag getTagByName(String name) throws DaoException;
}
