package com.epam.esm.jbdc.mapper;

import com.epam.esm.entity.Tag;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import static com.epam.esm.entity.TagTableColumns.*;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TagMapper implements RowMapper<Tag> {

    @Override
    public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {

        Tag tag = new Tag();
        tag.setId(rs.getLong(ID));
        tag.setName(rs.getString(NAME));
        return tag;
    }
}
