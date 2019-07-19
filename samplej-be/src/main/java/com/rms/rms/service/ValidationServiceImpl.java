package com.rms.rms.service;

import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.persistence.GenericDao;
import com.rms.rms.persistence.PersonDao;
import com.rms.rms.persistence.SpecificDao;
import com.rms.rms.service.model.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * homertruong
 */

@Service
@Transactional(value = "transactionManager", propagation = Propagation.SUPPORTS)
public class ValidationServiceImpl implements ValidationService {

    private Logger logger = Logger.getLogger(ValidationServiceImpl.class);

    @Autowired
    private GenericDao genericDao;

    @Autowired
    private PersonDao personDao;

    @Autowired
    private SpecificDao specificDao;

    @Override
    public Admin getAdmin(String adminId, boolean forUpdate) throws BusinessException {
        if (StringUtils.isBlank(adminId)) {
            throw new BusinessException(BusinessException.ADMIN_NOT_FOUND,
                    String.format(BusinessException.ADMIN_NOT_FOUND_MESSAGE, ""));
        }

        Admin pdo = personDao.read(Admin.class, adminId, forUpdate);
        if (pdo == null) {
            throw new BusinessException(BusinessException.ADMIN_NOT_FOUND,
                    String.format(BusinessException.ADMIN_NOT_FOUND_MESSAGE, adminId));
        }

        return pdo;
    }

    @Override
    public Agent getAgent(String affiliateId, String subscriberId, boolean forUpdate) throws BusinessException {
        if (StringUtils.isBlank(affiliateId) || StringUtils.isBlank(subscriberId)) {
            throw new BusinessException(BusinessException.AGENT_NOT_FOUND,
                    String.format(BusinessException.AGENT_NOT_FOUND_MESSAGE, affiliateId, subscriberId));
        }

        Map<String, Object> params = new HashMap<>();
        params.put("affiliateId", affiliateId);
        params.put("subscriberId", subscriberId);
        Agent pdo = genericDao.readUnique(Agent.class, params, forUpdate);
        if (pdo == null) {
            throw new BusinessException(BusinessException.AGENT_NOT_FOUND,
                    String.format(BusinessException.AGENT_NOT_FOUND_MESSAGE, affiliateId, subscriberId));
        }

        return pdo;
    }

    @Override
    public Affiliate getAffiliate(String affiliateId, boolean forUpdate) throws BusinessException {
        if (StringUtils.isBlank(affiliateId)) {
            throw new BusinessException(BusinessException.AFFILIATE_NOT_FOUND,
                    String.format(BusinessException.AFFILIATE_NOT_FOUND_MESSAGE, ""));
        }

        Affiliate pdo = personDao.read(Affiliate.class, affiliateId, forUpdate);
        if (pdo == null) {
            throw new BusinessException(BusinessException.AFFILIATE_NOT_FOUND,
                    String.format(BusinessException.AFFILIATE_NOT_FOUND_MESSAGE, affiliateId));
        }

        return pdo;
    }

    @Override
    public Affiliate getAffiliateByNickname(String nickname, boolean forUpdate) throws BusinessException {
        if (StringUtils.isBlank(nickname)) {
            throw new BusinessException(BusinessException.AFFILIATE_NOT_FOUND,
                    String.format(BusinessException.AFFILIATE_NOT_FOUND_MESSAGE, ""));
        }

        Map<String, Object> params = new HashMap<>();
        params.put("nickname", nickname);
        Affiliate pdo = genericDao.readUnique(Affiliate.class, params, forUpdate);
        if (pdo == null) {
            throw new BusinessException(BusinessException.AFFILIATE_NOT_FOUND,
                    String.format(BusinessException.AFFILIATE_NOT_FOUND_MESSAGE, nickname));
        }

        return pdo;
    }

    @Override
    public Bill getBill(String billId, boolean forUpdate) throws BusinessException {
        if (StringUtils.isBlank(billId)) {
            throw new BusinessException(BusinessException.BILL_NOT_FOUND,
                    String.format(BusinessException.BILL_NOT_FOUND_MESSAGE, billId));
        }

        Bill pdo = personDao.read(Bill.class, billId, forUpdate);
        if (pdo == null) {
            throw new BusinessException(BusinessException.BILL_NOT_FOUND,
                    String.format(BusinessException.BILL_NOT_FOUND_MESSAGE, billId));
        }

        return pdo;
    }

    @Override
    public Category getCategory(String categoryId, boolean forUpdate) throws BusinessException {
        if (StringUtils.isBlank(categoryId)) {
            throw new BusinessException(BusinessException.CATEGORY_NOT_FOUND,
                    String.format(BusinessException.CATEGORY_NOT_FOUND_MESSAGE, ""));
        }

        Category pdo = genericDao.read(Category.class, categoryId, forUpdate);
        if (pdo == null) {
            throw new BusinessException(BusinessException.CATEGORY_NOT_FOUND,
                    String.format(BusinessException.CATEGORY_NOT_FOUND_MESSAGE, categoryId));
        }

        return pdo;
    }

    @Override
    public Channel getChannel(String channelId, boolean forUpdate) throws BusinessException {
        if (StringUtils.isBlank(channelId)) {
            throw new BusinessException(BusinessException.CHANNEL_NOT_FOUND,
                    String.format(BusinessException.CHANNEL_NOT_FOUND_MESSAGE, ""));
        }

        Channel pdo = genericDao.read(Channel.class, channelId, forUpdate);
        if (pdo == null) {
            throw new BusinessException(BusinessException.CHANNEL_NOT_FOUND,
                    String.format(BusinessException.CHANNEL_NOT_FOUND_MESSAGE, channelId));
        }

        return pdo;
    }

    @Override
    public Customer getCustomer(String customerId, boolean forUpdate) throws BusinessException {
        if (StringUtils.isBlank(customerId)) {
            throw new BusinessException(BusinessException.CUSTOMER_NOT_FOUND,
                    String.format(BusinessException.CUSTOMER_NOT_FOUND_MESSAGE, ""));
        }

        Customer pdo = genericDao.read(Customer.class, customerId, forUpdate);
        if (pdo == null) {
            throw new BusinessException(BusinessException.CUSTOMER_NOT_FOUND,
                    String.format(BusinessException.CUSTOMER_NOT_FOUND_MESSAGE, customerId));
        }

        return pdo;
    }

    @Override
    public Channel getChannelByDomainName(String domainName, boolean forUpdate) throws BusinessException {
        if (StringUtils.isBlank(domainName)) {
            throw new BusinessException(BusinessException.CHANNEL_NOT_FOUND,
                    String.format(BusinessException.CHANNEL_NOT_FOUND_MESSAGE, ""));
        }

        Map<String, Object> params = new HashMap<>();
        params.put("domainName", domainName);
        Channel pdo = genericDao.readUnique(Channel.class, params, forUpdate);
        if (pdo == null) {
            throw new BusinessException(BusinessException.CHANNEL_NOT_FOUND,
                    String.format(BusinessException.CHANNEL_NOT_FOUND_MESSAGE, domainName));
        }

        return pdo;
    }

    @Override
    public DiscountCode getDiscountCode(String discountCodeId, boolean forUpdate) throws BusinessException {
        if (StringUtils.isBlank(discountCodeId)) {
            throw new BusinessException(BusinessException.DISCOUNT_CODE_NOT_FOUND,
                    String.format(BusinessException.DISCOUNT_CODE_NOT_FOUND_MESSAGE, ""));
        }

        DiscountCode pdo = genericDao.read(DiscountCode.class, discountCodeId, forUpdate);
        if (pdo == null) {
            throw new BusinessException(BusinessException.DISCOUNT_CODE_NOT_FOUND,
                    String.format(BusinessException.DISCOUNT_CODE_NOT_FOUND_MESSAGE, discountCodeId));
        }

        return pdo;
    }

    @Override
    public DiscountCode getDiscountCodeByAffiliateIdAndCode(String affiliateId, String code, boolean forUpdate) throws BusinessException {
        if (StringUtils.isBlank(affiliateId) || StringUtils.isBlank(code)) {
            throw new BusinessException(BusinessException.DISCOUNT_CODE_CODE_OF_AFFILIATE_NOT_FOUND,
                String.format(BusinessException.DISCOUNT_CODE_CODE_OF_AFFILIATE_NOT_FOUND_MESSAGE, code, affiliateId));
        }

        Map<String, Object> params = new HashMap<>();
        params.put("affiliateId", affiliateId);
        params.put("code", code);
        DiscountCode pdo = genericDao.readUnique(DiscountCode.class, params, forUpdate);
        if (pdo == null) {
            throw new BusinessException(BusinessException.DISCOUNT_CODE_CODE_OF_AFFILIATE_NOT_FOUND,
                String.format(BusinessException.DISCOUNT_CODE_CODE_OF_AFFILIATE_NOT_FOUND_MESSAGE, code, affiliateId));
        }

        return pdo;
    }

    @Override
    public Domain getDomain(String domainId, boolean forUpdate) throws BusinessException {
        if (StringUtils.isBlank(domainId)) {
            throw new BusinessException(BusinessException.DOMAIN_NOT_FOUND,
                    String.format(BusinessException.DOMAIN_NOT_FOUND_MESSAGE, ""));
        }

        Domain pdo = genericDao.read(Domain.class, domainId, forUpdate);
        if (pdo == null) {
            throw new BusinessException(BusinessException.DOMAIN_NOT_FOUND,
                    String.format(BusinessException.DOMAIN_NOT_FOUND_MESSAGE, domainId));
        }

        return pdo;
    }

    @Override
    public Feedback getFeedback(String feedbackId, boolean forUpdate) throws BusinessException {
        if (StringUtils.isBlank(feedbackId)) {
            throw new BusinessException(BusinessException.FEEDBACK_NOT_FOUND,
                    String.format(BusinessException.FEEDBACK_NOT_FOUND_MESSAGE, ""));
        }

        Feedback pdo = genericDao.read(Feedback.class, feedbackId, forUpdate);
        if (pdo == null) {
            throw new BusinessException(BusinessException.FEEDBACK_NOT_FOUND,
                    String.format(BusinessException.FEEDBACK_NOT_FOUND_MESSAGE, feedbackId));
        }

        return pdo;
    }

    @Override
    public Guide getGuide(String guideId, boolean forUpdate) throws BusinessException {
        if (StringUtils.isBlank(guideId)) {
            throw new BusinessException(BusinessException.GUIDE_NOT_FOUND,
                    String.format(BusinessException.GUIDE_NOT_FOUND_MESSAGE, ""));
        }

        Guide pdo = genericDao.read(Guide.class, guideId, forUpdate);
        if (pdo == null) {
            throw new BusinessException(BusinessException.GUIDE_NOT_FOUND,
                    String.format(BusinessException.GUIDE_NOT_FOUND_MESSAGE, guideId));
        }

        return pdo;
    }

    @Override
    public Guide getGuideByTarget(String target, boolean forUpdate) throws BusinessException {
        if (StringUtils.isBlank(target)) {
            throw new BusinessException(BusinessException.GUIDE_GUIDE_OF_TARGET_NOT_FOUND,
                    String.format(BusinessException.GUIDE_GUIDE_OF_TARGET_NOT_FOUND_MESSAGE, target));
        }

        Map<String, Object> params = new HashMap<>();
        params.put("target", target);
        Guide pdo = genericDao.readUnique(Guide.class, params, forUpdate);
        if (pdo == null) {
            throw new BusinessException(BusinessException.GUIDE_GUIDE_OF_TARGET_NOT_FOUND,
                    String.format(BusinessException.GUIDE_GUIDE_OF_TARGET_NOT_FOUND_MESSAGE, target));
        }

        return pdo;
    }

    @Override
    public Notification getNotification(String notificationId, boolean forUpdate) throws BusinessException {
        if (StringUtils.isBlank(notificationId)) {
            throw new BusinessException(BusinessException.NOTIFICATION_NOT_FOUND,
                    String.format(BusinessException.NOTIFICATION_NOT_FOUND_MESSAGE, ""));
        }

        Notification pdo = genericDao.read(Notification.class, notificationId, forUpdate);
        if (pdo == null) {
            throw new BusinessException(BusinessException.NOTIFICATION_NOT_FOUND,
                    String.format(BusinessException.NOTIFICATION_NOT_FOUND_MESSAGE, notificationId));
        }

        return pdo;
    }

    @Override
    public Order getOrder(String orderId, boolean forUpdate) throws BusinessException {
        if (StringUtils.isBlank(orderId)) {
            throw new BusinessException(BusinessException.ORDER_NOT_FOUND,
                    String.format(BusinessException.ORDER_NOT_FOUND_MESSAGE, ""));
        }

        Order pdo = genericDao.read(Order.class, orderId, forUpdate);
        if (pdo == null) {
            throw new BusinessException(BusinessException.ORDER_NOT_FOUND,
                    String.format(BusinessException.ORDER_NOT_FOUND_MESSAGE, orderId));
        }

        return pdo;
    }

    @Override
    public PackageConfig getPackageConfig(String packageConfigId, boolean forUpdate) throws BusinessException {
        if (StringUtils.isBlank(packageConfigId)) {
            throw new BusinessException(BusinessException.PACKAGE_CONFIG_NOT_FOUND,
                    String.format(BusinessException.PACKAGE_CONFIG_NOT_FOUND_MESSAGE, ""));
        }

        PackageConfig pdo = genericDao.read(PackageConfig.class, packageConfigId, forUpdate);
        if (pdo == null) {
            throw new BusinessException(BusinessException.PACKAGE_CONFIG_NOT_FOUND,
                    String.format(BusinessException.PACKAGE_CONFIG_NOT_FOUND_MESSAGE, packageConfigId));
        }

        return pdo;
    }

    @Override
    public PackageConfig getPackageConfigByType(String type, boolean forUpdate) throws BusinessException {
        if (StringUtils.isBlank(type)) {
            throw new BusinessException(BusinessException.PACKAGE_CONFIG_NOT_FOUND,
                    String.format(BusinessException.PACKAGE_CONFIG_NOT_FOUND_MESSAGE, ""));
        }

        Map<String, Object> params = new HashMap<>();
        params.put("type", type);
        PackageConfig pdo = genericDao.readUnique(PackageConfig.class, params, forUpdate);
        if (pdo == null) {
            throw new BusinessException(BusinessException.PACKAGE_CONFIG_NOT_FOUND,
                    String.format(BusinessException.PACKAGE_CONFIG_NOT_FOUND_MESSAGE, type));
        }

        return pdo;
    }

    @Override
    public PackageConfigApplied getPackageConfigApplied(String subscriberId, boolean forUpdate) throws BusinessException {
        if (StringUtils.isBlank(subscriberId)) {
            throw new BusinessException(BusinessException.PACKAGE_CONFIG_APPLIED_NOT_FOUND,
                    String.format(BusinessException.PACKAGE_CONFIG_APPLIED_NOT_FOUND_MESSAGE, ""));
        }

        PackageConfigApplied pdo = genericDao.read(PackageConfigApplied.class, subscriberId, forUpdate);
        if (pdo == null) {
            throw new BusinessException(BusinessException.PACKAGE_CONFIG_APPLIED_NOT_FOUND,
                    String.format(BusinessException.PACKAGE_CONFIG_APPLIED_NOT_FOUND_MESSAGE, subscriberId));
        }

        return pdo;
    }

    @Override
    public Payment getPayment(String paymentId, boolean forUpdate) throws BusinessException {
        if (StringUtils.isBlank(paymentId)) {
            throw new BusinessException(BusinessException.PAYMENT_NOT_FOUND,
                    String.format(BusinessException.PAYMENT_NOT_FOUND_MESSAGE, ""));
        }

        Payment pdo = genericDao.read(Payment.class, paymentId, forUpdate);
        if (pdo == null) {
            throw new BusinessException(BusinessException.PAYMENT_NOT_FOUND,
                    String.format(BusinessException.PAYMENT_NOT_FOUND_MESSAGE, paymentId));
        }

        return pdo;
    }

    @Override
    public Product getProduct(String productId, boolean forUpdate) throws BusinessException {
        if (StringUtils.isBlank(productId)) {
            throw new BusinessException(BusinessException.PRODUCT_NOT_FOUND,
                    String.format(BusinessException.PRODUCT_NOT_FOUND_MESSAGE, ""));
        }

        Product pdo = genericDao.read(Product.class, productId, forUpdate);
        if (pdo == null) {
            throw new BusinessException(BusinessException.PRODUCT_NOT_FOUND,
                    String.format(BusinessException.PRODUCT_NOT_FOUND_MESSAGE, productId));
        }

        return pdo;
    }

    @Override
    public Person getPerson(String personId, boolean forUpdate) throws BusinessException {
        if (StringUtils.isBlank(personId)) {
            throw new BusinessException(BusinessException.USER_NOT_FOUND,
                    String.format(BusinessException.USER_NOT_FOUND_MESSAGE, ""));
        }

        Person pdo = genericDao.read(Person.class, personId, forUpdate);
        if (pdo == null) {
            throw new BusinessException(BusinessException.USER_NOT_FOUND,
                    String.format(BusinessException.USER_NOT_FOUND_MESSAGE, personId));
        }

        return pdo;
    }

    @Override
    public SubsAdmin getSubsAdmin(String subsAdminId, boolean forUpdate) throws BusinessException {
        if (StringUtils.isBlank(subsAdminId)) {
            throw new BusinessException(BusinessException.SUBS_ADMIN_NOT_FOUND,
                    String.format(BusinessException.SUBS_ADMIN_NOT_FOUND_MESSAGE, ""));
        }

        SubsAdmin pdo = genericDao.read(SubsAdmin.class, subsAdminId, forUpdate);
        if (pdo == null) {
            throw new BusinessException(BusinessException.SUBS_ADMIN_NOT_FOUND,
                    String.format(BusinessException.SUBS_ADMIN_NOT_FOUND_MESSAGE, subsAdminId));
        }

        return pdo;
    }

    @Override
    public SubsAlertConfig getSubsAlertConfig(String subsAlertConfigId, boolean forUpdate) throws BusinessException {
        if (StringUtils.isBlank(subsAlertConfigId)) {
            throw new BusinessException(BusinessException.SUBS_ALERT_CONFIG_NOT_FOUND,
                    String.format(BusinessException.SUBS_ALERT_CONFIG_NOT_FOUND_MESSAGE, ""));
        }

        SubsAlertConfig pdo = genericDao.read(SubsAlertConfig.class, subsAlertConfigId, forUpdate);
        if (pdo == null) {
            throw new BusinessException(BusinessException.SUBS_ALERT_CONFIG_NOT_FOUND,
                    String.format(BusinessException.SUBS_ALERT_CONFIG_NOT_FOUND_MESSAGE, subsAlertConfigId));
        }

        return pdo;
    }

    @Override
    public Post getPost(String postId, boolean forUpdate) throws BusinessException {
        if (StringUtils.isBlank(postId)) {
            throw new BusinessException(BusinessException.POST_NOT_FOUND,
                    String.format(BusinessException.POST_NOT_FOUND_MESSAGE, ""));
        }

        Post pdo = genericDao.read(Post.class, postId, forUpdate);
        if (pdo == null) {
            throw new BusinessException(BusinessException.POST_NOT_FOUND,
                    String.format(BusinessException.POST_NOT_FOUND_MESSAGE, postId));
        }

        return pdo;
    }

    @Override
    public PriorityGroup getPriorityGroup(String priorityGroupId, boolean forUpdate) throws BusinessException {
        if (StringUtils.isBlank(priorityGroupId)) {
            throw new BusinessException(BusinessException.PRIORITY_GROUP_NOT_FOUND,
                    String.format(BusinessException.PRIORITY_GROUP_NOT_FOUND_MESSAGE, ""));
        }

        PriorityGroup pdo = genericDao.read(PriorityGroup.class, priorityGroupId, forUpdate);
        if ( pdo == null ) {
            throw new BusinessException(BusinessException.PRIORITY_GROUP_NOT_FOUND,
                    String.format(BusinessException.PRIORITY_GROUP_NOT_FOUND_MESSAGE, priorityGroupId));
        }

        return pdo;
    }

    @Override
    public ProductSet getProductSet(String productSetId, boolean forUpdate) throws BusinessException {
        if (StringUtils.isBlank(productSetId)) {
            throw new BusinessException(BusinessException.PRODUCT_SET_NOT_FOUND,
                    String.format(BusinessException.PRODUCT_SET_NOT_FOUND_MESSAGE, ""));
        }

        ProductSet pdo = genericDao.read(ProductSet.class, productSetId, forUpdate);
        if (pdo == null) {
            throw new BusinessException(BusinessException.PRODUCT_SET_NOT_FOUND,
                    String.format(BusinessException.PRODUCT_SET_NOT_FOUND_MESSAGE, productSetId));
        }

        return pdo;
    }

    @Override
    public Role getRoleByName(String roleName, boolean forUpdate) throws BusinessException {
        if (StringUtils.isBlank(roleName)) {
            throw new BusinessException(BusinessException.ROLE_NOT_FOUND,
                    String.format(BusinessException.ROLE_NOT_FOUND_MESSAGE,  ""));
        }

        Map<String, Object> param = new HashMap<>();
        param.put("name", roleName);
        Role pdo = genericDao.readUnique(Role.class, param, forUpdate);
        if (pdo == null) {
            throw new BusinessException(BusinessException.ROLE_NOT_FOUND,
                    String.format(BusinessException.ROLE_NOT_FOUND_MESSAGE,  roleName));
        }

        return pdo;
    }

    @Override
    public SubsConfig getSubsConfig(String subsConfigId, boolean forUpdate) throws BusinessException {
        if (StringUtils.isBlank(subsConfigId)) {
            throw new BusinessException(BusinessException.SUBS_CONFIG_NOT_FOUND,
                    String.format(BusinessException.SUBS_CONFIG_NOT_FOUND_MESSAGE, ""));
        }

        SubsConfig pdo = genericDao.read(SubsConfig.class, subsConfigId, forUpdate);
        if (pdo == null) {
            throw new BusinessException(BusinessException.SUBS_CONFIG_NOT_FOUND,
                    String.format(BusinessException.SUBS_CONFIG_NOT_FOUND_MESSAGE, subsConfigId));
        }

        return pdo;
    }

    @Override
    public Subscriber getSubscriber(String subscriberId, boolean forUpdate) throws BusinessException {
        if (StringUtils.isBlank(subscriberId)) {
            throw new BusinessException(BusinessException.SUBSCRIBER_NOT_FOUND,
                    String.format(BusinessException.SUBSCRIBER_NOT_FOUND_MESSAGE, ""));
        }

        Subscriber pdo = genericDao.read(Subscriber.class, subscriberId, forUpdate);
        if (pdo == null) {
            throw new BusinessException(BusinessException.SUBSCRIBER_NOT_FOUND,
                    String.format(BusinessException.SUBSCRIBER_NOT_FOUND_MESSAGE, subscriberId));
        }

        return pdo;
    }

    @Override
    public Subscriber getSubscriberByDomainName(String domainName, boolean forUpdate) throws BusinessException {
        if (StringUtils.isBlank(domainName)) {
            throw new BusinessException(BusinessException.SUBSCRIBER_NOT_FOUND,
                    String.format(BusinessException.SUBSCRIBER_NOT_FOUND_MESSAGE, ""));
        }

        Map<String, Object> params = new HashMap<>();
        params.put("domainName", domainName);
        Subscriber pdo = personDao.readUnique(Subscriber.class, params, forUpdate);
        if (pdo == null) {
            throw new BusinessException(BusinessException.SUBSCRIBER_NOT_FOUND,
                    String.format(BusinessException.SUBSCRIBER_NOT_FOUND_MESSAGE, domainName));
        }

        return pdo;
    }

    @Override
    public SubsCommissionConfig getSubsCommissionConfig(String subsCommissionConfigId, boolean forUpdate) throws BusinessException {
        if (StringUtils.isBlank(subsCommissionConfigId)) {
            throw new BusinessException(BusinessException.SUBS_COMMISSION_CONFIG_NOT_FOUND,
                    String.format(BusinessException.SUBS_COMMISSION_CONFIG_NOT_FOUND_MESSAGE, ""));
        }

        SubsCommissionConfig pdo = genericDao.read(SubsCommissionConfig.class, subsCommissionConfigId, forUpdate);
        if (pdo == null) {
            throw new BusinessException(BusinessException.SUBS_COMMISSION_CONFIG_NOT_FOUND,
                    String.format(BusinessException.SUBS_COMMISSION_CONFIG_NOT_FOUND_MESSAGE, subsCommissionConfigId));
        }

        return pdo;
    }

    @Override
    public SubsEarnerConfig getSubsEarnerConfig (String subsEarnerConfigId, boolean forUpdate) throws BusinessException {
        if (StringUtils.isBlank(subsEarnerConfigId)) {
            throw new BusinessException(BusinessException.SUBS_EARNER_CONFIG_NOT_FOUND,
                    String.format(BusinessException.SUBS_EARNER_CONFIG_NOT_FOUND_MESSAGE, ""));
        }

        SubsEarnerConfig pdo = genericDao.read(SubsEarnerConfig.class, subsEarnerConfigId, forUpdate);
        if (pdo == null) {
            throw new BusinessException(BusinessException.SUBS_EARNER_CONFIG_NOT_FOUND,
                    String.format(BusinessException.SUBS_EARNER_CONFIG_NOT_FOUND_MESSAGE, subsEarnerConfigId));
        }
        return pdo;
    }

    @Override
    public SubsPerformerConfig getSubsPerformerConfig(String subsPerformerConfigId, boolean forUpdate) throws BusinessException {
        if (StringUtils.isBlank(subsPerformerConfigId)) {
            throw new BusinessException(BusinessException.SUBS_PERFORMER_CONFIG_NOT_FOUND,
                    String.format(BusinessException.SUBS_PERFORMER_CONFIG_NOT_FOUND_MESSAGE, ""));
        }

        SubsPerformerConfig pdo = genericDao.read(SubsPerformerConfig.class, subsPerformerConfigId, forUpdate);
        if (pdo == null) {
            throw new BusinessException(BusinessException.SUBS_PERFORMER_CONFIG_NOT_FOUND,
                    String.format(BusinessException.SUBS_PERFORMER_CONFIG_NOT_FOUND_MESSAGE, subsPerformerConfigId));
        }

        return pdo;
    }

    public SubsRewardConfig getSubsRewardConfig(String subsRewardConfigId, boolean forUpdate) throws BusinessException {
        if (StringUtils.isBlank(subsRewardConfigId)) {
            throw new BusinessException(BusinessException.SUBS_REWARD_CONFIG_NOT_FOUND,
                    String.format(BusinessException.SUBS_REWARD_CONFIG_NOT_FOUND_MESSAGE, ""));
        }

        SubsRewardConfig pdo = genericDao.read(SubsRewardConfig.class, subsRewardConfigId, forUpdate);
        if (pdo == null) {
            throw new BusinessException(BusinessException.SUBS_REWARD_CONFIG_NOT_FOUND,
                    String.format(BusinessException.SUBS_REWARD_CONFIG_NOT_FOUND_MESSAGE, subsRewardConfigId));
        }

        return pdo;
    }

    @Override
    public SubsEmailConfig getSubsEmailConfig(String subsEmailConfigId, boolean forUpdate) throws BusinessException {
        if (StringUtils.isBlank(subsEmailConfigId)) {
            throw new BusinessException(BusinessException.SUBS_EMAIL_CONFIG_NOT_FOUND,
                    String.format(BusinessException.SUBS_EMAIL_CONFIG_NOT_FOUND_MESSAGE, ""));
        }

        SubsEmailConfig pdo = genericDao.read(SubsEmailConfig.class, subsEmailConfigId, forUpdate);
        if (pdo == null) {
            throw new BusinessException(BusinessException.SUBS_EMAIL_CONFIG_NOT_FOUND,
                    String.format(BusinessException.SUBS_EMAIL_CONFIG_NOT_FOUND_MESSAGE, subsEmailConfigId));
        }

        return pdo;
    }

    @Override
    public SubsEmailTemplate getSubsEmailTemplate(String subsEmailTemplateId, boolean forUpdate) throws BusinessException {
        if (StringUtils.isBlank(subsEmailTemplateId)) {
            throw new BusinessException(BusinessException.SUBS_EMAIL_TEMPLATE_NOT_FOUND,
                    String.format(BusinessException.SUBS_EMAIL_TEMPLATE_NOT_FOUND_MESSAGE, ""));
        }

        SubsEmailTemplate pdo = genericDao.read(SubsEmailTemplate.class, subsEmailTemplateId, forUpdate);
        if (pdo == null) {
            throw new BusinessException(BusinessException.SUBS_EMAIL_TEMPLATE_NOT_FOUND,
                    String.format(BusinessException.SUBS_EMAIL_TEMPLATE_NOT_FOUND_MESSAGE, subsEmailTemplateId));
        }

        return pdo;
    }

    @Override
    public SubsEmailTemplate getSubsEmailTemplateBySubscriberIdAndType(String subscriberId, String type, boolean forUpdate) throws BusinessException {
        if (StringUtils.isBlank(subscriberId) || StringUtils.isBlank(type)) {
            throw new BusinessException(BusinessException.SUBS_EMAIL_TEMPLATE_BY_SUBSCRIBER_AND_TYPE_NOT_FOUND,
                String.format(BusinessException.SUBS_EMAIL_TEMPLATE_BY_SUBSCRIBER_AND_TYPE_NOT_FOUND_MESSAGE,
                              subscriberId, type));
        }

        Map<String, Object> params = new HashMap<>();
        params.put("subsEmailConfigId", subscriberId);
        params.put("type", type);
        SubsEmailTemplate pdo = genericDao.readUnique(SubsEmailTemplate.class, params, forUpdate);
        if (pdo == null ){
            throw new BusinessException(BusinessException.SUBS_EMAIL_TEMPLATE_BY_SUBSCRIBER_AND_TYPE_NOT_FOUND,
                String.format(BusinessException.SUBS_EMAIL_TEMPLATE_BY_SUBSCRIBER_AND_TYPE_NOT_FOUND_MESSAGE,
                              subscriberId, type));
        }

        return pdo;
    }

    @Override
    public SubsGetflyConfig getSubsGetflyConfig(String subsGetflyConfigId, boolean forUpdate) throws BusinessException {
        if (StringUtils.isBlank(subsGetflyConfigId)) {
            throw new BusinessException(BusinessException.SUBS_GETFLY_CONFIG_NOT_FOUND,
                    String.format(BusinessException.SUBS_GETFLY_CONFIG_NOT_FOUND_MESSAGE, ""));
        }

        SubsGetflyConfig pdo = genericDao.read(SubsGetflyConfig.class, subsGetflyConfigId, forUpdate);
        if (pdo == null) {
            throw new BusinessException(BusinessException.SUBS_GETFLY_CONFIG_NOT_FOUND,
                    String.format(BusinessException.SUBS_GETFLY_CONFIG_NOT_FOUND_MESSAGE, subsGetflyConfigId));
        }

        return pdo;
    }

    @Override
    public SubsGetResponseConfig getSubsGetResponseConfig(String subsGetResponseConfigId, boolean forUpdate) throws BusinessException {
        if (StringUtils.isBlank(subsGetResponseConfigId)) {
            throw new BusinessException(BusinessException.SUBS_GETRESPONSE_CONFIG_NOT_FOUND,
                    String.format(BusinessException.SUBS_GETRESPONSE_CONFIG_NOT_FOUND_MESSAGE, ""));
        }

        SubsGetResponseConfig pdo = genericDao.read(SubsGetResponseConfig.class, subsGetResponseConfigId, forUpdate);
        if (pdo == null) {
            throw new BusinessException(BusinessException.SUBS_GETRESPONSE_CONFIG_NOT_FOUND,
                    String.format(BusinessException.SUBS_GETRESPONSE_CONFIG_NOT_FOUND_MESSAGE, subsGetResponseConfigId));
        }

        return pdo;
    }

    @Override
    public SubsInfusionConfig getSubsInfusionConfig(String subsInfusionConfigId, boolean forUpdate) throws BusinessException {
        if (StringUtils.isBlank(subsInfusionConfigId)) {
            throw new BusinessException(BusinessException.SUBS_INFUSION_CONFIG_NOT_FOUND,
                    String.format(BusinessException.SUBS_INFUSION_CONFIG_NOT_FOUND_MESSAGE, ""));
        }

        SubsInfusionConfig pdo = genericDao.read(SubsInfusionConfig.class, subsInfusionConfigId, forUpdate);
        if (pdo == null) {
            throw new BusinessException(BusinessException.SUBS_INFUSION_CONFIG_NOT_FOUND,
                    String.format(BusinessException.SUBS_INFUSION_CONFIG_NOT_FOUND_MESSAGE, subsInfusionConfigId));
        }

        return pdo;
    }

    @Override
    public SubsPackageConfig getSubsPackageConfig(String subsPackageConfigId, boolean forUpdate) throws BusinessException {
        if (StringUtils.isBlank(subsPackageConfigId)) {
            throw new BusinessException(BusinessException.SUBS_PACKAGE_CONFIG_NOT_FOUND,
                    String.format(BusinessException.SUBS_PACKAGE_CONFIG_NOT_FOUND_MESSAGE, ""));
        }

        SubsPackageConfig pdo = genericDao.read(SubsPackageConfig.class, subsPackageConfigId, forUpdate);
        if (pdo == null) {
            throw new BusinessException(BusinessException.SUBS_PACKAGE_CONFIG_NOT_FOUND,
                    String.format(BusinessException.SUBS_PACKAGE_CONFIG_NOT_FOUND_MESSAGE, subsPackageConfigId));
        }

        return pdo;
    }

    @Override
    public SubsPackageStatus getSubsPackageStatus(String subsPackageStatusId, boolean forUpdate) throws BusinessException {
        if (StringUtils.isBlank(subsPackageStatusId)) {
            throw new BusinessException(BusinessException.SUBS_PACKAGE_STATUS_NOT_FOUND,
                    String.format(BusinessException.SUBS_PACKAGE_STATUS_NOT_FOUND_MESSAGE, ""));
        }

        SubsPackageStatus pdo = genericDao.read(SubsPackageStatus.class, subsPackageStatusId, forUpdate);
        if (pdo == null) {
            throw new BusinessException(BusinessException.SUBS_PACKAGE_STATUS_NOT_FOUND,
                    String.format(BusinessException.SUBS_PACKAGE_STATUS_NOT_FOUND_MESSAGE, subsPackageStatusId));
        }

        return pdo;
    }

    @Override
    public SystemAlert getSystemAlert(String systemAlertId, boolean forUpdate) throws BusinessException {
        if (StringUtils.isBlank(systemAlertId)) {
            throw new BusinessException(BusinessException.SYSTEM_ALERT_NOT_FOUND,
                    String.format(BusinessException.SYSTEM_ALERT_NOT_FOUND_MESSAGE, ""));
        }

        SystemAlert pdo = genericDao.read(SystemAlert.class, systemAlertId, forUpdate);
        if (pdo == null) {
            throw new BusinessException(BusinessException.SYSTEM_ALERT_NOT_FOUND,
                    String.format(BusinessException.SYSTEM_ALERT_NOT_FOUND_MESSAGE, systemAlertId));
        }

        return pdo;
    }

    @Override
    public User getUser(String userId, boolean forUpdate) throws BusinessException {
        if (StringUtils.isBlank(userId)) {
            throw new BusinessException(BusinessException.USER_NOT_FOUND,
                    String.format(BusinessException.USER_NOT_FOUND_MESSAGE, ""));
        }

        User pdo = genericDao.read(User.class, userId, forUpdate);
        if (pdo == null) {
            throw new BusinessException(BusinessException.USER_NOT_FOUND,
                    String.format(BusinessException.USER_NOT_FOUND_MESSAGE, userId));
        }

        return pdo;
    }

    @Override
    public User getUserByEmail(String email, boolean forUpdate) throws BusinessException {
        if (StringUtils.isBlank(email)) {
            throw new BusinessException(BusinessException.USER_NOT_FOUND,
                    String.format(BusinessException.USER_NOT_FOUND_MESSAGE, ""));
        }

        Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        User pdo = genericDao.readUnique(User.class, params, forUpdate);
        if (pdo == null) {
            throw new BusinessException(BusinessException.USER_NOT_FOUND,
                    String.format(BusinessException.USER_NOT_FOUND_MESSAGE, email));
        }

        return pdo;
    }

    @Override
    public Voucher getVoucher(String voucherId, boolean forUpdate) throws BusinessException {
        if (StringUtils.isBlank(voucherId)) {
            throw new BusinessException(BusinessException.VOUCHER_NOT_FOUND,
                    String.format(BusinessException.VOUCHER_NOT_FOUND_MESSAGE, ""));
        }

        Voucher pdo = genericDao.read(Voucher.class, voucherId, forUpdate);
        if (pdo == null) {
            throw new BusinessException(BusinessException.VOUCHER_NOT_FOUND,
                    String.format(BusinessException.VOUCHER_NOT_FOUND_MESSAGE, voucherId));
        }

        return pdo;
    }
}
