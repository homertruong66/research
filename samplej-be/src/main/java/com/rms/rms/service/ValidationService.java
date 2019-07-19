package com.rms.rms.service;

import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.service.model.*;

/**
 * homertruong
 */

public interface ValidationService {

    Admin getAdmin(String adminId, boolean forUpdate) throws BusinessException;

    Agent getAgent(String affiliateId, String subscriberId, boolean forUpdate) throws BusinessException;

    Affiliate getAffiliate(String affiliateId, boolean forUpdate) throws BusinessException;

    Affiliate getAffiliateByNickname(String nickname, boolean forUpdate) throws BusinessException;

    Bill getBill(String billId, boolean forUpdate) throws BusinessException;

    Category getCategory(String categoryId, boolean forUpdate) throws BusinessException;

    Channel getChannel(String channelId, boolean forUpdate) throws BusinessException;

    Customer getCustomer(String customerId, boolean forUpdate) throws BusinessException;

    Channel getChannelByDomainName(String domainName, boolean forUpdate) throws BusinessException;

    DiscountCode getDiscountCode(String discountCodeId, boolean forUpdate) throws BusinessException;

    DiscountCode getDiscountCodeByAffiliateIdAndCode(String affiliateId, String code, boolean forUpdate) throws BusinessException;

    Domain getDomain(String domainId, boolean forUpdate) throws BusinessException;

    Feedback getFeedback(String feedbackId, boolean forUpdate) throws BusinessException;

    Guide getGuide(String guideId, boolean forUpdate) throws BusinessException;

    Guide getGuideByTarget(String target, boolean forUpdate) throws BusinessException;

    Notification getNotification(String notificationId, boolean forUpdate) throws BusinessException;

    Order getOrder(String orderId, boolean forUpdate) throws BusinessException;

    PackageConfig getPackageConfig(String packageConfigId, boolean forUpdate) throws BusinessException;

    PackageConfig getPackageConfigByType(String type, boolean forUpdate) throws BusinessException;

    PackageConfigApplied getPackageConfigApplied(String subscriberId, boolean forUpdate) throws BusinessException;

    Payment getPayment(String paymentId, boolean forUpdate) throws BusinessException;

    Product getProduct(String productId, boolean forUpdate) throws BusinessException;

    Person getPerson(String personId, boolean forUpdate) throws BusinessException;

    Post getPost(String postId, boolean forUpdate) throws BusinessException;

    PriorityGroup getPriorityGroup(String priorityGroupId, boolean forUpdate) throws BusinessException;

    ProductSet getProductSet(String productSetId, boolean forUpdate) throws BusinessException;

    Role getRoleByName(String roleName, boolean forUpdate) throws BusinessException;

    SubsAdmin getSubsAdmin(String subsAdminId, boolean forUpdate) throws BusinessException;

    SubsAlertConfig getSubsAlertConfig(String id, boolean forUpdate) throws BusinessException;

    SubsConfig getSubsConfig(String subsConfigId, boolean forUpdate) throws BusinessException;

    Subscriber getSubscriber(String subscriberId, boolean forUpdate) throws BusinessException;

    Subscriber getSubscriberByDomainName(String domainName, boolean forUpdate) throws BusinessException;

    SubsCommissionConfig getSubsCommissionConfig(String subsCommissionConfigId, boolean forUpdate) throws BusinessException;

    SubsEarnerConfig getSubsEarnerConfig(String subsEarnerConfigId, boolean forUpdate) throws BusinessException;

    SubsPerformerConfig getSubsPerformerConfig(String subsPerformerConfigId, boolean forUpdate) throws BusinessException;

    SubsRewardConfig getSubsRewardConfig(String subsRewardConfigId, boolean forUpdate) throws BusinessException;

    SubsEmailConfig getSubsEmailConfig(String subsEmailConfigId, boolean forUpdate) throws BusinessException;

    SubsEmailTemplate getSubsEmailTemplate(String subsEmailTemplateId, boolean forUpdate) throws BusinessException;

    SubsEmailTemplate getSubsEmailTemplateBySubscriberIdAndType(String subscriberId, String type, boolean forUpdate) throws BusinessException;

    SubsGetflyConfig getSubsGetflyConfig(String subsGetflyConfigId, boolean forUpdate) throws BusinessException;

    SubsGetResponseConfig getSubsGetResponseConfig(String subsGetResponseConfigId, boolean forUpdate) throws BusinessException;

    SubsInfusionConfig getSubsInfusionConfig(String subsInfusionConfigId, boolean forUpdate) throws BusinessException;

    SubsPackageConfig getSubsPackageConfig(String subsPackageConfigId, boolean forUpdate) throws BusinessException;

    SubsPackageStatus getSubsPackageStatus(String subsPackageStatusId, boolean forUpdate) throws BusinessException;

    SystemAlert getSystemAlert(String systemAlertId, boolean forUpdate) throws BusinessException;

    User getUser(String userId, boolean forUpdate) throws BusinessException;

    User getUserByEmail(String email, boolean forUpdate) throws BusinessException;

    Voucher getVoucher(String voucherId, boolean forUpdate) throws BusinessException;

}