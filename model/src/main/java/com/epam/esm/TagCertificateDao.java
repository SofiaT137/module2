package com.epam.esm;

import com.epam.esm.entity.Tag;

import java.util.List;

public interface TagCertificateDao extends CRDDao<Tag>{

    List<Tag> getTagByName(String name);
}
