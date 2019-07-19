package com.hoang.lsp.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class IncrementFieldRowMapper implements RowMapper<Integer> {

    private final String fieldName;

    public IncrementFieldRowMapper(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
        return rs.getInt(this.fieldName);
    }

}
