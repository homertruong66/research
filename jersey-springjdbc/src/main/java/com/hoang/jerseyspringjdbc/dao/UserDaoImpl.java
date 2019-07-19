package com.hoang.jerseyspringjdbc.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.hoang.jerseyspringjdbc.model.Subscription;
import com.hoang.jerseyspringjdbc.util.SubscriptionRowMapper;
import com.hoang.jerseyspringjdbc.view_model.UserSearchCriteria;
import com.hoang.jerseyspringjdbc.model.User;
import com.hoang.jerseyspringjdbc.util.UserRowMapper;

@Repository
public class UserDaoImpl implements UserDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);

    @Autowired
    @Qualifier("modelSlaveTemplate")
    private JdbcTemplate modelSlaveTemplate;

    @Autowired
    @Qualifier("modelMasterTemplate")
    private JdbcTemplate modelMasterTemplate;

    @Autowired
    @Qualifier("modelMasterNamedParameterJdbcTemplate")
    private NamedParameterJdbcTemplate modelMasterNamedParameterJdbcTemplate;

    @Autowired
    private UserRowMapper userRowMapper;

    @Autowired
    private SubscriptionRowMapper subscriptionRowMapper;

    @Override
    public boolean checkMasterModelsDbConnection () {
        String sql = "SELECT 1 FROM DUAL";
        Integer result = modelMasterTemplate.queryForObject(sql, Integer.class);
        return result.equals(Integer.valueOf(1));
    }

    @Override
    public boolean checkSlaveModelsDbConnection () {
        String sql = "SELECT 1 FROM DUAL";
        Integer result = modelSlaveTemplate.queryForObject(sql, Integer.class);
        return result.equals(Integer.valueOf(1));
    }


    @Override
    public int create (User user) {
        String sql = "INSERT INTO users (" +
                     "email," +
                     "name," +
                     "password," +
                     "level," +
                     "status," +
                     "created_at," +
                     "updated_at ) " +
                     "VALUES (" +
                     ":email," +
                     ":name," +
                     ":password," +
                     ":level," +
                     ":status," +
                     "now()," +
                     "now())";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource namedParameters = new MapSqlParameterSource("email", user.getEmail())
            .addValue("name", user.getName())
            .addValue("password", user.getPassword())
            .addValue("level", user.getLevel())
            .addValue("status", user.getStatus());
        modelMasterNamedParameterJdbcTemplate.update(sql, namedParameters, keyHolder);

        return keyHolder.getKey().intValue();
    }

    @Override
    public int createSubscription (int id, Subscription subscription) {
        String sql = "INSERT INTO subscriptions (" +
                     "  name," +
                     "  hostname," +
                     "  user_id," +
                     "  subscription_key," +
                     "  status," +
                     "  created_at," +
                     "  updated_at ) " +
                     "VALUES (" +
                     "  :name," +
                     "  :hostname," +
                     "  :user_id," +
                     "  :subscription_key," +
                     "  :status," +
                     "  now()," +
                     "  now())";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource namedParameters = new MapSqlParameterSource("user_id", id)
            .addValue("name", subscription.getName())
            .addValue("hostname", subscription.getHostname())
            .addValue("subscription_key", UUID.randomUUID().toString())
            .addValue("status", "active");

        modelMasterNamedParameterJdbcTemplate.update(sql, namedParameters, keyHolder);

        return keyHolder.getKey().intValue();
    }

    @Override
    public User read (int id) {
        User user;
        try {
            String sql = "SELECT id, email, name, password, level, created_at, updated_at " +
                         "FROM users " +
                         "WHERE id = ? ";
            user = modelSlaveTemplate.queryForObject(
                sql,
                new Object[] { id },
                userRowMapper
            );
        }
        catch (EmptyResultDataAccessException e) {
            user = null;
        }

        return user;
    }

    @Override
    public Subscription readSubscription (int subscriptionId) {
        Subscription subscription;

        try {
            String sql = "SELECT id, name, hostname, user_id, subscription_key, status, " +
                         "  billing_status, next_billing_date, last_billing_date, trial_end_date, " +
                         "  created_at, updated_at " +
                         "FROM subscriptions " +
                         "WHERE id = ? ";
            subscription = modelSlaveTemplate.queryForObject(sql, new Object[] { subscriptionId }, subscriptionRowMapper);
        }
        catch (EmptyResultDataAccessException e) {
            subscription = null;
        }

        return subscription;
    }

    @Override
    public boolean isUserEmailExistent (String email) {
        boolean result;
        try {
            String sql = "SELECT id " +
                         "FROM users " +
                         "WHERE email = ? ";
            modelSlaveTemplate.queryForObject(
                sql,
                new Object[] { email },
                Integer.class
            );

            result = true;
        }
        catch (EmptyResultDataAccessException e) {
            result = false;
        }

        return result;
    }

    @Override
    public boolean isSubscriptionNameExistent (String name) {
        boolean result;
        try {
            String sql = "SELECT id " +
                         "FROM subscriptions " +
                         "WHERE name = ? ";
            modelSlaveTemplate.queryForObject(
                sql,
                new Object[] { name },
                Integer.class
            );

            result = true;
        }
        catch (EmptyResultDataAccessException e) {
            result = false;
        }

        return result;
    }

    @Override
    public List<User> search (UserSearchCriteria userSearchCriteria) {
        List<User> users;
        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource();
            String whereClause = "";
            if (!StringUtils.isEmpty(userSearchCriteria.getName())) {
                whereClause += "WHERE name like :name ";
                namedParameters.addValue("name", "%" + userSearchCriteria.getName() + "%");
            }

            String sql = "SELECT id, email, name, password, level, status, created_at, updated_at " +
                         "FROM users " +
                         whereClause +
                         "ORDER BY :sortName :sortDirection " +
                         "LIMIT :limit OFFSET :offset";

            namedParameters.addValue("sortName", userSearchCriteria.getSortName());
            namedParameters.addValue("sortDirection", userSearchCriteria.getSortDirection());
            namedParameters.addValue("limit", userSearchCriteria.getPageSize());
            namedParameters.addValue("offset", userSearchCriteria.getOffset());

            users = modelMasterNamedParameterJdbcTemplate.query(sql, namedParameters, userRowMapper);
        }
        catch (EmptyResultDataAccessException e) {
            users = new ArrayList<>();
        }

        return users;
    }
}
