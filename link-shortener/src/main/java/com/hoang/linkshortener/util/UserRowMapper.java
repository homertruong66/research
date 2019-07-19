package com.hoang.linkshortener.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.hoang.linkshortener.model.User;

@Component
public class UserRowMapper implements RowMapper<User> {

    private static final String ID_COLUMN         = "id";
    private static final String EMAIL_COLUMN      = "email";
    private static final String NAME_COLUMN       = "name";
    private static final String PASSWORD_COLUMN   = "password";
    private static final String LEVEL_COLUMN      = "level";
    private static final String CREATED_AT_COLUMN = "created_at";
    private static final String UPDATED_AT_COLUMN = "updated_at";

    @Override
    public User mapRow (ResultSet rs, int i) throws SQLException {
        User user = new User();
        user.setId(rs.getInt(ID_COLUMN));
        user.setEmail(rs.getString(EMAIL_COLUMN));
        user.setName(rs.getString(NAME_COLUMN));
        user.setPassword(rs.getString(PASSWORD_COLUMN));
        user.setLevel(rs.getInt(LEVEL_COLUMN));
        user.setCreatedAt(DateUtils.toZonedDateTime(rs, CREATED_AT_COLUMN));
        user.setUpdatedAt(DateUtils.toZonedDateTime(rs, UPDATED_AT_COLUMN));

        return user;
    }
}
