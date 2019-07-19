package com.rms.rms.common.exception;

/**
 * homertruong
 */

public class BusinessException extends Exception {

    // Agent
    public static final int AGENT_NOT_FOUND = 1;
    public static final String AGENT_NOT_FOUND_MESSAGE = "Agent of Affiliate '%s' and Subscriber '%s' not found.";

    // Admin
    public static final int ADMIN_NOT_FOUND = 2;
    public static final String ADMIN_NOT_FOUND_MESSAGE = "Admin '%s' not found.";

    // Affiliate
    public static final int AFFILIATE_NICKNAME_ALREADY_EXIST = 51;
    public static final String AFFILIATE_NICKNAME_ALREADY_EXIST_MESSAGE = "Nickname: '%s' already exists.";
    public static final int AFFILIATE_REFERRER_NOT_FOUND = 52;
    public static final String AFFILIATE_REFERRER_NOT_FOUND_MESSAGE = "Can not find referrer '%s' ";
    public static final int AFFILIATE_ALREADY_ASSIGNED_TO_SUBSCRIBER = 53;
    public static final String AFFILIATE_ALREADY_ASSIGNED_TO_SUBSCRIBER_MESSAGE = "Affiliate '%s' already assigned to Subscriber '%s'";
    public static final int AFFILIATE_NOT_FOUND = 54;
    public static final String AFFILIATE_NOT_FOUND_MESSAGE = "Affiliate '%s' not found.";
    public static final int AFFILIATE_LOGGED_AFFILIATE_NOT_CREATOR = 55;
    public static final String AFFILIATE_LOGGED_AFFILIATE_NOT_CREATOR_MESSAGE = "Logged Affiliate '%s' is not creator of Affiliate '%s'.";
    public static final int AFFILIATE_REFERRER_NOT_BELONG_TO_SUBSCRIBER = 56;
    public static final String AFFILIATE_REFERRER_NOT_BELONG_TO_SUBSCRIBER_MESSAGE = "Referrer '%s' not belong to Subscriber '%s'.";
    public static final int AFFILIATE_ACTIVATION_LINK_INVALID = 57;
    public static final String AFFILIATE_ACTIVATION_LINK_INVALID_MESSAGE = "Invalid activation link, please check again.";
    public static final int AFFILIATE_ACTIVATE_FAILED = 58;
    public static final String AFFILIATE_ACTIVATE_FAILED_MESSAGE = "User not found to activate or link has been expired.";
    public static final int AFFILIATES_NOT_SAME_SUBSCRIBER = 59;
    public static final String AFFILIATES_NOT_SAME_SUBSCRIBER_MESSAGE = "Affiliates are not in the same Subscriber: '%s'";

    // Authorization
    public static final int AUTHORIZATION_LOGGED_USER_NOT_BELONG_TO_SUBSCRIBER = 101;
    public static final String AUTHORIZATION_LOGGED_USER_NOT_BELONG_TO_SUBSCRIBER_MESSAGE = "Logged User '%s' not belong to Subscriber '%s'.";
    public static final int AUTHORIZATION_LOGGED_SUBS_ADMIN_AFFILIATE_NOT_SAME_SUBSCRIBER = 102;
    public static final String AUTHORIZATION_LOGGED_SUBS_ADMIN_AFFILIATE_NOT_SAME_SUBSCRIBER_MESSAGE = "Logged SubsAdmin '%s' not same Subscriber with Affiliate '%s'.";
    public static final int AUTHORIZATION_LOGGED_CHANNEL_AFFILIATE_NOT_SAME_SUBSCRIBER = 103;
    public static final String AUTHORIZATION_LOGGED_CHANNEL_AFFILIATE_NOT_SAME_SUBSCRIBER_MESSAGE = "Logged Channel '%s' not same Subscriber with Affiliate '%s'.";

    // AWS
    public static final int AWS_S3_GENERATE_PRE_SIGNED_URL_FAILED = 151;
    public static final String AWS_S3_GENERATE_PRE_SIGNED_URL_FAILED_MESSAGE = "Failure to generate Pre-Signed Url with '%s'.";

    // Bill
    public static final int BILL_NOT_FOUND = 201;
    public static final String BILL_NOT_FOUND_MESSAGE = "Bill '%s' not found.";
    public static final int BILL_CONFIRM_ERROR = 202;
    public static final String BILL_CONFIRM_ERROR_MESSAGE = "Can not confirm '%s' Bill.";

    // Category
    public static final int CATEGORY_NAME_EXISTS_IN_SUBSCRIBER = 251;
    public static final String CATEGORY_NAME_EXISTS_IN_SUBSCRIBER_MESSAGE = "Category name '%s' already exists in Subscriber '%s'.";
    public static final int CATEGORY_NOT_FOUND = 252;
    public static final String CATEGORY_NOT_FOUND_MESSAGE = "Category '%s' not found.";

    // Channel
    public static final int CHANNEL_DOMAIN_NAME_EXISTS = 301;
    public static final String CHANNEL_DOMAIN_NAME_EXISTS_MESSAGE = "Channel domain name '%s' exists.";
    public static final int CHANNEL_NOT_FOUND = 302;
    public static final String CHANNEL_NOT_FOUND_MESSAGE = "Channel '%s' not found.";

    // DiscountCode
    public static final int DISCOUNT_CODE_CODE_EXISTS_IN_SUBSCRIBER = 351;
    public static final String DISCOUNT_CODE_CODE_EXISTS_IN_SUBSCRIBER_MESSAGE = "DiscountCode '%s' exists in Subscriber '%s'.";
    public static final int DISCOUNT_CODE_NOT_FOUND = 352;
    public static final String DISCOUNT_CODE_NOT_FOUND_MESSAGE = "DiscountCode '%s' not found.";
    public static final int DISCOUNT_CODE_START_DATE_UPDATE_INVALID = 353;
    public static final String DISCOUNT_CODE_START_DATE_UPDATE_INVALID_MESSAGE = "DiscountCode start_date must <= existing endDate.";
    public static final int DISCOUNT_CODE_END_DATE_UPDATE_INVALID = 354;
    public static final String DISCOUNT_CODE_END_DATE_UPDATE_INVALID_MESSAGE = "DiscountCode endDate must >= existing startDate.";
    public static final int DISCOUNT_CODE_LOGGED_AFFILIATE_NOT_CREATOR = 355;
    public static final String DISCOUNT_CODE_LOGGED_AFFILIATE_NOT_CREATOR_MESSAGE = "Logged Affiliate '%s' is not creator of DiscountCode '%s'.";
    public static final int DISCOUNT_CODE_CODE_OF_AFFILIATE_NOT_FOUND = 356;
    public static final String DISCOUNT_CODE_CODE_OF_AFFILIATE_NOT_FOUND_MESSAGE = "DiscountCode code '%s' of Affiliate '%s' not found.";
    public static final int DISCOUNT_CODE_SUBSCRIBER_PACKAGE_NOT_ALLOWED = 357;
    public static final String DISCOUNT_CODE_SUBSCRIBER_PACKAGE_NOT_ALLOWED_MESSAGE = "DiscountCode is not supported for Subscriber '%s'.";

    // Guide
    public static final int GUIDE_NOT_FOUND = 410;
    public static final String GUIDE_NOT_FOUND_MESSAGE = "Guide '%s' not found.";
    public static final int GUIDE_GUIDE_OF_TARGET_NOT_FOUND = 411;
    public static final String GUIDE_GUIDE_OF_TARGET_NOT_FOUND_MESSAGE = "Guide of target '%s' not found.";

    // Order
    public static final int ORDER_NUMBER_EXISTS_IN_CHANNEL = 451;
    public static final String ORDER_NUMBER_EXISTS_IN_CHANNEL_MESSAGE = "Order number '%s' exists in Channel '%s'.";
    public static final int ORDER_AFFILIATE_CHANNEL_NOT_SAME_SUBSCRIBER = 452;
    public static final String ORDER_AFFILIATE_CHANNEL_NOT_SAME_SUBSCRIBER_MESSAGE = "Affiliate and Channel must have the same Subscriber.";
    public static final int ORDER_NOT_FOUND = 453;
    public static final String ORDER_NOT_FOUND_MESSAGE = "Order '%s' not found.";
    public static final int ORDER_CAN_NOT_UPDATE_NON_NEW_ORDER = 454;
    public static final String ORDER_CAN_NOT_UPDATE_NON_NEW_ORDER_MESSAGE = "Can only update Order with non-NEW status.";
    public static final int ORDER_WRONG_ORDER_CREATOR = 455;
    public static final String ORDER_WRONG_ORDER_CREATOR_MESSAGE = "Only Order creator can update Order.";
    public static final int ORDER_AFFILIATE_DATA_AUTHORIZATION = 456;
    public static final String ORDER_AFFILIATE_DATA_AUTHORIZATION_MESSAGE = "Can not view other Affiliates' Order '%s'.";
    public static final int ORDER_APPROVE_ERROR = 457;
    public static final String ORDER_APPROVE_ERROR_MESSAGE = "Can not approve '%s' Order.";
    public static final int ORDER_REJECT_ERROR = 458;
    public static final String ORDER_REJECT_ERROR_MESSAGE = "Can not reject '%s' Order.";
    public static final int ORDER_INVALID_ACTION = 459;
    public static final String ORDER_INVALID_ACTION_MESSAGE = "Invalid Action '%s' on Order.";
    public static final int ORDER_STATUS_INVALID_FOR_COMMISSION_CALCULATION = 460;
    public static final String ORDER_STATUS_INVALID_FOR_COMMISSION_CALCULATION_MESSAGE = "Invalid Order status '%s' for Commission Calculation.";
    public static final int ORDER_CONFIRM_COMMISSIONS_DONE_ERROR = 461;
    public static final String ORDER_CONFIRM_COMMISSIONS_DONE_ERROR_MESSAGE = "Can not confirm Commissions done on '%s' Order.";

    // PackageConfig
    public static final int PACKAGE_CONFIG_NOT_FOUND = 1151;
    public static final String PACKAGE_CONFIG_NOT_FOUND_MESSAGE = "Can not find PackageConfig '%s'.";
    public static final int PACKAGE_CONFIG_WRONG_AFFILIATE_COUNT_FOR_TYPE = 1152;
    public static final String PACKAGE_CONFIG_WRONG_AFFILIATE_COUNT_FOR_TYPE_MESSAGE = "Wrong affiliateCount '%s' for type '%s'.";
    public static final int PACKAGE_CONFIG_WRONG_CHANNEL_COUNT_FOR_TYPE = 1153;
    public static final String PACKAGE_CONFIG_WRONG_CHANNEL_COUNT_FOR_TYPE_MESSAGE = "Wrong channelCount '%s' for type '%s'.";
    public static final int PACKAGE_CONFIG_WRONG_LAYER_COUNT_FOR_TYPE = 1154;
    public static final String PACKAGE_CONFIG_WRONG_LAYER_COUNT_FOR_TYPE_MESSAGE = "Wrong layerCount '%s' for type '%s'.";
    public static final int PACKAGE_CONFIG_INVALID_USAGE_PERIOD = 1155;
    public static final String PACKAGE_CONFIG_INVALID_USAGE_PERIOD_MESSAGE = "Invalid PackageConfig usagePeriod '%s'.";
    public static final int PACKAGE_CONFIG_APPLIED_NOT_FOUND = 1156;
    public static final String PACKAGE_CONFIG_APPLIED_NOT_FOUND_MESSAGE = "Can not find PackageConfigApplied '%s'.";

    // Payment
    public static final int PAYMENT_VALUE_MORE_THAN_AFFILIATE_EARNING = 501;
    public static final String PAYMENT_VALUE_MORE_THAN_AFFILIATE_EARNING_MESSAGE = "Payment value '%s' must less than or equal Affiliate's earning '%s'.";
    public static final int PAYMENT_NOT_FOUND = 502;
    public static final String PAYMENT_NOT_FOUND_MESSAGE = "Can not find Payment '%s'.";
    public static final int PAYMENT_APPROVE_ERROR = 503;
    public static final String PAYMENT_APPROVE_ERROR_MESSAGE = "Can not approve '%s' Payment.";
    public static final int PAYMENT_REJECT_ERROR = 504;
    public static final String PAYMENT_REJECT_ERROR_MESSAGE = "Can not reject '%s' Payment.";
    public static final int PAYMENT_INVALID_ACTION = 505;
    public static final String PAYMENT_INVALID_ACTION_MESSAGE = "Invalid Action '%s' on Payment.";
    public static final int PAYMENT_DATA_AUTHORIZATION = 506;
    public static final String PAYMENT_DATA_AUTHORIZATION_MESSAGE = "Only Payment creator or SubsAdmin can process id: '%s'.";
    public static final int PAYMENT_VALUE_LESS_THAN_LOWEST_PAYMENT = 507;
    public static final String PAYMENT_VALUE_LESS_THAN_LOWEST_PAYMENT_MESSAGE = "Payment value '%s' must more than or equal SubsConfig lowestPayment '%s'.";
    public static final int PAYMENT_NEW_BALANCE_LESS_THAN_ACCOUNT_KEEPING_FEE = 508;
    public static final String PAYMENT_NEW_BALANCE_LESS_THAN_ACCOUNT_KEEPING_FEE_MESSAGE = "Balance '%s' must more than or equal SubsConfig accountKeepingFee '%s'.";

    // Post
    public static final int POST_NOT_FOUND = 801;
    public static final String POST_NOT_FOUND_MESSAGE = "Post '%s' not found.";
    public static final int POST_SUBSCRIBER_ID_NOT_PROVIDED = 802;
    public static final String POST_SUBSCRIBER_ID_NOT_PROVIDED_MESSAGE = "SubscriberId must be provided.";
    public static final int POST_DOES_NOT_BELONG_TO_SUBSCRIBER = 803;
    public static final String POST_DOES_NOT_BELONG_TO_SUBSCRIBER_MESSAGE = "Post '%s' does not belong to Subscriber '%s'.";

    // Priority Group
    public static final int    PRIORITY_GROUP_START_DATE_VS_END_DATE                 = 851;
    public static final String PRIORITY_GROUP_START_DATE_VS_END_DATE_MESSAGE         = "PriorityGroup start_date ['%s'] can't be after end_date ['%s'].";
    public static final int    PRIORITY_GROUP_NOT_FOUND                              = 852;
    public static final String PRIORITY_GROUP_NOT_FOUND_MESSAGE                      = "PriorityGroup '%s' not found.";
    public static final int PRIORITY_GROUP_AFFILIATE_ALREADY_ASSIGNED = 853;
    public static final String PRIORITY_GROUP_AFFILIATE_ALREADY_ASSIGNED_MESSAGE     = "Affiliate '%s' already assigned for PriorityGroup '%s'.";
    public static final String PRIORITY_GROUP_AFFILIATE_NOT_SAME_SUBSCRIBER_MESSAGE  = "Affiliate '%s' and PriorityGroup '%s' do not belong to the same Subscriber.";
    public static final int PRIORITY_GROUP_AFFILIATE_NOT_IN = 854;
    public static final String PRIORITY_GROUP_AFFILIATE_NOT_IN_MESSAGE               = "PriorityGroup '%s' doesn't contain Affiliate '%s'.";

    // Role
    public static final int ROLE_NOT_FOUND = 901;
    public static final String ROLE_NOT_FOUND_MESSAGE = "Role '%s' not found.";
    public static final int ROLE_INVALID = 902;
    public static final String ROLE_INVALID_MESSAGE = "Can not change Role '%s'.";

    // SubsAdmin
    public static final int SUBS_ADMIN_NOT_FOUND = 951;
    public static final String SUBS_ADMIN_NOT_FOUND_MESSAGE = "SubsAdmin '%s' not found.";
    public static final int SUBS_ADMIN_ROLE_NOT_FOUND = 952;
    public static final String SUBS_ADMIN_ROLE_NOT_FOUND_MESSAGE = "Can not find role '%s' in SubsAdmin '%s'.";
    public static final int SUBS_ADMIN_ROLE_ALREADY_EXIST = 953;
    public static final String SUBS_ADMIN_ROLE_ALREADY_EXIST_MESSAGE = "SubsAdmin '%s' already has role '%s'.";
    public static final int CAN_NOT_UPDATE_ROOT_SUBS_ADMIN = 954;
    public static final String CAN_NOT_UPDATE_ROOT_SUBS_ADMIN_MESSAGE = "Can't update root SubsAdmin '%s'.";
    public static final int CAN_NOT_DELETE_ROOT_SUBS_ADMIN = 955;
    public static final String CAN_NOT_DELETE_ROOT_SUBS_ADMIN_MESSAGE = "Can't delete root SubsAdmin '%s'.";
    public static final int SUBS_ADMIN_IS_ALREADY_ROOT = 956;
    public static final String SUBS_ADMIN_IS_ALREADY_ROOT_MESSAGE = "SubsAdmin '%s' is already Root SubsAdmin.";
    public static final int SUBS_ADMIN_IS_NOT_ROOT_SUBS_ADMIN = 957;
    public static final String SUBS_ADMIN_IS_NOT_ROOT_SUBS_ADMIN_MESSAGE = "SubsAdmin '%s' is not a Root SubsAdmin.";

    // Subscriber
    public static final int SUBSCRIBER_DOMAIN_NAME_EXISTS = 1001;
    public static final String SUBSCRIBER_DOMAIN_NAME_EXISTS_MESSAGE = "Subscriber domain name '%s' already exists.";
    public static final int SUBSCRIBER_NOT_FOUND = 1002;
    public static final String SUBSCRIBER_NOT_FOUND_MESSAGE = "Subscriber '%s' not found.";
    public static final int SUBS_CONFIG_NOT_FOUND = 1003;
    public static final String SUBS_CONFIG_NOT_FOUND_MESSAGE = "SubsConfig '%s' not found.";
    public static final int SUBS_CONFIG_COAN_TYPE_INVALID = 1004;
    public static final String SUBS_CONFIG_COAN_TYPE_INVALID_MESSAGE = "'coanType' is invalid.";
    public static final int SUBS_EMAIL_CONFIG_NOT_FOUND = 1005;
    public static final String SUBS_EMAIL_CONFIG_NOT_FOUND_MESSAGE = "SubsEmailConfig '%s' not found.";
    public static final int SUBS_EMAIL_TEMPLATE_NOT_FOUND = 1006;
    public static final String SUBS_EMAIL_TEMPLATE_NOT_FOUND_MESSAGE = "SubsEmailTemplate '%s' not found.";
    public static final int SUBS_EMAIL_TEMPLATE_BY_SUBSCRIBER_AND_TYPE_NOT_FOUND = 1007;
    public static final String SUBS_EMAIL_TEMPLATE_BY_SUBSCRIBER_AND_TYPE_NOT_FOUND_MESSAGE = "SubsEmailTemplate of Subscriber '%s' and type '%s' not found.";
    public static final int SUBS_COMMISSION_CONFIG_NOT_FOUND = 1008;
    public static final String SUBS_COMMISSION_CONFIG_NOT_FOUND_MESSAGE = "SubsCommissionConfig '%s' not found.";
    public static final int SUBS_COMMISSION_CONFIG_IS_DISABLED = 1009;
    public static final String SUBS_COMMISSION_CONFIG_IS_DISABLED_MESSAGE = "SubsCommissionConfig '%s' is disabled.";
    public static final int SUBS_PACKAGE_CONFIG_NOT_FOUND = 1010;
    public static final String SUBS_PACKAGE_CONFIG_NOT_FOUND_MESSAGE = "SubsPackageConfig '%s' not found.";
    public static final int SUBS_PACKAGE_CONFIG_WRONG_AFFILIATE_COUNT = 1011;
    public static final String SUBS_PACKAGE_CONFIG_WRONG_AFFILIATE_COUNT_MESSAGE = "Wrong affiliateCount '%s'.";
    public static final int SUBS_PACKAGE_CONFIG_WRONG_CHANNEL_COUNT = 1012;
    public static final String SUBS_PACKAGE_CONFIG_WRONG_CHANNEL_COUNT_MESSAGE = "Wrong channelCount '%s'.";
    public static final int SUBS_INFUSION_CONFIG_NOT_FOUND = 1013;
    public static final String SUBS_INFUSION_CONFIG_NOT_FOUND_MESSAGE = "SubsInfusionConfig '%s' not found.";
    public static final int SUBS_INFUSION_CONFIG_ACCESS_TOKEN_UNAUTHORIZED = 1014;
    public static final String SUBS_INFUSION_CONFIG_ACCESS_TOKEN_UNAUTHORIZED_MESSAGE = "SubsInfusionConfig '%s' not authorized yet !";
    public static final int SUBS_GETFLY_CONFIG_NOT_FOUND = 1015;
    public static final String SUBS_GETFLY_CONFIG_NOT_FOUND_MESSAGE = "SubsGetflyConfig '%s' not found.";
    public static final int SUBSCRIBER_INVALID_PACKAGE_TYPE = 1016;
    public static final String SUBSCRIBER_INVALID_PACKAGE_TYPE_MESSAGE = "Invalid package type '%s' for the Subscriber";
    public static final int SUBS_PACKAGE_STATUS_NOT_FOUND = 1017;
    public static final String SUBS_PACKAGE_STATUS_NOT_FOUND_MESSAGE = "SubsPackageStatus '%s' not found.";
    public static final int SUBS_GETFLY_CONFIG_BASEURL_APIKEY_NOT_FOUND = 1018;
    public static final String SUBS_GETFLY_CONFIG_BASEURL_APIKEY_NOT_FOUND_MESSAGE = "SubsGetflyConfig '%s' baseUrl or apiKey not found !";
    public static final int SUBS_EARNER_CONFIG_NOT_FOUND = 1019;
    public static final String SUBS_EARNER_CONFIG_NOT_FOUND_MESSAGE = "SubsEarnerConfig '%s' not found";
    public static final int SUBS_PERFORMER_CONFIG_NOT_FOUND = 1020;
    public static final String SUBS_PERFORMER_CONFIG_NOT_FOUND_MESSAGE = "SubsPerformerConfig '%s' not found";
    public static final int SUBS_REWARD_CONFIG_NOT_FOUND = 1021;
    public static final String SUBS_REWARD_CONFIG_NOT_FOUND_MESSAGE = "SubsRewardConfig '%s' not found";
    public static final int SUBSCRIBER_USAGE_EXPIRED = 1022;
    public static final String SUBSCRIBER_USAGE_EXPIRED_MESSAGE = "Subscriber usage expired at: '%s'.";
    public static final int SUBSCRIBER_AFFILIATE_COUNT_REACH_LIMIT = 1023;
    public static final String SUBSCRIBER_AFFILIATE_COUNT_REACH_LIMIT_MESSAGE = "Affiliate Count reach limit: '%s'.";
    public static final int SUBSCRIBER_CHANNEL_COUNT_REACH_LIMIT = 1024;
    public static final String SUBSCRIBER_CHANNEL_COUNT_REACH_LIMIT_MESSAGE = "Channel Count reach limit: '%s'.";
    public static final int SUBSCRIBER_LAYER_COUNT_REACH_LIMIT = 1025;
    public static final String SUBSCRIBER_LAYER_COUNT_REACH_LIMIT_MESSAGE = "Layer Count reach limit: '%s'.";
    public static final int SUBSCRIBER_NOT_HAVE_INFUSION = 1026;
    public static final String SUBSCRIBER_NOT_HAVE_INFUSION_MESSAGE = "Subscriber '%s' not have Infusion.";
    public static final int SUBSCRIBER_NOT_HAVE_GETFLY = 1027;
    public static final String SUBSCRIBER_NOT_HAVE_GETFLY_MESSAGE = "Subscriber '%s' not have Getfly.";
    public static final int SUBSCRIBER_NOT_HAVE_SHARE_STATS = 1028;
    public static final String SUBSCRIBER_NOT_HAVE_SHARE_STATS_MESSAGE = "Subscriber '%s' not have Share Stats.";
    public static final int SUBSCRIBER_NOT_HAVE_VOUCHER = 1029;
    public static final String SUBSCRIBER_NOT_HAVE_VOUCHER_MESSAGE = "Subscriber '%s' not have Voucher.";
    public static final int SUBSCRIBER_INVALID_UPGRADE_PACKAGE_TYPE = 1030;
    public static final String SUBSCRIBER_INVALID_UPGRADE_PACKAGE_TYPE_MESSAGE = "Invalid package type to upgrade from package type '%s'.";
    public static final int SUBSCRIBER_CAN_NOT_RENEW_TRIAL = 1031;
    public static final String SUBSCRIBER_CAN_NOT_RENEW_TRIAL_MESSAGE = "Can not renew TRIAL plan.";
    public static final int SUBSCRIBER_COMPANY_NAME_EXISTS = 1032;
    public static final String SUBSCRIBER_COMPANY_NAME_EXISTS_MESSAGE = "Subscriber company name '%s' already exists.";
    public static final int SUBS_GETRESPONSE_CONFIG_NOT_FOUND = 1033;
    public static final String SUBS_GETRESPONSE_CONFIG_NOT_FOUND_MESSAGE = "SubsGetResponseConfig '%s' not found.";
    public static final int SUBSCRIBER_NOT_HAVE_GETRESPONSE = 1034;
    public static final String SUBSCRIBER_NOT_HAVE_GETRESPONSE_MESSAGE = "Subscriber '%s' not have GetResponse.";
    public static final int SUBS_GETRESPONSE_CONFIG_CAMPAIGN_DEFAULT_APIKEY_NOT_FOUND = 1035;
    public static final String SUBS_GETRESPONSE_CONFIG_CAMPAIGN_DEFAULT_APIKEY_NOT_FOUND_MESSAGE = "SubsGetResponseConfig '%s' campaignDefaultId or apiKey not found !";

    // User
    public static final int USER_EMAIL_EXISTS = 1051;
    public static final String USER_EMAIL_EXISTS_MESSAGE = "User email '%s' already exists.";
    public static final int USER_NOT_FOUND = 1052;
    public static final String USER_NOT_FOUND_MESSAGE = "User '%s' not found.";
    public static final int USER_INACTIVE = 1053;
    public static final String USER_INACTIVE_MESSAGE = "User '%s' is inactive.";
    public static final int USER_DATA_AUTHORIZATION = 1054;
    public static final String USER_DATA_AUTHORIZATION_MESSAGE = "Can not process data of different User '%s' ";
    public static final int USER_WRONG_PASSWORD = 1055;
    public static final String USER_WRONG_PASSWORD_MESSAGE = "Wrong password!";

    // Domain
    public static final int DOMAIN_NOT_FOUND = 1100;
    public static final String DOMAIN_NOT_FOUND_MESSAGE = "Domain '%s' not found";

    // Customer
    public static final int CUSTOMER_NOT_FOUND = 1150;
    public static final String CUSTOMER_NOT_FOUND_MESSAGE = "Customer '%s' not found";
    public static final int CUSTOMER_NOT_BELONG_TO_AFFILIATE = 1151;
    public static final String CUSTOMER_NOT_BELONG_TO_AFFILIATE_MESSAGE = "Customers '%s' not be long to Affiliate '%s' !";
    public static final int CUSTOMER_ALREADY_EXIST = 1152;
    public static final String CUSTOMER_ALREADY_EXIST_MESSAGE = "Customer '%s' already exist";

    // Voucher
    public static final int VOUCHER_CODE_EXISTS_IN_SUBSCRIBER = 1200;
    public static final String VOUCHER_CODE_EXISTS_IN_SUBSCRIBER_MESSAGE = "Voucher code '%s' already exists in Subscriber '%s'.";
    public static final int VOUCHER_NOT_FOUND = 1201;
    public static final String VOUCHER_NOT_FOUND_MESSAGE = "Voucher '%s' not found.";
    public static final int VOUCHER_START_DATE_UPDATE_INVALID = 1202;
    public static final String VOUCHER_START_DATE_UPDATE_INVALID_MESSAGE = "Voucher start_date must < existing endDate.";
    public static final int VOUCHER_END_DATE_UPDATE_INVALID = 1203;
    public static final String VOUCHER_END_DATE_UPDATE_INVALID_MESSAGE = "Voucher endDate must > existing start_date.";
    public static final int VOUCHER_TYPE_INVALID = 1204;
    public static final String VOUCHER_TYPE_INVALID_MESSAGE = "Voucher type '%s' invalid! ";
    public static final int VOUCHER_NOT_BELONG_TO_AFFILIATE = 1205;
    public static final String VOUCHER_NOT_BELONG_TO_AFFILIATE_MESSAGE = "Vouchers '%s' not be long to Affiliate '%s' !";

    // AffiliateVoucher
    public static final int AFFILIATE_VOUCHER_EXISTS = 1250;
    public static final String AFFILIATE_VOUCHER_EXISTS_MESSAGE = "AffiliateVoucher Affiliate '%s' Voucher '%s' exists.";

    // Product
    public static final int PRODUCT_NOT_FOUND = 2051;
    public static final String PRODUCT_NOT_FOUND_MESSAGE = "Product '%s' not found.";
    public static final int PRODUCT_CODE_EXISTS_IN_CHANNEL = 2052;
    public static final String PRODUCT_CODE_EXISTS_IN_CHANNEL_MESSAGE = "Product code '%s' exist in channel '%s'.";

    // Notification
    public static final int NOTIFICATION_NOT_FOUND = 2101;
    public static final String NOTIFICATION_NOT_FOUND_MESSAGE = "Notification '%s' not found.";
    public static final int NOTIFICATION_STATUS_CANT_BE_MARK_AS_READ = 2102;
    public static final String NOTIFICATION_STATUS_CANT_BE_MARK_AS_READ_MESSAGE = "Notification status '%s' can't be marked as 'READ'.";
    public static final int NOTIFICATION_NOT_BELONG_TO_USER = 2103;
    public static final String NOTIFICATION_NOT_BELONG_TO_USER_MESSAGE = "Notification '%s' does not belong to user '%s'.";

    // Feedback
    public static final int FEEDBACK_NOT_FOUND = 2151;
    public static final String FEEDBACK_NOT_FOUND_MESSAGE = "Feedback '%s' not found";

    // SubsAlertConfig
    public static final int SUBS_ALERT_CONFIG_NOT_FOUND = 2201;
    public static final String SUBS_ALERT_CONFIG_NOT_FOUND_MESSAGE = "SubsAlertConfig '%s' not found";

    // SystemAlert
    public static final int SYSTEM_ALERT_NOT_FOUND = 2251;
    public static final String SYSTEM_ALERT_NOT_FOUND_MESSAGE = "SystemAlert '%s' not found";

    // Product Set
    public static final int    PRODUCT_SET_START_DATE_VS_END_DATE                 = 2301;
    public static final String PRODUCT_SET_START_DATE_VS_END_DATE_MESSAGE         = "ProductSet start_date ['%s'] can't be after end_date ['%s'].";
    public static final int    PRODUCT_SET_NOT_FOUND                              = 2302;
    public static final String PRODUCT_SET_NOT_FOUND_MESSAGE                      = "ProductSet '%s' not found.";
    public static final int PRODUCT_SET_PRODUCT_ALREADY_ASSIGNED = 2303;
    public static final String PRODUCT_SET_PRODUCT_ALREADY_ASSIGNED_MESSAGE     = "Product '%s' already assigned for ProductSet '%s'.";
    public static final String PRODUCT_SET_PRODUCT_NOT_SAME_SUBSCRIBER_MESSAGE  = "Product '%s' and ProductSet '%s' do not belong to the same Subscriber.";
    public static final int PRODUCT_SET_PRODUCT_NOT_IN = 2304;
    public static final String PRODUCT_SET_PRODUCT_NOT_IN_MESSAGE               = "ProductSet '%s' doesn't contain Product '%s'.";

    private static final long serialVersionUID = 63230199878230047L;
    private final Integer code;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.code = null;
    }

    public BusinessException(String message) {
        super(message);
        this.code = null;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getCode() {
        return code;
    }

}
