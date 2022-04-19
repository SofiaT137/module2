package com.epam.esm.jbdc.mapper;

import com.epam.esm.entity.Tag;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import static com.epam.esm.entity.table_columns.TagTableColumns.*;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class TagMapper implements RowMapper.
 * This class helps JdbcTemplate for mapping rows of a ResultSet on a per-row basis.
 */
@Component
public class TagMapper implements RowMapper<Tag> {

    /**
     * The mapRow method maps each row of data in the ResultSet.
     * @param rs ResultSet rs
     * @param rowNum Integer row number
     * @return Tag entity
     */
    @Override
    public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
        Tag tag = new Tag();
        tag.setId(rs.getLong(ID));
        tag.setName(rs.getString(NAME));
        return tag;
    }
}
