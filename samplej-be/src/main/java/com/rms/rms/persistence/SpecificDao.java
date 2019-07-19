package com.rms.rms.persistence;

import com.rms.rms.service.model.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * homertruong
 */

public interface SpecificDao {

    Long countAffiliatesByReferrer(String referrer);

    Long countAffiliatesBySubscriberId(String subscriberId);

    Long countCustomersByAffiliateId(String affiliateId);

    Long countCustomersByChannelId(String channelId);

    Long countCustomersBySubscriberId(String subscriberId);

    Long countOrdersByAffiliateId(String affiliateId);

    Long countOrdersByChannelId(String channelId);

    Long countOrdersBySubscriberId(String subscriberId);

    Long countShareClicksByChannelId(String channelId);

    Long countSubsAdminsBySubscriberId(String subscriberId);

    void deleteToken(String userId);

    List<String> getAffiliateIdsByNotStatus(String notStatus);

    List<String> getAffiliateIdsBySubscriberId(String subscriberId);

    List<String> getAffiliateIdsCreatedByAffiliateId(String affiliateId);

    List<String> getAffiliateIdsBySubscriberIdAndReferrer(String subscriberId, String referrer);

    List<String> getAffiliateNicknamesByAffiliateIds(List<String> affiliateIds);

    List<Affiliate> getAffiliatesBySubscriberAndReferrer(String subscriberId, String referrer);

    List<String> getChannelIdsBySubscriberId(String subscriberId);

    List<String> getCustomerIdsByAffiliateId(String affiliateId);

    List<String> getCustomerIdsBySubscriberId(String subscriberId);

    List<Customer> getCustomersByAffiliateId(Set<String> customerIds, String affiliateId);

    Double getCommissionEarningByAffiliateIdAndOrderId(String affiliateId, String orderId);

    List<Commission> getCommissionsByProductId(String productId, Date from, Date to);

    List<String> getLinkIdsBySubscriberId(String subscriberId);

    List<String> getOrderIdsByAffiliateId(String affiliateId);

    List<String> getOrderIdsByProductId(String productId);

    List<String> getOrderIdsBySubscriberId(String subscriberId);

    List<String> getOrderIdsByProductName(String productName);

    List<String> getOrderIdsByProductNameAndSubscriberId(String productName, String subscriberId);

    Double getOrderRevenue(String status);

    Double getOrderRevenueByAffiliateId(String affiliateId, String status);

    Double getOrderRevenueByAffiliateIdAndSubscriberId(String affiliateId, String subscriberId, String status);

    Double getOrderRevenueByAffiliateIdAndSubscriberId(String affiliateId, String subscriberId, Date startDate, Date endDate);

    Double getOrderRevenueByAffiliateIdsAndSubscriberId(List<String> affiliateIds, String subscriberId, String status);

    Double getOrderRevenueByChannelId(String channelId, String status);

    Double getOrderRevenueBySubscriberId(String subscriberId, String status);

    List<String> getProductIdsBySubscriberId(String subscriberId);

    List<Map<Date, Integer>> getShareClicksByDate();

    List<Map<Date, Integer>> getShareClicksByDateByAffiliateId(String affiliateId);

    List<Map<Date, Integer>> getShareClicksByDateByAffiliateIdAndSubscriberId(String affiliateId, String subscriberId);

    List<Map<Date,Integer>> getShareClicksByDateByChannelId(String channelId);

    List<Map<Date, Integer>> getShareClicksByDateBySubscriberId(String subscriberId);

    List<String> getSubsAdminIdsBySubscriberId(String subscriberId);

    List<String> getSubsAdminAccountantIdsBySubscriberId(String subscriberId);

    List<String> getSubsAdminEmailsBySubscriberId(String subscriberId);

    List<SubsAlertConfig> getSubsAlertConfigsByAffiliateId(String affiliateId);

    List<String> getProductIdsByChannelIds(List<String> channelIds);

    List<String> getSubscriberIds();

    List<String> getSubscriberIdsByAffiliateId(String affiliateId);

    List<String> getSubsCommissionConfigIdsBySubscriberId(String subscriberId);

    List<String> getUnbilledSubscriberIds(Date startDate, Date endDate);

    List<String> getUserIdsByEmail(String email);

    List<String> getUserIdsByEmailAndStatus(String email, String status);

    List<String> getUserIdsByStatus(String status);

    List<String> getVoucherIdsByAffiliateId(String affiliateId);

    List<Voucher> getVouchersByAffiliateId(Set<String> voucherIds, String affiliateId);

    String healthCheck();

    int insertToken(String userId, String token);

    int increaseAffiliateNumberOfAnAffiliateInASubscriber(String affiliateId, String subscriberId);

    int increaseNumberOfAffiliatesOfSubscriber(String subscriberId);

    boolean isAffiliateCreatedBy(String affiliateId, String createdBy);

    boolean isAffiliatesSameSubscriber(String... affiliateIds);

    boolean isAffiliatesSameSubscriber(List<String> affiliateIds);

    boolean isCategoryNameExistentInSubscriber(String name, List<String> subsAdminIds);

    boolean isCustomerIdsBelongToAffiliateId(Set<String> customerIds, String affiliateId);

    <T> boolean isPropertyValueExistent(Class<T> clazz, String fieldName, String fieldValue);

    boolean isVoucherIdsBelongToAffiliateId(Set<String> voucherIds, String affiliateId);

    int updateClickInfoCount(String clickInfoId, int clicks);

    int updateGetflySuccess(String id, boolean success);

    int updateGetResponseSuccessOfAffiliate(String id, boolean success);

    int updateGetResponseSuccessOfOrder(String id, boolean success);

    int updateInfusionSuccess(String id, boolean success);

    int updateShareClickCount(String shareId, int clicks);

    /* DANGEROUS SECTION
     Methods to reset data of a Subscriber in a business transaction, call in order
    */

    int deletePostsBySubscriberId(String subscriberId);
    int deleteCategoriesBySubscriberId(String subscriberId);
    int deletePaymentsBySubscriberId(String subscriberId);
    int deleteDiscountCodesAppliedBySubscriberId(String subscriberId);
    int deleteSubsCommissionConfigsAppliedBySubscriberId(String subscriberId);
    int deleteCommissionsBySubscriberId(String subscriberId);
    int deleteDiscountCodesBySubscriberId(String subscriberId);
    int deletePerformersBySubscriberId(String subscriberId);
    int deleteOrderLinesSubscriberId(String subscriberId);
    int deleteOrdersBySubscriberId(String subscriberId);
    int deleteProductsBySubscriberId(String subscriberId);
    int deleteCustomersBySubscriberId(String subscriberId);
    int deleteClickInfosBySubscriberId(String subscriberId);
    int deleteLinksByLinkIds(List<String> linkIds);
    int deleteSharesBySubscriberId(String subscriberId);
    int deleteRewardsBySubscriberId(String subscriberId);
    int deleteAffiliateVouchersBySubscriberId(String subscriberId);
    int deleteVouchersBySubscriberId(String subscriberId);
    int resetAffiliateEarningsBySubscriberId(String subscriberId);

    /* DANGEROUS SECTION */
}
