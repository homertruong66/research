package com.hoang.lsp.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class LastClickRowMapper implements RowMapper<String> {

    private final String fieldName;

    public LastClickRowMapper(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
        return rs.getString(this.fieldName);
    }

}
