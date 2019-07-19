package com.hoang.linkredirector.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.hoang.linkredirector.model.Subscription;

@Component
public class SubscriptionRowMapper implements RowMapper<Subscription> {

    private static final String ID_COLUMN                = "id";
    private static final String NAME_COLUMN              = "name";
    private static final String HOSTNAME_COLUMN          = "hostname";
    private static final String USER_ID_COLUMN           = "user_id";
    private static final String SUBSCRIPTION_KEY_COLUMN  = "subscription_key";
    private static final String STATUS_COLUMN            = "status";
    private static final String BILLING_STATUS_COLUMN    = "billing_status";
    private static final String NEXT_BILLING_DATE_COLUMN = "next_billing_date";
    private static final String LAST_BILLING_DATE_COLUMN = "last_billing_date";
    private static final String TRIAL_END_DATE_COLUMN    = "trial_end_date";
    private static final String CREATED_AT_COLUMN        = "created_at";
    private static final String UPDATED_AT_COLUMN        = "updated_at";

    @Override
    public Subscription mapRow (ResultSet rs, int i) throws SQLException {
        Subscription subscription = new Subscription();
        subscription.setId(rs.getInt(ID_COLUMN));
        subscription.setName(rs.getString(NAME_COLUMN));
        subscription.setHostname(rs.getString(HOSTNAME_COLUMN));
        subscription.setUserId(rs.getInt(USER_ID_COLUMN));
        subscription.setSubscriptionKey(rs.getString(SUBSCRIPTION_KEY_COLUMN));
        subscription.setStatus(rs.getString(STATUS_COLUMN));
        subscription.setBillingStatus(rs.getString(BILLING_STATUS_COLUMN));
        subscription.setNextBillingDate(DateUtils.toZonedDateTime(rs, NEXT_BILLING_DATE_COLUMN));
        subscription.setLastBillingDate(DateUtils.toZonedDateTime(rs, LAST_BILLING_DATE_COLUMN));
        subscription.setTrialEndDate(DateUtils.toZonedDateTime(rs, TRIAL_END_DATE_COLUMN));
        subscription.setCreatedAt(DateUtils.toZonedDateTime(rs, CREATED_AT_COLUMN));
        subscription.setUpdatedAt(DateUtils.toZonedDateTime(rs, UPDATED_AT_COLUMN));

        return subscription;
    }
}
