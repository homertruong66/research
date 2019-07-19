package com.rms.rms.persistence;

import com.rms.rms.common.constant.SystemRole;
import com.rms.rms.common.util.MyDateUtil;
import com.rms.rms.service.model.*;
import io.jsonwebtoken.lang.Collections;
import org.apache.log4j.Logger;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * homertruong
 */

@Component
public class SpecificDaoImpl implements SpecificDao {

    private Logger logger = Logger.getLogger(SpecificDaoImpl.class);

    private static final String CONDITION_TEMPLATE = "IF ( %s < %s , %s, %s )";

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Long countAffiliatesByReferrer(String referrer) {
        logger.debug("countAffiliatesByReferrer: " + referrer);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT count(a.affiliateId) FROM Agent a");
        sb.append(" WHERE a.referrer = (:referrer)");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("referrer", referrer);
        Long result = (Long) query.uniqueResult();

        return result != null? result : Long.valueOf(0);
    }

    @Override
    public Long countAffiliatesBySubscriberId(String subscriberId) {
        logger.debug("countAffiliatesBySubscriberId: " + subscriberId);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT count(a.affiliateId) FROM Agent a");
        sb.append(" WHERE a.subscriberId = (:subscriberId)");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("subscriberId", subscriberId);
        Long result = (Long) query.uniqueResult();

        return result != null? result : Long.valueOf(0);
    }

    @Override
    public Long countCustomersByAffiliateId(String affiliateId) {
        logger.debug("countCustomersByAffiliateId: " + affiliateId);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT count(distinct o.customerId) FROM Order o");
        sb.append(" WHERE o.affiliateId = (:affiliateId)");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("affiliateId", affiliateId);
        Long result = (Long) query.uniqueResult();

        return result != null? result : Long.valueOf(0);
    }

    @Override
    public Long countCustomersByChannelId(String channelId) {
        logger.debug("countCustomersByChannelId: " + channelId);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT count(distinct o.customerId) FROM Order o");
        sb.append(" WHERE o.channelId = (:channelId)");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("channelId", channelId);
        Long result = (Long) query.uniqueResult();

        return result != null? result : Long.valueOf(0);
    }

    @Override
    public Long countCustomersBySubscriberId(String subscriberId) {
        logger.debug("countCustomersBySubscriberId: " + subscriberId);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT count(c.id) FROM Customer c");
        sb.append(" WHERE c.subscriberId = (:subscriberId)");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("subscriberId", subscriberId);
        Long result = (Long) query.uniqueResult();

        return result != null? result : Long.valueOf(0);
    }

    @Override
    public Long countOrdersByAffiliateId(String affiliateId) {
        logger.debug("countOrdersByAffiliateId: " + affiliateId);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT count(o.id) FROM Order o");
        sb.append(" WHERE o.affiliateId = (:affiliateId)");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("affiliateId", affiliateId);
        Long result = (Long) query.uniqueResult();

        return result != null? result : Long.valueOf(0);
    }

    @Override
    public Long countOrdersByChannelId(String channelId) {
        logger.debug("countOrdersByChannelId: " + channelId);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT count(o.id) FROM Order o");
        sb.append(" WHERE o.channelId = (:channelId)");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("channelId", channelId);
        Long result = (Long) query.uniqueResult();

        return result != null? result : Long.valueOf(0);
    }

    @Override
    public Long countOrdersBySubscriberId(String subscriberId) {
        logger.debug("countOrdersBySubscriberId: " + subscriberId);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT count(o.id) FROM Order o");
        sb.append(" WHERE o.channel.subscriberId = (:subscriberId)");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("subscriberId", subscriberId);
        Long result = (Long) query.uniqueResult();

        return result != null? result : Long.valueOf(0);
    }

    @Override
    public Long countShareClicksByChannelId(String channelId) {
        logger.debug("countShareClicksByChannelId: " + channelId);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT sum(s.clickCount) FROM Share s");
        sb.append(" WHERE s.channelId = :channelId");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("channelId", channelId);
        Long result = (Long) query.uniqueResult();

        return result != null? result : Long.valueOf(0);
    }

    @Override
    public Long countSubsAdminsBySubscriberId(String subscriberId) {
        logger.debug("countSubsAdminsBySubscriberId: " + subscriberId);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT count(sa.id) FROM SubsAdmin sa");
        sb.append(" WHERE sa.subscriberId = (:subscriberId)");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("subscriberId", subscriberId);
        Long result = (Long) query.uniqueResult();

        return result != null? result : Long.valueOf(0);
    }

    @Override
    public void deleteToken(String userId) {
        logger.debug("deleteToken: " + userId);

        String sql = "UPDATE users " +
                "SET token = NULL " +
                "WHERE id = :id";

        logger.debug(sql + " --- params: " + userId);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", userId);
        namedParameterJdbcTemplate.update(sql, namedParameters, keyHolder);
        // keyHolder.getKey().intValue(); -> get PK in case of insertion
    }

    @Override
    public List<String> getAffiliateIdsByNotStatus(String notStatus) {
        logger.debug("getAffiliateIdsByNotStatus: " + notStatus);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT u.id FROM User u");
        sb.append(" WHERE u.status != (:notStatus)");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("notStatus", notStatus);
        List<String> result = query.list();

        return result;
    }

    @Override
    public List<String> getAffiliateIdsBySubscriberId(String subscriberId) {
        logger.debug("getAffiliateIdsBySubscriberId: " + subscriberId);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT a.affiliateId FROM Agent a");
        sb.append(" WHERE a.subscriberId = (:subscriberId)");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("subscriberId", subscriberId);
        List<String> result = query.list();

        return result;
    }

    @Override
    public List<String> getAffiliateIdsCreatedByAffiliateId(String affiliateId) {
        logger.debug("getAffiliateIdsCreatedByAffiliateId: " + affiliateId);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT a.affiliateId FROM Agent a");
        sb.append(" WHERE a.createdBy = (:affiliateId)");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("affiliateId", affiliateId);
        List<String> result = query.list();

        return result;
    }

    @Override
    public List<String> getAffiliateIdsBySubscriberIdAndReferrer(String subscriberId, String referrer) {
        logger.debug("getAffiliateIdsBySubscriberIdAndReferrer: " + subscriberId + " - referrer: " + referrer);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT a.affiliateId FROM Agent a");
        sb.append(" WHERE a.subscriberId = (:subscriberId) ");
        if (referrer != null) {
            sb.append(" AND a.referrer = (:referrer) ");
        }
        else {
            sb.append(" AND a.referrer IS NULL ");
        }
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("subscriberId", subscriberId);
        if (referrer != null) {
            query.setParameter("referrer", referrer);
        }
        List<String> result = query.list();

        return result;
    }

    @Override
    public List<String> getAffiliateNicknamesByAffiliateIds(List<String> affiliateIds) {
        logger.debug("getAffiliateNicknamesByAffiliateIds: " + affiliateIds);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT a.nickname FROM Affiliate a");
        sb.append(" WHERE a.id IN (:affiliateIds)");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameterList("affiliateIds", affiliateIds);
        List<String> result = query.list();

        return result;
    }

    @Override
    public List<Affiliate> getAffiliatesBySubscriberAndReferrer(String subscriberId, String referrer) {
        logger.debug("getAffiliatesBySubscriberAndReferrer: " + subscriberId + " - referrer: " + referrer);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT a.affiliate FROM Agent a");
        sb.append(" WHERE a.subscriberId = (:subscriberId) ");
        if (referrer != null) {
            sb.append(" AND a.referrer = (:referrer) ");
        }
        else {
            sb.append(" AND a.referrer IS NULL ");
        }
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("subscriberId", subscriberId);
        if (referrer != null) {
            query.setParameter("referrer", referrer);
        }
        List<Affiliate> result = query.list();

        return result;
    }

    @Override
    public List<String> getChannelIdsBySubscriberId(String subscriberId) {
        logger.debug("getChannelIdsBySubscriberId: " + subscriberId);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT c.id FROM Channel c");
        sb.append(" WHERE c.subscriberId = (:subscriberId)");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("subscriberId", subscriberId);
        List<String> result = query.list();

        return result;
    }

    @Override
    public List<String> getCustomerIdsByAffiliateId(String affiliateId) {
        logger.debug("getCustomerIdsByAffiliateId: " + affiliateId);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT o.customerId FROM Order o");
        sb.append(" WHERE o.affiliateId = (:affiliateId)");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("affiliateId", affiliateId);
        List<String> result = query.list();

        return result;
    }

    @Override
    public List<String> getCustomerIdsBySubscriberId(String subscriberId) {
        logger.debug("getCustomerIdsBySubscriberId: " + subscriberId);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT c.id FROM Customer c ");
        sb.append("WHERE c.subscriberId = (:subscriberId) ");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("subscriberId", subscriberId);
        List<String> result = query.list();

        return result;
    }

    @Override
    public List<Customer> getCustomersByAffiliateId(Set<String> customerIds, String affiliateId) {
        logger.debug("getCustomersByAffiliateId: " + customerIds + " - affiliateId: " + affiliateId);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT DISTINCT o.customer FROM Order o");
        sb.append(" WHERE o.affiliateId = (:affiliateId)");
        sb.append(" AND o.customerId IN (:customerIds)");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("affiliateId", affiliateId);
        query.setParameterList("customerIds", customerIds);
        List<Customer> result = query.list();

        return result;
    }

    @Override
    public Double getCommissionEarningByAffiliateIdAndOrderId(String affiliateId, String orderId) {
        logger.debug("getCommissionEarningByAffiliateIdAndOrderId: " + affiliateId + ", " + orderId);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT sum(c.earning) FROM Commission c");
        sb.append(" WHERE c.affiliateId = (:affiliateId)");
        sb.append(" AND c.orderId = (:orderId)");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("affiliateId", affiliateId);
        query.setParameter("orderId", orderId);
        Double result = (Double) query.uniqueResult();

        return result != null ? result : Double.valueOf(0);
    }

    @Override
    public List<Commission> getCommissionsByProductId(String productId, Date from, Date to) {
        logger.debug("getCommissionsByProductId: " + productId + ", from: " + from + ", to: " + to);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT comm FROM Commission comm ");
        sb.append("WHERE comm.orderId IN ( ");
        sb.append("    SELECT orl.orderId " +
                  "    FROM OrderLine orl " +
                  "    WHERE orl.productId = (:productId) ");
        sb.append(" ) ");
        if( from != null ) {
            sb.append(" AND createdAt >= (:from) ");
        }
        if( to != null ) {
            sb.append(" AND createdAt <= (:to) ");
        }

        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("productId", productId);
        if( from != null ) {
            query.setParameter("from", from);
        }
        if( to != null ) {
            query.setParameter("to", to);
        }

        return query.list();
    }

    @Override
    public List<String> getLinkIdsBySubscriberId(String subscriberId) {
        logger.debug("getLinkIdsBySubscriberId: " + subscriberId);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s.linkId FROM Share s ");
        sb.append("WHERE s.channel.subscriberId = (:subscriberId) ");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("subscriberId", subscriberId);
        List<String> result = query.list();

        return result;
    }

    @Override
    public List<String> getOrderIdsByAffiliateId(String affiliateId) {
        logger.debug("getOrderIdsByAffiliateId: " + affiliateId);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT o.id FROM Order o");
        sb.append(" WHERE o.affiliateId = (:affiliateId)");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("affiliateId", affiliateId);
        List<String> result = query.list();

        return result;
    }

    @Override
    public List<String> getOrderIdsByProductId(String productId) {
        logger.debug("getOrderIdsByProductId: " + productId);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT orl.orderId FROM OrderLine orl");
        sb.append(" WHERE orl.productId = (:productId)");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("productId", productId);
        List<String> result = query.list();

        return result;
    }

    @Override
    public List<String> getOrderIdsBySubscriberId(String subscriberId) {
        logger.debug("getOrderIdsBySubscriberId: " + subscriberId);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT o.id FROM Order o, Channel c");
        sb.append(" WHERE c.subscriberId = (:subscriberId)");
        sb.append(" AND o.channelId = c.id");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("subscriberId", subscriberId);
        List<String> result = query.list();

        return result;
    }

    @Override
    public List<String> getOrderIdsByProductName(String productName) {
        logger.debug("getOrderIdsByProductName: " + productName);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ol.orderId FROM OrderLine ol");
        sb.append(" WHERE ol.product.name LIKE :productName");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("productName", productName);
        List<String> result = query.list();

        return result;
    }

    @Override
    public List<String> getOrderIdsByProductNameAndSubscriberId(String productName, String subscriberId) {
        logger.debug("getOrderIdsByProductNameAndSubscriberId: " + productName + ", " + subscriberId);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ol.orderId FROM OrderLine ol");
        sb.append(" WHERE ol.product.name LIKE :productName");
        sb.append(" AND ol.product.channel.subscriberId = :subscriberId");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("productName", productName);
        query.setParameter("subscriberId", subscriberId);
        List<String> result = query.list();

        return result;
    }

    @Override
    public Double getOrderRevenue(String status) {
        logger.debug("getOrderRevenue:");

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT sum(o.total) FROM Order o");
        if (status != null) {
            sb.append(" WHERE o.status = (:status)");
        }
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);;
        if (status != null) {
            query.setParameter("status", status);
        }
        Double result = (Double) query.uniqueResult();

        return result != null? result : Double.valueOf(0);
    }

    @Override
    public Double getOrderRevenueByAffiliateId(String affiliateId, String status) {
        logger.debug("getOrderRevenueByAffiliateId: " + affiliateId);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT sum(o.total) FROM Order o");
        sb.append(" WHERE o.affiliateId = (:affiliateId)");
        if (status != null) {
            sb.append(" AND o.status = (:status)");
        }
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("affiliateId", affiliateId);
        if (status != null) {
            query.setParameter("status", status);
        }
        Double result = (Double) query.uniqueResult();

        return result != null? result : Double.valueOf(0);
    }

    @Override
    public Double getOrderRevenueByAffiliateIdAndSubscriberId(String affiliateId, String subscriberId, String status) {
        logger.info("getOrderRevenueByAffiliateIdAndSubscriberId: " + affiliateId + ", subscriberId: " + subscriberId + ", status: " + status);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT sum(o.total) FROM Order o");
        sb.append(" WHERE o.channel.subscriberId = (:subscriberId)");
        sb.append(" AND o.affiliateId = (:affiliateId)");
        if (status != null) {
            sb.append(" AND o.status = (:status)");
        }
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("subscriberId", subscriberId);
        query.setParameter("affiliateId", affiliateId);
        if (status != null) {
            query.setParameter("status", status);
        }
        Double result = (Double) query.uniqueResult();

        return result != null ? result : Double.valueOf(0);
    }

    @Override
    public Double getOrderRevenueByAffiliateIdAndSubscriberId(String affiliateId, String subscriberId, Date from, Date to) {
        logger.info("getOrderRevenueByAffiliateIdAndSubscriberId: " + affiliateId + ", subscriberId: " + subscriberId +
                ", from: " + from + ", to: " + to);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT sum(o.total) FROM Order o");
        sb.append(" WHERE o.channel.subscriberId = (:subscriberId)");
        sb.append(" AND o.affiliateId = (:affiliateId)");
        sb.append(" AND o.createdAt >= (:from)");
        sb.append(" AND o.createdAt <= (:to)");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("subscriberId", subscriberId);
        query.setParameter("affiliateId", affiliateId);
        query.setParameter("from", from);
        query.setParameter("to", to);
        Double result = (Double) query.uniqueResult();

        return result != null ? result : Double.valueOf(0);
    }

    @Override
    public Double getOrderRevenueByAffiliateIdsAndSubscriberId(List<String> affiliateIds, String subscriberId, String status) {
        logger.info("getOrderRevenueByAffiliateIdsAndSubscriberId: " + affiliateIds + ", subscriberId: " + subscriberId + ", status: " + status);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT sum(o.total) FROM Order o");
        sb.append(" WHERE o.channel.subscriberId = (:subscriberId)");
        sb.append(" AND o.affiliateId IN (:affiliateIds)");
        if (status != null) {
            sb.append(" AND o.status = (:status)");
        }
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("subscriberId", subscriberId);
        query.setParameterList("affiliateIds", affiliateIds);
        if (status != null) {
            query.setParameter("status", status);
        }
        Double result = (Double) query.uniqueResult();

        return result != null ? result : Double.valueOf(0);
    }

    @Override
    public Double getOrderRevenueByChannelId(String channelId, String status) {
        logger.debug("getOrderRevenueByChannelId: " + channelId);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT sum(o.total) FROM Order o");
        sb.append(" WHERE o.channelId = (:channelId)");
        if (status != null) {
            sb.append(" AND o.status = (:status)");
        }
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("channelId", channelId);
        if (status != null) {
            query.setParameter("status", status);
        }
        Double result = (Double) query.uniqueResult();

        return result != null? result : Double.valueOf(0);
    }

    @Override
    public Double getOrderRevenueBySubscriberId(String subscriberId, String status) {
        logger.debug("getOrderRevenueBySubscriberId: " + subscriberId);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT sum(o.total) FROM Order o");
        sb.append(" WHERE o.channel.subscriberId = (:subscriberId)");
        if (status != null) {
            sb.append(" AND o.status = (:status)");
        }
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("subscriberId", subscriberId);
        if (status != null) {
            query.setParameter("status", status);
        }
        Double result = (Double) query.uniqueResult();

        return result != null? result : Double.valueOf(0);
    }

    @Override
    public List<String> getProductIdsBySubscriberId(String subscriberId) {
        logger.debug("getProductIdsBySubscriberId: " + subscriberId);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT p.id FROM Product p");
        sb.append(" WHERE p.channel.subscriberId = :subscriberId");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("subscriberId", subscriberId);
        List<String> result = query.list();

        return result;
    }

    @Override
    public List<Map<Date, Integer>> getShareClicksByDate() {
        logger.debug("getShareClicksByDate: ");

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT new map(s.statsDate, sum(s.clickCount)) FROM Share s");
        sb.append(" GROUP BY statsDate");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        List<Map<Date, Integer>> result = query.list();

        return result;
    }

    @Override
    public List<Map<Date, Integer>> getShareClicksByDateByAffiliateId(String affiliateId) {
        logger.debug("getShareClicksByDateByAffiliateId: " + affiliateId);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT new map(s.statsDate, sum(s.clickCount)) FROM Share s");
        sb.append(" WHERE s.affiliateId = :affiliateId");
        sb.append(" GROUP BY statsDate");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("affiliateId", affiliateId);
        List<Map<Date, Integer>> result = query.list();

        return result;
    }

    @Override
    public List<Map<Date, Integer>> getShareClicksByDateByAffiliateIdAndSubscriberId(String affiliateId, String subscriberId) {
        logger.info("getShareClicksByDateByAffiliateIdAndSubscriberId: " + affiliateId + ", subscriberId: " + subscriberId);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT new map(s.statsDate, sum(s.clickCount)) FROM Share s");
        sb.append(" WHERE s.affiliateId = :affiliateId");
        sb.append(" AND s.channel.subscriberId = :subscriberId");
        sb.append(" GROUP BY statsDate");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("affiliateId", affiliateId);
        query.setParameter("subscriberId", subscriberId);
        List<Map<Date, Integer>> result = query.list();

        return result;
    }

    @Override
    public List<Map<Date, Integer>> getShareClicksByDateByChannelId(String channelId) {
        logger.debug("getShareClicksByDateByChannelId: " + channelId);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT new map(s.statsDate, sum(s.clickCount)) FROM Share s");
        sb.append(" WHERE s.channelId = :channelId");
        sb.append(" GROUP BY s.statsDate");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("channelId", channelId);
        List<Map<Date, Integer>> result = query.list();

        return result;
    }

    @Override
    public List<Map<Date, Integer>> getShareClicksByDateBySubscriberId(String subscriberId) {
        logger.debug("getShareClicksByDateBySubscriberId: " + subscriberId);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT new map(s.statsDate, sum(s.clickCount)) FROM Share s");
        sb.append(" WHERE s.channel.subscriberId = :subscriberId");
        sb.append(" GROUP BY s.statsDate");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("subscriberId", subscriberId);
        List<Map<Date, Integer>> result = query.list();

        return result;
    }

    @Override
    public List<String> getSubsAdminIdsBySubscriberId(String subscriberId) {
        logger.debug("getSubsAdminIdsBySubscriberId: " + subscriberId);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT sa.id FROM SubsAdmin sa");
        sb.append(" WHERE sa.subscriberId = (:subscriberId)");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("subscriberId", subscriberId);
        List<String> result = query.list();

        return result;
    }

    @Override
    public List<String> getSubsAdminAccountantIdsBySubscriberId(String subscriberId) {
        logger.debug("getSubsAdminAccountantIdsBySubscriberId: " + subscriberId);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT sa.id FROM SubsAdmin sa");
        sb.append(" JOIN sa.user.roles r");
        sb.append(" WHERE r.name = '"+ SystemRole.ROLE_ACCOUNTANT +"'");
        sb.append(" AND sa.subscriberId = :subscriberId");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("subscriberId", subscriberId);
        List<String> result = query.list();

        return result;
    }

    @Override
    public List<String> getSubsAdminEmailsBySubscriberId(String subscriberId) {
        logger.debug("getSubsAdminEmailsBySubscriberId: " + subscriberId);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT u.email FROM User u");
        sb.append(" WHERE u.id IN ( ");
        sb.append("  SELECT id FROM SubsAdmin WHERE subscriberId = :subscriberId ) ");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("subscriberId", subscriberId);
        List<String> result = query.list();

        return result;
    }

    @Override
    public List<SubsAlertConfig> getSubsAlertConfigsByAffiliateId(String affiliateId) {
        logger.debug("getSubsAlertConfigsByAffiliateId: " + affiliateId);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT sac FROM SubsAlertConfig sac ");
        sb.append("WHERE sac.subscriber.id IN ( ");
        sb.append("  SELECT a.subscriberId FROM Agent a WHERE a.affiliateId = :affiliateId) ");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("affiliateId", affiliateId);
        List<SubsAlertConfig> result = query.list();

        return result;
    }

    @Override
    public List<String> getProductIdsByChannelIds(List<String> channelIds) {
        logger.debug("getProductIdsByChannelIds: " + channelIds);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT pr.id FROM Product pr");
        sb.append(" WHERE pr.channelId IN (:channelId)");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameterList("channelId", channelIds);
        List<String> result = query.list();

        return result;
    }

    @Override
    public List<String> getSubscriberIds() {
        logger.info("getSubscriberIds");

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT DISTINCT a.subscriberId FROM Agent a");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        List<String> result = query.list();

        return result;
    }

    @Override
    public List<String> getSubscriberIdsByAffiliateId(String affiliateId) {
        logger.debug("getSubscriberIdsByAffiliateId: " + affiliateId);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT a.subscriberId FROM Agent a");
        sb.append(" WHERE a.affiliateId = (:affiliateId)");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("affiliateId", affiliateId);
        List<String> result = query.list();

        return result;
    }

    @Override
    public List<String> getSubsCommissionConfigIdsBySubscriberId(String subscriberId) {
        logger.debug("getSubsCommissionConfigIdsBySubscriberId: " + subscriberId);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT scg.id FROM SubsCommissionConfig scg");
        sb.append(" WHERE scg.subscriberId = (:subscriberId)");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("subscriberId", subscriberId);
        List<String> result = query.list();

        return result;
    }

    @Override
    public List<String> getUnbilledSubscriberIds(Date startDate, Date endDate) {
        logger.debug("getUnbilledSubscriberIds: " + startDate + "endDate" + endDate);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s.id FROM Subscriber s");
        sb.append(" WHERE s.id NOT IN");
        sb.append(" (SELECT b.subscriberId FROM Bill b WHERE b.deadline >= (:startDate) AND b.deadline <= (:endDate))");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        List<String> result = query.list();

        return result;
    }

    @Override
    public List<String> getUserIdsByEmail(String email) {
        logger.debug("getUserIdsByEmail: " + email);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT u.id FROM User u");
        sb.append(" WHERE u.email like (:email)");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("email", "%" + email + "%");
        List<String> result = query.list();

        return result;
    }

    @Override
    public List<String> getUserIdsByEmailAndStatus(String email, String status) {
        logger.debug("getUserIdsByEmailAndStatus: " + email + " - status: " + status);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT u.id FROM User u");
        sb.append(" WHERE u.email like (:email)");
        sb.append(" AND u.status = :status");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("email", "%" + email + "%");
        query.setParameter("status", status);
        List<String> result = query.list();

        return result;
    }

    @Override
    public List<String> getUserIdsByStatus(String status) {
        logger.debug("getUserIdsByStatus: " + status);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT u.id FROM User u");
        sb.append(" WHERE u.status = :status");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("status", status);
        List<String> result = query.list();

        return result;
    }

    @Override
    public List<String> getVoucherIdsByAffiliateId(String affiliateId) {
        logger.debug("getVoucherIdsByAffiliateId: " + affiliateId);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT av.voucherId FROM AffiliateVoucher av");
        sb.append(" WHERE av.affiliateId = (:affiliateId)");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("affiliateId", affiliateId);
        List<String> result = query.list();

        return result;
    }

    @Override
    public List<Voucher> getVouchersByAffiliateId(Set<String> voucherIds, String affiliateId) {
        logger.debug("getVouchersByAffiliateId: " + voucherIds + " - affiliateId: " + affiliateId);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT DISTINCT av.voucher FROM AffiliateVoucher av");
        sb.append(" WHERE av.affiliateId = (:affiliateId)");
        sb.append(" AND av.voucherId IN (:voucherIds)");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("affiliateId", affiliateId);
        query.setParameterList("voucherIds", voucherIds);
        List<Voucher> result = query.list();

        return result;
    }

    @Override
    public String healthCheck() {
        String sql = "Select version()";
        String result = jdbcTemplate.queryForObject(sql, String.class);
        return result;
    }

    @Override
    public int insertToken(String userId, String token) {
        logger.debug("insertToken:" + userId + " - " + token);

        String sql = "UPDATE users " +
                "SET token = :token, updated_at = :updated_at " +
                "WHERE id = :id";

        logger.debug(sql + " --- params: " + userId);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource namedParameters =
                new MapSqlParameterSource().addValue("token", token)
                                           .addValue("id", userId)
                                           .addValue("updated_at", MyDateUtil.convertToUTCDate(new Date()));
        int numberOfRowsAffected = namedParameterJdbcTemplate.update(sql, namedParameters, keyHolder);

        return numberOfRowsAffected;
    }

    @Override
    public int increaseAffiliateNumberOfAnAffiliateInASubscriber(String affiliateId, String subscriberId) {
        logger.debug("increaseAffiliateNumberOfAnAffiliateInASubscriber: " + affiliateId + ", subscriberId: " + subscriberId);

        String sql =
                "UPDATE agents " +
                        "SET number_of_affiliates_in_network = IF(number_of_affiliates_in_network IS NULL, 1, number_of_affiliates_in_network + 1 ), " +
                        "    updated_at = :updated_at " +
                        "WHERE affiliate_id = :affiliate_id " +
                        "AND subscriber_id = :subscriber_id";

        logger.debug(sql + " --- params: " + affiliateId + ", " + subscriberId);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource namedParameters =
                new MapSqlParameterSource()
                        .addValue("affiliate_id", affiliateId)
                        .addValue("subscriber_id", subscriberId)
                        .addValue("updated_at", MyDateUtil.convertToUTCDate(new Date()));
        int numberOfRowsAffected = namedParameterJdbcTemplate.update(sql, namedParameters, keyHolder);

        return numberOfRowsAffected;
    }

    @Override
    public int increaseNumberOfAffiliatesOfSubscriber(String subscriberId) {
        logger.debug("increaseNumberOfAffiliatesOfSubscriber: " + subscriberId);

        String sql =
            "UPDATE subscribers " +
            "SET number_of_affiliates = IF(number_of_affiliates IS NULL, 1, number_of_affiliates + 1 ), " +
            "    updated_at = :updated_at " +
            "WHERE id = :id";

        logger.debug(sql + " --- params: " + subscriberId);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource namedParameters =
            new MapSqlParameterSource()
                    .addValue("id", subscriberId)
                    .addValue("updated_at", MyDateUtil.convertToUTCDate(new Date()));
        int numberOfRowsAffected = namedParameterJdbcTemplate.update(sql, namedParameters, keyHolder);

        return numberOfRowsAffected;
    }

    @Override
    public boolean isAffiliateCreatedBy(String affiliateId, String createdBy){
        logger.debug("isAffiliateCreatedBy: " + affiliateId + "and createdBy: " + createdBy);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT a.id FROM Agent a");
        sb.append(" WHERE a.affiliateId = (:affiliateId)");
        sb.append(" AND a.createdBy = (:createdBy)");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("affiliateId", affiliateId);
        query.setParameter("createdBy", createdBy);

        return query.uniqueResult() != null;
    }

    @Override
    public boolean isAffiliatesSameSubscriber(String... affiliateIds) {
        logger.debug("isAffiliatesSameSubscriber: " + affiliateIds);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT subscriberId, count(*) FROM Agent a");
        sb.append(" WHERE a.affiliateId IN (:affiliateIds)");
        sb.append("  GROUP BY subscriberId");

        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameterList("affiliateIds", affiliateIds);

        List list = query.list();
        if (list.size() != 1) {
            return false;
        }

        Object[] result = (Object[]) list.get(0);
        Long count = (Long) result[1];

        return count == affiliateIds.length;
    }

    @Override
    public boolean isAffiliatesSameSubscriber(List<String> affiliateIds) {
        logger.debug("isAffiliatesSameSubscriber: " + affiliateIds);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT subscriberId, count(*) FROM Agent a");
        sb.append(" WHERE a.affiliateId IN (:affiliateIds)");
        sb.append("  GROUP BY subscriberId");

        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameterList("affiliateIds", affiliateIds);

        List list = query.list();
        if (list.size() != 1) {
            return false;
        }

        Object[] result = (Object[]) list.get(0);
        Long count = (Long) result[1];

        return count == affiliateIds.size();
    }

    @Override
    public boolean isCategoryNameExistentInSubscriber(String name, List<String> subsAdminIds){
        logger.debug("isCategoryNameExistentInSubscriber: " + name + "and subsAdminIds: " + subsAdminIds);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT c.id FROM Category c");
        sb.append(" WHERE c.name = (:name)");
        sb.append(" AND c.subsAdminId IN (:subsAdminIds)");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("name", name);
        query.setParameterList("subsAdminIds", subsAdminIds);

        return query.uniqueResult() != null;
    }

    @Override
    public boolean isCustomerIdsBelongToAffiliateId(Set<String> customerIds, String affiliateId) {
        logger.debug("isCustomerIdsBelongToAffiliateId: " + customerIds + " - affiliateId: " + affiliateId);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT COUNT(DISTINCT o.customerId) FROM Order o");
        sb.append(" WHERE o.affiliateId = (:affiliateId)");
        sb.append(" AND o.customerId IN (:customerIds)");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("affiliateId", affiliateId);
        query.setParameterList("customerIds", customerIds);
        Long result = (Long) query.uniqueResult();

        return result == customerIds.size();
    }

    @Override
    public <T> boolean isPropertyValueExistent(Class<T> clazz, String fieldName, String fieldValue) {
        logger.debug("check if '" + fieldName + "' already has value '" + fieldValue  + "' in " + clazz.toString());

        String queryStr = "FROM " + clazz.getName() + " e " +
                          "WHERE e. " + fieldName + " = :fieldValue";
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("fieldValue", fieldValue);

        Object result;
        try {
            result = query.uniqueResult();
        }
        catch (ObjectNotFoundException onfe) {
            return false;
        }

        return result != null;
    }

    @Override
    public boolean isVoucherIdsBelongToAffiliateId(Set<String> voucherIds, String affiliateId) {
        logger.debug("isVoucherIdsBelongToAffiliateId: " + voucherIds + " - affiliateId: " + affiliateId);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT COUNT(DISTINCT av.voucherId) FROM AffiliateVoucher av");
        sb.append(" WHERE av.affiliateId = (:affiliateId)");
        sb.append(" AND av.voucherId IN (:voucherIds)");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("affiliateId", affiliateId);
        query.setParameterList("voucherIds", voucherIds);
        Long result = (Long) query.uniqueResult();

        return result == voucherIds.size();
    }

    @Override
    public int updateClickInfoCount(String clickInfoId, int clicks) {
        logger.debug("updateClickInfoCount:" + clickInfoId + ", " + clicks);

        String sql = "UPDATE click_infos " +
                "SET count = " + String.format(CONDITION_TEMPLATE, "count", clicks, clicks, "count") +
                ",updated_at = :updated_at " +
                "WHERE id = :id";

        logger.debug(sql + " --- params: " + clickInfoId);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource namedParameters =
                new MapSqlParameterSource().addValue("id", clickInfoId)
                        .addValue("updated_at", MyDateUtil.convertToUTCDate(new Date()));
        int numberOfRowsAffected = namedParameterJdbcTemplate.update(sql, namedParameters, keyHolder);

        return numberOfRowsAffected;
    }

    @Override
    public int updateGetflySuccess(String id, boolean success) {
        logger.info("updateGetflySuccess: " + id + " success: " + success);

        String sql = "UPDATE orders " +
                "SET is_getfly_success = :success, updated_at = :updated_at " +
                "WHERE id = :id";

        logger.info(sql + " --- params: " + id);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource namedParameters =
                new MapSqlParameterSource().addValue("success", success)
                        .addValue("id", id)
                        .addValue("updated_at", MyDateUtil.convertToUTCDate(new Date()));
        int numberOfRowsAffected = namedParameterJdbcTemplate.update(sql, namedParameters, keyHolder);

        return numberOfRowsAffected;
    }

    @Override
    public int updateGetResponseSuccessOfAffiliate(String id, boolean success) {
        logger.info("updateGetResponseSuccessOfAffiliate: " + id + ", success: " + success);

        String sql = "UPDATE persons " +
                "SET is_get_response_success = :success, updated_at = :updated_at " +
                "WHERE id = :id";

        logger.info(sql + " --- params: " + id + ", success: " + success);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource namedParameters =
                new MapSqlParameterSource().addValue("success", success)
                        .addValue("id", id)
                        .addValue("updated_at", MyDateUtil.convertToUTCDate(new Date()));
        int numberOfRowsAffected = namedParameterJdbcTemplate.update(sql, namedParameters, keyHolder);

        return numberOfRowsAffected;
    }

    @Override
    public int updateGetResponseSuccessOfOrder(String id, boolean success) {
        logger.info("updateGetResponseSuccessOfOrder: " + id + ", success: " + success);

        String sql = "UPDATE orders " +
                "SET is_get_response_success = :success, updated_at = :updated_at " +
                "WHERE id = :id";

        logger.info(sql + " --- params: " + id + ", success: " + success);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource namedParameters =
                new MapSqlParameterSource().addValue("success", success)
                        .addValue("id", id)
                        .addValue("updated_at", MyDateUtil.convertToUTCDate(new Date()));
        int numberOfRowsAffected = namedParameterJdbcTemplate.update(sql, namedParameters, keyHolder);

        return numberOfRowsAffected;
    }

    @Override
    public int updateInfusionSuccess(String id, boolean success) {
        logger.info("updateInfusionSuccess: " + id + " success: " + success);

        String sql = "UPDATE orders " +
                "SET is_infusion_success = :success, updated_at = :updated_at " +
                "WHERE id = :id";

        logger.info(sql + " --- params: " + id);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource namedParameters =
                new MapSqlParameterSource().addValue("success", success)
                        .addValue("id", id)
                        .addValue("updated_at", MyDateUtil.convertToUTCDate(new Date()));
        int numberOfRowsAffected = namedParameterJdbcTemplate.update(sql, namedParameters, keyHolder);

        return numberOfRowsAffected;
    }

    @Override
    public int updateShareClickCount(String shareId, int clicks) {
        logger.debug("updateShareClickCount:" + shareId + ", " + clicks);

        String sql = "UPDATE shares " +
                "SET click_count = " + String.format(CONDITION_TEMPLATE, "click_count", clicks, clicks, "click_count") +
                ",updated_at = :updated_at " +
                "WHERE id = :id";

        logger.debug(sql + " --- params: " + shareId);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource namedParameters =
                new MapSqlParameterSource().addValue("id", shareId)
                        .addValue("updated_at", MyDateUtil.convertToUTCDate(new Date()));
        int numberOfRowsAffected = namedParameterJdbcTemplate.update(sql, namedParameters, keyHolder);

        return numberOfRowsAffected;
    }


    /* DANGEROUS SECTION
     Methods to reset data of a Subscriber in a business transaction, call in order
    */
    @Override
    public int deletePostsBySubscriberId(String subscriberId) {
        logger.debug("deletePostsBySubscriberId: " + subscriberId);

        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM Post p ");
        sb.append("WHERE p.subsAdminId IN ( ");
        sb.append("  SELECT id FROM SubsAdmin WHERE subscriberId = :subscriberId ) ");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("subscriberId", subscriberId);

        return query.executeUpdate();
    }

    @Override
    public int deleteCategoriesBySubscriberId(String subscriberId) {
        logger.debug("deleteCategoriesBySubscriberId: " + subscriberId);

        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM Category c ");
        sb.append("WHERE c.subsAdminId IN ( ");
        sb.append("  SELECT id FROM SubsAdmin WHERE subscriberId = :subscriberId ) ");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("subscriberId", subscriberId);

        return query.executeUpdate();
    }

    @Override
    public int deletePaymentsBySubscriberId(String subscriberId) {
        logger.debug("deletePaymentsBySubscriberId: " + subscriberId);

        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM Payment p ");
        sb.append("WHERE p.subscriberId = :subscriberId ");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("subscriberId", subscriberId);

        return query.executeUpdate();
    }

    @Override
    public int deleteDiscountCodesAppliedBySubscriberId(String subscriberId) {
        logger.debug("deleteDiscountCodesAppliedBySubscriberId: " + subscriberId);

        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM DiscountCodeApplied dca ");
        sb.append("WHERE dca.subscriberId = :subscriberId ");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("subscriberId", subscriberId);

        return query.executeUpdate();
    }

    @Override
    public int deleteSubsCommissionConfigsAppliedBySubscriberId(String subscriberId) {
        logger.debug("deleteSubsCommissionConfigsAppliedBySubscriberId: " + subscriberId);

        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM SubsCommissionConfigApplied scca ");
        sb.append("WHERE scca.subscriberId = :subscriberId ");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("subscriberId", subscriberId);

        return query.executeUpdate();
    }

    @Override
    public int deleteCommissionsBySubscriberId(String subscriberId) {
        logger.debug("deleteCommissionsBySubscriberId: " + subscriberId);

        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM Commission c ");
        sb.append("WHERE c.subscriberId = :subscriberId ");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("subscriberId", subscriberId);

        return query.executeUpdate();
    }

    @Override
    public int deleteDiscountCodesBySubscriberId(String subscriberId) {
        logger.debug("deleteDiscountCodesBySubscriberId: " + subscriberId);

        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM DiscountCode dc ");
        sb.append("WHERE dc.subscriberId = :subscriberId ");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("subscriberId", subscriberId);

        return query.executeUpdate();
    }

    @Override
    public int deletePerformersBySubscriberId(String subscriberId) {
        // TODO: implement code when DO created
        return 0;
    }

    @Override
    public int deleteOrderLinesSubscriberId(String subscriberId) {
        logger.debug("deleteOrderLinesSubscriberId: " + subscriberId);

        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM OrderLine ol ");
        sb.append("WHERE ol.orderId IN ( ");
        sb.append("  SELECT id FROM Order o WHERE o.channel.subscriberId = :subscriberId ) ");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("subscriberId", subscriberId);

        return query.executeUpdate();
    }

    @Override
    public int deleteOrdersBySubscriberId(String subscriberId) {
        logger.debug("deleteOrdersBySubscriberId: " + subscriberId);

        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM Order o ");
        sb.append("WHERE o.channelId IN ( ");
        sb.append("  SELECT id FROM Channel WHERE subscriberId = :subscriberId ) ");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("subscriberId", subscriberId);

        return query.executeUpdate();
    }

    @Override
    public int deleteProductsBySubscriberId(String subscriberId) {
        logger.debug("deleteProductsBySubscriberId: " + subscriberId);

        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM Product p ");
        sb.append("WHERE p.channelId IN ( ");
        sb.append("  SELECT id FROM Channel WHERE subscriberId = :subscriberId ) ");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("subscriberId", subscriberId);

        return query.executeUpdate();
    }

    @Override
    public int deleteCustomersBySubscriberId(String subscriberId) {
        logger.debug("deleteCustomersBySubscriberId: " + subscriberId);

        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM Customer c ");
        sb.append("WHERE c.subscriberId = :subscriberId ");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("subscriberId", subscriberId);

        return query.executeUpdate();
    }

    @Override
    public int deleteClickInfosBySubscriberId(String subscriberId) {
        logger.debug("deleteClickInfosBySubscriberId: " + subscriberId);

        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ClickInfo ci ");
        sb.append("WHERE ci.shareId IN ( ");
        sb.append("  SELECT id FROM Share s WHERE s.channel.subscriberId = :subscriberId ) ");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("subscriberId", subscriberId);

        return query.executeUpdate();
    }

    @Override
    public int deleteSharesBySubscriberId(String subscriberId) {
        logger.debug("deleteSharesBySubscriberId: " + subscriberId);

        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM Share s ");
        sb.append("WHERE s.channelId IN ( ");
        sb.append("  SELECT id FROM Channel WHERE subscriberId = :subscriberId ) ");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("subscriberId", subscriberId);

        return query.executeUpdate();
    }

    @Override
    public int deleteLinksByLinkIds(List<String> linkIds) {
        logger.debug("deleteLinksByLinkIds: " + linkIds);

        if (Collections.isEmpty(linkIds)) {
            return 0;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM Link l ");
        sb.append("WHERE l.id IN :linkIds ");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameterList("linkIds", linkIds);

        return query.executeUpdate();
    }

    @Override
    public int deleteRewardsBySubscriberId(String subscriberId) {
        // TODO: implement code when DO created
        return 0;
    }

    @Override
    public int deleteAffiliateVouchersBySubscriberId(String subscriberId) {
        // TODO: implement code when DO created
        return 0;
    }

    @Override
    public int deleteVouchersBySubscriberId(String subscriberId) {
        logger.debug("deleteVouchersBySubscriberId: " + subscriberId);

        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM Voucher v ");
        sb.append("WHERE v.subscriberId = :subscriberId ");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("subscriberId", subscriberId);

        return query.executeUpdate();
    }

    @Override
    public int resetAffiliateEarningsBySubscriberId(String subscriberId) {
        logger.debug("resetAffiliateEarningsBySubscriberId: " + subscriberId);

        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE Agent a ");
        sb.append("SET a.earning = :earning ");
        sb.append("WHERE a.subscriberId = :subscriberId ");
        String queryStr = sb.toString();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("earning", 0d);
        query.setParameter("subscriberId", subscriberId);

        return query.executeUpdate();
    }

    /* DANGEROUS SECTION */


    // Utilities //
    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

}
