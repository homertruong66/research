package com.rms.rms.service;

import com.rms.rms.common.CustomCriteria;
import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.config.properties.SubsEmailTemplateProperties;
import com.rms.rms.common.constant.Constant;
import com.rms.rms.common.constant.SystemRole;
import com.rms.rms.common.dto.BillDto;
import com.rms.rms.common.dto.ChannelDto;
import com.rms.rms.common.dto.SubscriberDto;
import com.rms.rms.common.dto.UserDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.exception.InvalidViewModelException;
import com.rms.rms.common.util.*;
import com.rms.rms.common.view_model.SubscriberCreateModel;
import com.rms.rms.common.view_model.SubscriberSearchCriteria;
import com.rms.rms.common.view_model.SubscriberUpdateModel;
import com.rms.rms.persistence.GenericDao;
import com.rms.rms.persistence.SpecificDao;
import com.rms.rms.service.model.*;
import com.rms.rms.task_processor.TaskProcessorImportSubscribers;
import org.apache.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ExecutorService;

/**
 * homertruong
 */

@Service
@Transactional(value = "transactionManager", propagation = Propagation.SUPPORTS)
public class SubscriberServiceImpl implements SubscriberService, InitializingBean {

    private Logger logger = Logger.getLogger(SubscriberServiceImpl.class);

    @Autowired
    private SubsEmailTemplateProperties subsEmailTemplateProperties;

    @Autowired
    private ModelMapper beanMapper;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private AuthenService authenService;

    @Autowired
    @Qualifier("taskExecutorService")
    private ExecutorService taskExecutorService;

    @Autowired
    private TaskProcessorImportSubscribers tpImportSubscribers;

    @Autowired
    private GenericDao genericDao;

    @Autowired
    private SpecificDao specificDao;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private EmailService emailService;

    private String affSignUpEmailTitle;
    private String affRemindUpdateBankInfoEmailTitle;
    private String affForgotPasswordEmailTitle;
    private String customerOrderCreatedEmailTitle;
    private String affOrderCreatedEmailTitle;
    private String affOrderApprovedEmailTitle;
    private String affPaymentApprovedEmailTitle;
    private String affSignUpEmailContent;
    private String affRemindUpdateBankInfoEmailContent;
    private String affForgotPasswordEmailContent;
    private String customerOrderCreatedEmailContent;
    private String affOrderCreatedEmailContent;
    private String affOrderApprovedEmailContent;
    private String affPaymentApprovedEmailContent;

    @Override
    public void afterPropertiesSet() throws Exception {
        loadEmailTemplates();
    }
    
    @Override
    @Secured({SystemRole.ROLE_ADMIN})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SubscriberDto create(SubscriberCreateModel createModel) throws BusinessException{
        logger.info("create: " + createModel.toString());

        // process view model
        createModel.escapeHtml();
        String errors = createModel.validate();
        if (errors != null) {
            throw new InvalidViewModelException(errors);
        }
        String firstChannelDomainName = createModel.getFirstChannelDomainName();
        Subscriber newDO = beanMapper.map(createModel, Subscriber.class);

        // validate biz rules
        String packageType = createModel.getPackageType();
        if (!PackageConfig.checkType(packageType)) {
            throw new BusinessException(BusinessException.SUBSCRIBER_INVALID_PACKAGE_TYPE,
                String.format(BusinessException.SUBSCRIBER_INVALID_PACKAGE_TYPE_MESSAGE, packageType));
        }

        if (specificDao.isPropertyValueExistent(Subscriber.class, "domainName", newDO.getDomainName())) {
            throw new BusinessException(BusinessException.SUBSCRIBER_DOMAIN_NAME_EXISTS,
                    String.format(BusinessException.SUBSCRIBER_DOMAIN_NAME_EXISTS_MESSAGE, newDO.getDomainName()));
        }

        if (firstChannelDomainName != null) {
            if (specificDao.isPropertyValueExistent(Channel.class, "domainName", firstChannelDomainName)) {
                throw new BusinessException(BusinessException.CHANNEL_DOMAIN_NAME_EXISTS,
                        String.format(BusinessException.CHANNEL_DOMAIN_NAME_EXISTS_MESSAGE, firstChannelDomainName));
            }
        }

        if (specificDao.isPropertyValueExistent(Subscriber.class, "companyName", newDO.getCompanyName())) {
            throw new BusinessException(BusinessException.SUBSCRIBER_COMPANY_NAME_EXISTS,
                    String.format(BusinessException.SUBSCRIBER_COMPANY_NAME_EXISTS_MESSAGE, newDO.getCompanyName()));
        }

        Domain province = validationService.getDomain(newDO.getProvinceId(), false);
        newDO.setProvince(province);

        // do biz action
        newDO.setStatus(Subscriber.STATUS_ENABLED);
        Subscriber pdo = genericDao.create(newDO);

        // set PackageConfigApplied for Subscriber
        PackageConfig packageConfig = validationService.getPackageConfigByType(packageType, false);
        Date usageExpiredAt = MyDateUtil.convertToUTCDate(new Date());   // today in UTC
        switch (packageConfig.getUsagePeriod()) {
            case PackageConfig.USAGE_PERIOD_MONTH:
                usageExpiredAt = MyDateUtil.addDate(usageExpiredAt, 30);
                break;
            case PackageConfig.USAGE_PERIOD_YEAR:
                usageExpiredAt = MyDateUtil.addDate(usageExpiredAt, 365);
                break;
            default:
                throw new RuntimeException("Invalid usage period: " + packageConfig.getUsagePeriod());
        }
        PackageConfigApplied packageConfigApplied = beanMapper.map(packageConfig, PackageConfigApplied.class);
        packageConfigApplied.setUsageExpiredAt(usageExpiredAt);
        packageConfigApplied.setUsagePeriod(packageConfig.getUsagePeriod());
        packageConfigApplied.setSubscriber(pdo);
        packageConfigApplied.setId(null);
        packageConfigApplied.setCreatedAt(null);
        genericDao.create(packageConfigApplied);
        pdo.setPackageConfigApplied(packageConfigApplied);

        // create default SubsConfig
        SubsConfig subsConfig = new SubsConfig();
        subsConfig.setLowestPayment(0d);
        subsConfig.setAccountKeepingFee(0d);
        subsConfig.setSubscriber(pdo);
        subsConfig.setCoanType(createModel.getCoanType());
        subsConfig.setIsBitlyEnabled(false);
        genericDao.create(subsConfig);
        pdo.setSubsConfig(subsConfig);

        // create default SubsEmailConfig & SubsEmailTemplates
        SubsEmailConfig subsEmailConfig = new SubsEmailConfig();
        subsEmailConfig.setSubscriber(pdo);
        genericDao.create(subsEmailConfig);
        pdo.setSubsEmailConfig(subsEmailConfig);
        createSubEmailTemplates(pdo, subsEmailConfig.getSubsEmailTemplates());

        // create default SubsCommissionConfigs
        createDefaultSubsCommissionConfigs(pdo);

        // create default SubsPackageConfig
        SubsPackageConfig subsPackageConfig = new SubsPackageConfig();
        subsPackageConfig.setSubscriber(pdo);
        genericDao.create(subsPackageConfig);
        pdo.setSubsPackageConfig(subsPackageConfig);

        // create default IntegrationConfigs
        createDefaultIntegrationConfigs(pdo);

        // create default SubsEarnerConfig
        SubsEarnerConfig subsEarnerConfig = new SubsEarnerConfig();
        subsEarnerConfig.setSubscriber(pdo);
        genericDao.create(subsEarnerConfig);
        pdo.setSubsEarnerConfig(subsEarnerConfig);

        // create default SubsPerformerConfig
        SubsPerformerConfig subsPerformerConfig = new SubsPerformerConfig();
        subsPerformerConfig.setSubscriber(pdo);
        genericDao.create(subsPerformerConfig);
        pdo.setSubsPerformerConfig(subsPerformerConfig);

        // create default SubsRewardConfig
        SubsRewardConfig subsRewardConfig = new SubsRewardConfig();
        subsRewardConfig.setSubscriber(pdo);
        genericDao.create(subsRewardConfig);
        pdo.setSubsRewardConfig(subsRewardConfig);

        SubscriberDto subscriberDto = beanMapper.map(pdo, SubscriberDto.class);

        // create default SubsPackageStatus
        SubsPackageStatus subsPackageStatus = new SubsPackageStatus();
        subsPackageStatus.setSubscriber(pdo);
        genericDao.create(subsPackageStatus);

        //create default SubsAlertConfig
        SubsAlertConfig subsAlertConfig = new SubsAlertConfig();
        subsAlertConfig.setSubscriber(pdo);
        genericDao.create(subsAlertConfig);

        // create first Channel if provided
        if (firstChannelDomainName != null) {
            Channel newChannel = new Channel();
            newChannel.setDomainName(firstChannelDomainName);
            newChannel.setSubscriber(pdo);
            newChannel.setSubscriberId(pdo.getId());
            newChannel.setFirstName("no first name");
            newChannel.setLastName("no last name");
            Channel channelPdo = genericDao.create(newChannel);

            // create User for new Channel
            User newUser = new User();
            String channelEmail = MyTokenUtil.generateRandomToken() + pdo.getId() + "@" + Constant.RMS_DOMAIN_NAME;
            newUser.setEmail(channelEmail);
            String[] encodingParams = {"secret-key", pdo.getId()};
            String password = MyTokenUtil.encode(encodingParams);
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
            newUser.setPassword(hashedPassword);   // save hashed password to db
            newUser.setPerson(channelPdo);
            channelPdo.setUser(newUser);
            newUser.setStatus(User.STATUS_ACTIVE);
            Set<Role> roles = new HashSet<>();
            Role role = validationService.getRoleByName(SystemRole.ROLE_CHANNEL, false);
            roles.add(role);
            newUser.setRoles(roles);
            genericDao.create(newUser);
            ChannelDto channelDto = beanMapper.map(channelPdo, ChannelDto.class);
            channelDto.getUser().setPassword(password);        // return plain password

            subscriberDto.setFirstChannel(channelDto);

            // update channel count in SubsPackageStatus
            subsPackageStatus.setChannelCount(subsPackageStatus.getChannelCount() + 1);
        }

        return subscriberDto;
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN})
    public SubscriberDto get(String id) throws BusinessException {
        logger.info("get: " + id);

        // validate biz rules
        Subscriber pdo = validationService.getSubscriber(id, false);

        // do authorization
        // Admin can get any Subscriber
        // SubsAdmin can only get its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        boolean isSubsAdmin = SystemRole.hasSubsAdminRole(loggedUserDto.getRoles());
        if (isSubsAdmin) {
            authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), id);
        }
        // do biz action
        return beanMapper.map(pdo, SubscriberDto.class);
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_CHANNEL, SystemRole.ROLE_AFFILIATE, SystemRole.ROLE_SUBS_ADMIN})
    public SubscriberDto getByDomainName(String domainName) throws BusinessException {
        logger.info("getByDomainName : " + domainName);

        // validate biz rules
        Subscriber pdo = validationService.getSubscriberByDomainName(domainName,false);

        // do authorization
        String subscriberId = pdo.getId();
        UserDto loggedUserDto = authenService.getLoggedUser();
        boolean isSubAdmin = SystemRole.hasSubsAdminRole(loggedUserDto.getRoles());
        boolean isChannel = SystemRole.hasChannelRole(loggedUserDto.getRoles());
        boolean isAffiliate = SystemRole.hasAffiliateRole(loggedUserDto.getRoles());
        if (isSubAdmin) {
            authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);
        }
        else if (isChannel) {
            authorService.checkChannelAndSubscriber(loggedUserDto.getId(), subscriberId);
        }
        else if (isAffiliate) {
            authorService.checkAffiliateAndSubscriber(loggedUserDto.getId(), subscriberId);
        }

        // do biz action
        return beanMapper.map(pdo, SubscriberDto.class);
    }

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SubscriberDto renew(String id) throws BusinessException {
        logger.info("renew: " + id);

        // process view model

        // validate biz rules
        Subscriber existingDO = validationService.getSubscriber(id, false);
        PackageConfigApplied pca = validationService.getPackageConfigApplied(id, true);
        if (PackageConfig.TYPE_TRIAL.equals(pca.getType())) {
            throw new BusinessException(BusinessException.SUBSCRIBER_CAN_NOT_RENEW_TRIAL,
                                        BusinessException.SUBSCRIBER_CAN_NOT_RENEW_TRIAL_MESSAGE);
        }

        // do biz action
        switch (pca.getUsagePeriod()) {
            case PackageConfig.USAGE_PERIOD_MONTH:
                pca.setUsageExpiredAt(MyDateUtil.addDate(pca.getUsageExpiredAt(), 30));
                break;
            case PackageConfig.USAGE_PERIOD_YEAR:
                pca.setUsageExpiredAt(MyDateUtil.addDate(pca.getUsageExpiredAt(), 365));
                break;
            default:
                throw new RuntimeException("Invalid usage period: " + pca.getUsagePeriod());
        }
        genericDao.update(pca);

        // create a new Bill
        Date today = MyDateUtil.convertToUTCDate(new Date());
        Bill bill = new Bill();
        bill.setDeadline(MyDateUtil.addDate(today, 7));     // 1 week
        bill.setDescription("Bill for renewing plan: " + pca.getType());
        bill.setFee(pca.getPrice());
        bill.setStatus(Bill.STATUS_NEW);
        bill.setSubscriber(existingDO);
        genericDao.create(bill);

        SubscriberDto dto = beanMapper.map(existingDO, SubscriberDto.class);
        dto.setLatestBill(beanMapper.map(bill, BillDto.class));

        return dto;
    }

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SubscriberDto resetData(String id) throws BusinessException {
        logger.info("resetData: " + id);

        // process view model

        // validate biz rules
        Subscriber pdo = validationService.getSubscriber(id, false);

        // do authorization
          // SubsAdmin can reset data of its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        SubsAdmin loggedSubsAdmin = genericDao.read(SubsAdmin.class, loggedUserDto.getId(), false);
        authorService.checkSubsAdminAndSubscriber(loggedSubsAdmin.getId(), id);

        // do biz action: reset
        /*
            .Posts
            .Categories
            .Payments
            .Commissions {DiscountCodeApplied, SubsCommissionConfigApplied}
            .Discount Codes
            .Performers
            .OrderLines
            .Orders
            .Products
            .Customers
            .ClickInfo
            .Links
            .Shares
            .Rewards
            .Vouchers {AffiliateVouchers}
            .Agent {earning}
        */
        logger.info(specificDao.deletePostsBySubscriberId(id) + " Posts deleted! ");
        logger.info(specificDao.deleteCategoriesBySubscriberId(id) + " Categories deleted! ");
        logger.info(specificDao.deletePaymentsBySubscriberId(id) + " Payments deleted! ");
        logger.info(specificDao.deleteDiscountCodesAppliedBySubscriberId(id) + " DiscountCodesApplied deleted! ");
        logger.info(specificDao.deleteSubsCommissionConfigsAppliedBySubscriberId(id) + " SubsCommissionConfigsApplied deleted! ");
        logger.info(specificDao.deleteCommissionsBySubscriberId(id) + " Commissions deleted! ");
        logger.info(specificDao.deleteDiscountCodesBySubscriberId(id) + " Discount Codes deleted! ");
        logger.info(specificDao.deletePerformersBySubscriberId(id) + " Performers deleted! ");
        logger.info(specificDao.deleteOrderLinesSubscriberId(id) + " OrderLines deleted! ");
        logger.info(specificDao.deleteOrdersBySubscriberId(id) + " Orders deleted! ");
        logger.info(specificDao.deleteProductsBySubscriberId(id) + " Products deleted! ");
        logger.info(specificDao.deleteCustomersBySubscriberId(id) + " Customers deleted! ");
        logger.info(specificDao.deleteClickInfosBySubscriberId(id) + " ClickInfo deleted! ");
        List<String> linkIds = specificDao.getLinkIdsBySubscriberId(id);
        logger.info(specificDao.deleteLinksByLinkIds(linkIds) + " Links deleted! ");
        logger.info(specificDao.deleteSharesBySubscriberId(id) + " Shares deleted! ");
        logger.info(specificDao.deleteRewardsBySubscriberId(id) + " Rewards deleted! ");
        logger.info(specificDao.deleteAffiliateVouchersBySubscriberId(id) + " AffiliateVouchers deleted! ");
        logger.info(specificDao.deleteVouchersBySubscriberId(id) + " Vouchers deleted! ");
        logger.info(specificDao.resetAffiliateEarningsBySubscriberId(id) + " Affiliate Earnings reset! ");

        return beanMapper.map(pdo, SubscriberDto.class);

    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN, SystemRole.ROLE_AFFILIATE})
    public SearchResult<SubscriberDto> search(SearchCriteria<SubscriberSearchCriteria> vmSearchCriteria) throws BusinessException {
        logger.info("search: " + vmSearchCriteria.toString());

        // setup search criteria
        SearchCriteria<Subscriber> searchCriteria = new SearchCriteria<>();
        beanMapper.map(vmSearchCriteria, searchCriteria);       // map sort, page info
        if (vmSearchCriteria.getCriteria() != null) {
            // map search info
            Subscriber criteria = beanMapper.map(vmSearchCriteria.getCriteria(), Subscriber.class);
            searchCriteria.setCriteria(criteria);
        }
        else {
            // no search info found, use default
            searchCriteria.setCriteria(new Subscriber());
        }

        // process custom criteria
        SearchResult<Subscriber> searchResult;
        SearchResult<SubscriberDto> dtoSearchResult;
        CustomCriteria customCriteria = searchCriteria.getCustomCriteria();

        // do authorization
          // Admin can search all Subscribers
          // SubsAdmin can only search its Subscriber
          // Affiliate can only search Subscribers he works for
        UserDto loggedUserDto = authenService.getLoggedUser();
        boolean isSubsAdmin = SystemRole.hasSubsAdminRole(loggedUserDto.getRoles());
        boolean isAffiliate = SystemRole.hasAffiliateRole(loggedUserDto.getRoles());
        if (isSubsAdmin) {
            SubsAdmin loggedSubsAdmin = genericDao.read(SubsAdmin.class, loggedUserDto.getId(), false);
            customCriteria.setValue("id", loggedSubsAdmin.getSubscriberId());
        }
        else if (isAffiliate) {
            List<String> loggedSubscriberIds = specificDao.getSubscriberIdsByAffiliateId(loggedUserDto.getId());
            customCriteria.setValue("id", loggedSubscriberIds);
        }

        // do biz action
        searchResult = genericDao.search(searchCriteria);
        dtoSearchResult = createDtoSearchResult(searchResult);

        return dtoSearchResult;
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SubscriberDto update(String id, SubscriberUpdateModel updateModel) throws BusinessException {
        logger.info("update: " + id + " - " + updateModel);

        // process view model
        updateModel.escapeHtml();
        String errors = updateModel.validate();
        if (errors != null) {
            throw new InvalidViewModelException(errors);
        }
        Subscriber detachedDO = beanMapper.map(updateModel, Subscriber.class);

        // validate biz rules
        Subscriber existingDO = validationService.getSubscriber(id, true);
        if (detachedDO.getDomainName() != null && !existingDO.getDomainName().equals(detachedDO.getDomainName())) {
            if (specificDao.isPropertyValueExistent(Subscriber.class, "domainName", detachedDO.getDomainName())) {
                throw new BusinessException(BusinessException.SUBSCRIBER_DOMAIN_NAME_EXISTS,
                        String.format(BusinessException.SUBSCRIBER_DOMAIN_NAME_EXISTS_MESSAGE, detachedDO.getDomainName()));
            }
        }
        if (detachedDO.getCompanyName() != null && !existingDO.getCompanyName().equals(detachedDO.getCompanyName())) {
            if (specificDao.isPropertyValueExistent(Subscriber.class, "companyName", detachedDO.getCompanyName())) {
                throw new BusinessException(BusinessException.SUBSCRIBER_COMPANY_NAME_EXISTS,
                        String.format(BusinessException.SUBSCRIBER_COMPANY_NAME_EXISTS_MESSAGE, detachedDO.getCompanyName()));
            }
        }

        if (detachedDO.getProvinceId() != null && !existingDO.getProvinceId().equals(detachedDO.getProvinceId())) {
            Domain province = validationService.getDomain(detachedDO.getProvinceId(), false);
            detachedDO.setProvince(province);
        }

        // map input to existingDO
        MyBeanUtil.mapIgnoreNullProps(existingDO, detachedDO);

        // do biz action
        genericDao.update(existingDO);

        return beanMapper.map(existingDO, SubscriberDto.class);
    }

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SubscriberDto upgrade(String id, String packageType) throws BusinessException {
        logger.info("upgrade: " + id + " - packageType: " + packageType);

        // process view model
          // check packageType
        if (!PackageConfig.checkType(packageType)) {
            throw new BusinessException(BusinessException.SUBSCRIBER_INVALID_PACKAGE_TYPE,
                    String.format(BusinessException.SUBSCRIBER_INVALID_PACKAGE_TYPE_MESSAGE, packageType));
        }

        // validate biz rules
          // check if upgrading is allowed
        Subscriber existingDO = validationService.getSubscriber(id, false);
        PackageConfigApplied pca = validationService.getPackageConfigApplied(id, true);
        if (!PackageConfig.checkUpgradeType(pca.getType(), packageType)) {
            throw new BusinessException(BusinessException.SUBSCRIBER_INVALID_UPGRADE_PACKAGE_TYPE,
                    String.format(BusinessException.SUBSCRIBER_INVALID_UPGRADE_PACKAGE_TYPE_MESSAGE, pca.getType()));
        }
        String currentType = pca.getType();

        // do authorization
            // SubsAdmin can only upgrade package for its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), id);

        // calculate remaining money
        Date today = MyDateUtil.convertToUTCDate(new Date());
        double remainingMoney;
        double remainingDays = (pca.getUsageExpiredAt().getTime() - today.getTime()) / (1000 * 3600 * 24);
        switch (pca.getUsagePeriod()) {
            case PackageConfig.USAGE_PERIOD_MONTH:
                remainingMoney = remainingDays * pca.getPrice().doubleValue() / 30;
                break;
            case PackageConfig.USAGE_PERIOD_YEAR:
                remainingMoney = remainingDays * pca.getPrice().doubleValue() / 365;
                break;
            default:
                throw new RuntimeException("Invalid current usage period: " + pca.getUsagePeriod());
        }
        if (remainingMoney < 0) {   // expired more than 1 day
            remainingMoney = 0;
        }

        // map metrics from PackageConfig to PackageConfigApplied
        PackageConfig packageConfig = validationService.getPackageConfigByType(packageType, false);
        String currentPackageConfigApplied = pca.getId();
        beanMapper.map(packageConfig, pca);
        pca.setId(currentPackageConfigApplied);

        // calculate usageExpiredAt
        Date usageExpiredAt = MyDateUtil.convertToUTCDate(new Date());   // today in UTC
        switch (packageConfig.getUsagePeriod()) {
            case PackageConfig.USAGE_PERIOD_MONTH:
                usageExpiredAt = MyDateUtil.addDate(usageExpiredAt, 30);
                break;
            case PackageConfig.USAGE_PERIOD_YEAR:
                usageExpiredAt = MyDateUtil.addDate(usageExpiredAt, 365);
                break;
            default:
                throw new RuntimeException("Invalid input usage period: " + packageConfig.getUsagePeriod());
        }
        pca.setUsageExpiredAt(usageExpiredAt);

        // do biz action
        genericDao.update(pca);

        // upgrade SubsCommissionConfigs for upgraded PackageConfig
        upgradeSubsCommissionConfigs(id, currentType, packageType);

        // create a new Bill
        Bill bill = new Bill();
        bill.setDeadline(MyDateUtil.addDate(today, 7));     // 1 week
        bill.setDescription("Bill for upgrading plan to: " + pca.getType());
        bill.setFee(MyNumberUtil.roundDouble(packageConfig.getPrice() - remainingMoney, 2));
        bill.setStatus(Bill.STATUS_NEW);
        bill.setSubscriber(existingDO);
        genericDao.create(bill);

        SubscriberDto dto = beanMapper.map(existingDO, SubscriberDto.class);
        dto.setLatestBill(beanMapper.map(bill, BillDto.class));

        return dto;
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN})
    public void upload(MultipartFile file) throws BusinessException {
        logger.info("upload with file: " + file.getName());

        // import Subscribers in background mode
        UserDto loggedUserDto = authenService.getLoggedUser();
        Runnable task = () -> tpImportSubscribers.process(file, loggedUserDto);
        taskExecutorService.submit(task);
    }


    // Utilities
    private void createDefaultIntegrationConfigs(Subscriber pdo) {
        PackageConfigApplied pca = pdo.getPackageConfigApplied();

        // create default SubsInfusionConfig
        if (pca.getHasInfusion()) {
            SubsInfusionConfig subsInfusionConfig = new SubsInfusionConfig();
            subsInfusionConfig.setSubscriber(pdo);
            genericDao.create(subsInfusionConfig);
            pdo.setSubsInfusionConfig(subsInfusionConfig);
        }

        // create default SubsGetflyConfig
        if (pca.getHasGetfly()) {
            SubsGetflyConfig subsGetflyConfig = new SubsGetflyConfig();
            subsGetflyConfig.setSubscriber(pdo);
            genericDao.create(subsGetflyConfig);
            pdo.setSubsGetflyConfig(subsGetflyConfig);
        }

        // create default SubsGetResponseConfig
        if (pca.getHasGetResponse()) {
            SubsGetResponseConfig subsGetResponseConfig = new SubsGetResponseConfig();
            subsGetResponseConfig.setSubscriber(pdo);
            genericDao.create(subsGetResponseConfig);
            pdo.setSubsGetResponseConfig(subsGetResponseConfig);
        }
    }

    private void createDefaultSubsCommissionConfigs(Subscriber pdo) {
        PackageConfigApplied pca = pdo.getPackageConfigApplied();

        // CFE
        if (pca.getHasCommissionCfe()) {
            SubsCommissionConfig cfeSubsCommissionConfig = new SubsCommissionConfig();
            cfeSubsCommissionConfig.setCommission(0.0);
            cfeSubsCommissionConfig.setIsEnabled(Boolean.FALSE);
            cfeSubsCommissionConfig.setType(SubsCommissionConfig.TYPE_CFE);
            cfeSubsCommissionConfig.setSubscriberId(pdo.getId());
            genericDao.create(cfeSubsCommissionConfig);
        }

        // COPPR
        if (pca.getHasCommissionCoppr()) {
            SubsCommissionConfig copprSubsCommissionConfig = new SubsCommissionConfig();
            copprSubsCommissionConfig.setCommission(0.0);
            copprSubsCommissionConfig.setIsEnabled(Boolean.TRUE);
            copprSubsCommissionConfig.setType(SubsCommissionConfig.TYPE_COPPR);
            copprSubsCommissionConfig.setSubscriberId(pdo.getId());
            genericDao.create(copprSubsCommissionConfig);
        }

        // COPQ
        if (pca.getHasCommissionCopq()) {
            SubsCommissionConfig copqSubsCommissionConfig = new SubsCommissionConfig();
            copqSubsCommissionConfig.setCommission(0.0);
            copqSubsCommissionConfig.setIsEnabled(Boolean.TRUE);
            copqSubsCommissionConfig.setType(SubsCommissionConfig.TYPE_COPQ);
            copqSubsCommissionConfig.setSubscriberId(pdo.getId());
            genericDao.create(copqSubsCommissionConfig);
        }

        // CAD
        if (pca.getHasCommissionCad()) {
            SubsCommissionConfig cadSubsCommissionConfig = new SubsCommissionConfig();
            cadSubsCommissionConfig.setCommission(0.0);
            cadSubsCommissionConfig.setIsEnabled(Boolean.TRUE);
            cadSubsCommissionConfig.setType(SubsCommissionConfig.TYPE_CAD);
            cadSubsCommissionConfig.setSubscriberId(pdo.getId());
            genericDao.create(cadSubsCommissionConfig);
        }

        // COAN
        if (pca.getHasCommissionCoan()) {
            SubsCommissionConfig coanSubsCommissionConfig = new SubsCommissionConfig();
            coanSubsCommissionConfig.setCommission(0.0);
            coanSubsCommissionConfig.setIsEnabled(Boolean.TRUE);
            coanSubsCommissionConfig.setType(SubsCommissionConfig.TYPE_COAN);
            coanSubsCommissionConfig.setSubscriberId(pdo.getId());
            genericDao.create(coanSubsCommissionConfig);
        }

        // COSAL
        // TODO: enable this config when RMS support COAN type=LEVEL
//        if (SubsConfig.TYPE_COAN_LEVEL.equals(pdo.getSubsConfig().getCoanType())) {
//            if (pca.getHasCommissionCosal()) {
//                SubsCommissionConfig cosalSubsCommissionConfig = new SubsCommissionConfig();
//                cosalSubsCommissionConfig.setCommission(0.0);
//                cosalSubsCommissionConfig.setIsEnabled(Boolean.FALSE);
//                cosalSubsCommissionConfig.setType(SubsCommissionConfig.TYPE_COSAL);
//                cosalSubsCommissionConfig.setSubscriberId(pdo.getId());
//                genericDao.create(cosalSubsCommissionConfig);
//            }
//        }

        // COOV
        if (pca.getHasCommissionCoov()) {
            SubsCommissionConfig coovSubsCommissionConfig = new SubsCommissionConfig();
            coovSubsCommissionConfig.setCommission(0.0);
            coovSubsCommissionConfig.setIsEnabled(Boolean.TRUE);
            coovSubsCommissionConfig.setType(SubsCommissionConfig.TYPE_COOV);
            coovSubsCommissionConfig.setSubscriberId(pdo.getId());
            genericDao.create(coovSubsCommissionConfig);
        }

        // COPG
        if (pca.getHasCommissionCopg()) {
            SubsCommissionConfig copgSubsCommissionConfig = new SubsCommissionConfig();
            copgSubsCommissionConfig.setCommission(0.0);
            copgSubsCommissionConfig.setIsEnabled(Boolean.TRUE);
            copgSubsCommissionConfig.setType(SubsCommissionConfig.TYPE_COPG);
            copgSubsCommissionConfig.setSubscriberId(pdo.getId());
            genericDao.create(copgSubsCommissionConfig);
        }

        // COASV
        if (pca.getHasCommissionCoasv()) {
            SubsCommissionConfig coasvSubsCommissionConfig = new SubsCommissionConfig();
            coasvSubsCommissionConfig.setCommission(0.0);
            coasvSubsCommissionConfig.setIsEnabled(Boolean.TRUE);
            coasvSubsCommissionConfig.setType(SubsCommissionConfig.TYPE_COASV);
            coasvSubsCommissionConfig.setSubscriberId(pdo.getId());
            genericDao.create(coasvSubsCommissionConfig);
        }

        // COPS
        if (pca.getHasCommissionCops()) {
            SubsCommissionConfig copsSubsCommissionConfig = new SubsCommissionConfig();
            copsSubsCommissionConfig.setCommission(0.0);
            copsSubsCommissionConfig.setIsEnabled(Boolean.TRUE);
            copsSubsCommissionConfig.setType(SubsCommissionConfig.TYPE_COPS);
            copsSubsCommissionConfig.setSubscriberId(pdo.getId());
            genericDao.create(copsSubsCommissionConfig);
        }
    }

    private void upgradeSubsCommissionConfigs(String id, String currentType, String upgradeType) throws BusinessException {
        PackageConfig currentPC = validationService.getPackageConfigByType(currentType, false);
        PackageConfig upgradePC = validationService.getPackageConfigByType(upgradeType, false);
        Subscriber pdo = validationService.getSubscriber(id, false);

        // CFE
        if (!currentPC.getHasCommissionCfe() && upgradePC.getHasCommissionCfe()) {
            SubsCommissionConfig cfeSubsCommissionConfig = new SubsCommissionConfig();
            cfeSubsCommissionConfig.setCommission(0.0);
            cfeSubsCommissionConfig.setIsEnabled(Boolean.FALSE);
            cfeSubsCommissionConfig.setType(SubsCommissionConfig.TYPE_CFE);
            cfeSubsCommissionConfig.setSubscriberId(id);
            genericDao.create(cfeSubsCommissionConfig);
        }

        // COPPR
        if (!currentPC.getHasCommissionCoppr() && upgradePC.getHasCommissionCoppr()) {
            SubsCommissionConfig copprSubsCommissionConfig = new SubsCommissionConfig();
            copprSubsCommissionConfig.setCommission(0.0);
            copprSubsCommissionConfig.setIsEnabled(Boolean.TRUE);
            copprSubsCommissionConfig.setType(SubsCommissionConfig.TYPE_COPPR);
            copprSubsCommissionConfig.setSubscriberId(id);
            genericDao.create(copprSubsCommissionConfig);
        }

        // COPQ
        if (!currentPC.getHasCommissionCopq() && upgradePC.getHasCommissionCopq()) {
            SubsCommissionConfig copqSubsCommissionConfig = new SubsCommissionConfig();
            copqSubsCommissionConfig.setCommission(0.0);
            copqSubsCommissionConfig.setIsEnabled(Boolean.TRUE);
            copqSubsCommissionConfig.setType(SubsCommissionConfig.TYPE_COPQ);
            copqSubsCommissionConfig.setSubscriberId(id);
            genericDao.create(copqSubsCommissionConfig);
        }

        // COOV
        if (!currentPC.getHasCommissionCoov() && upgradePC.getHasCommissionCoov()) {
            SubsCommissionConfig coovSubsCommissionConfig = new SubsCommissionConfig();
            coovSubsCommissionConfig.setCommission(0.0);
            coovSubsCommissionConfig.setIsEnabled(Boolean.TRUE);
            coovSubsCommissionConfig.setType(SubsCommissionConfig.TYPE_COOV);
            coovSubsCommissionConfig.setSubscriberId(id);
            genericDao.create(coovSubsCommissionConfig);
        }

        // COPG
        if (!currentPC.getHasCommissionCopg() && upgradePC.getHasCommissionCopg()) {
            SubsCommissionConfig copgSubsCommissionConfig = new SubsCommissionConfig();
            copgSubsCommissionConfig.setCommission(0.0);
            copgSubsCommissionConfig.setIsEnabled(Boolean.TRUE);
            copgSubsCommissionConfig.setType(SubsCommissionConfig.TYPE_COPG);
            copgSubsCommissionConfig.setSubscriberId(id);
            genericDao.create(copgSubsCommissionConfig);
        }

        // COASV
        if (!currentPC.getHasCommissionCoasv() && upgradePC.getHasCommissionCoasv()) {
            SubsCommissionConfig coasvSubsCommissionConfig = new SubsCommissionConfig();
            coasvSubsCommissionConfig.setCommission(0.0);
            coasvSubsCommissionConfig.setIsEnabled(Boolean.TRUE);
            coasvSubsCommissionConfig.setType(SubsCommissionConfig.TYPE_COASV);
            coasvSubsCommissionConfig.setSubscriberId(id);
            genericDao.create(coasvSubsCommissionConfig);
        }

        // COPS
        if (!currentPC.getHasCommissionCops() && upgradePC.getHasCommissionCops()) {
            SubsCommissionConfig copsSubsCommissionConfig = new SubsCommissionConfig();
            copsSubsCommissionConfig.setCommission(0.0);
            copsSubsCommissionConfig.setIsEnabled(Boolean.TRUE);
            copsSubsCommissionConfig.setType(SubsCommissionConfig.TYPE_COPS);
            copsSubsCommissionConfig.setSubscriberId(id);
            genericDao.create(copsSubsCommissionConfig);
        }

        // SubsInfusionConfig
        if (!currentPC.getHasInfusion() && upgradePC.getHasInfusion()) {
            SubsInfusionConfig subsInfusionConfig = new SubsInfusionConfig();
            subsInfusionConfig.setSubscriber(pdo);
            genericDao.create(subsInfusionConfig);
        }

        // SubsGetflyConfig
        if (!currentPC.getHasGetfly() && upgradePC.getHasGetfly()) {
            SubsGetflyConfig subsGetflyConfig = new SubsGetflyConfig();
            subsGetflyConfig.setSubscriber(pdo);
            genericDao.create(subsGetflyConfig);
        }

        // SubsGetResponseConfig
        if (!currentPC.getHasGetResponse() && upgradePC.getHasGetResponse()) {
            SubsGetResponseConfig subsGetResponseConfig = new SubsGetResponseConfig();
            subsGetResponseConfig.setSubscriber(pdo);
            genericDao.create(subsGetResponseConfig);
        }
    }

    private SearchResult<SubscriberDto> createDtoSearchResult(SearchResult<Subscriber> searchResult) {
        SearchResult<SubscriberDto> result = new SearchResult<>();
        beanMapper.map(searchResult, result);
        List<SubscriberDto> dtos = new ArrayList<>();
        for (Subscriber pdo : searchResult.getList()) {
            dtos.add(beanMapper.map(pdo, SubscriberDto.class));
        }
        result.setList(dtos);

        return result;
    }

    private SubsEmailTemplate createSubsEmailTemplate(String type, String title, String content, Subscriber subscriber) {
        SubsEmailTemplate subsEmailTemplate = new SubsEmailTemplate();
        subsEmailTemplate.setType(type);
        subsEmailTemplate.setIsEnabled(Boolean.TRUE);
        subsEmailTemplate.setTitle(title);
        content=content.replace("[name]",subscriber.getCompanyName());
        content=content.replace("[telephone]",String.join(" - ",subscriber.getPhone(),subscriber.getMobilePhone()));
        content=content.replace("[email]",subscriber.getEmail());
        content=content.replace("[address]",subscriber.getAddress());
        subsEmailTemplate.setContent(content);
        subsEmailTemplate.setSubsEmailConfigId(subscriber.getId());

        return genericDao.create(subsEmailTemplate);
    }

    private void createSubEmailTemplates (Subscriber pdo, Set<SubsEmailTemplate> templates) {
        templates.add(createSubsEmailTemplate(SubsEmailTemplate.TYPE_AFF_SIGN_UP,
                affSignUpEmailTitle, affSignUpEmailContent, pdo));
        templates.add(createSubsEmailTemplate(SubsEmailTemplate.TYPE_AFF_REMIND_UPDATE_BANK_INFO,
                affRemindUpdateBankInfoEmailTitle, affRemindUpdateBankInfoEmailContent, pdo));
        templates.add(createSubsEmailTemplate(SubsEmailTemplate.TYPE_AFF_FORGOT_PASSWORD,
                affForgotPasswordEmailTitle, affForgotPasswordEmailContent, pdo));
        templates.add(createSubsEmailTemplate(SubsEmailTemplate.TYPE_CUSTOMER_ORDER_CREATED,
                customerOrderCreatedEmailTitle, customerOrderCreatedEmailContent, pdo));
        templates.add(createSubsEmailTemplate(SubsEmailTemplate.TYPE_AFF_ORDER_CREATED,
                affOrderCreatedEmailTitle, affOrderCreatedEmailContent, pdo));
        templates.add(createSubsEmailTemplate(SubsEmailTemplate.TYPE_AFF_ORDER_APPROVED,
                affOrderApprovedEmailTitle, affOrderApprovedEmailContent, pdo));
        templates.add(createSubsEmailTemplate(SubsEmailTemplate.TYPE_AFF_PAYMENT_APPROVED,
                affPaymentApprovedEmailTitle, affPaymentApprovedEmailContent, pdo));
    }

    private void loadEmailTemplates() throws IOException {
        InputStream in;
        String emailTemplatePath = "/email_template/";
        String bodyTag = "{{body}}";

        in = getClass().getResourceAsStream(emailTemplatePath + subsEmailTemplateProperties.getHeaderFooterContent());
        String headerFooterEmailContent = MyFileUtil.readText(in);

        // Affiliate
        in = getClass().getResourceAsStream(emailTemplatePath + subsEmailTemplateProperties.getAffSignUpContent());
        affSignUpEmailContent = MyFileUtil.readText(in);
        affSignUpEmailContent = headerFooterEmailContent.replace(bodyTag, affSignUpEmailContent);
        affSignUpEmailTitle = subsEmailTemplateProperties.getAffSignUpTitle();

        in = getClass().getResourceAsStream(emailTemplatePath + subsEmailTemplateProperties.getAffRemindUpdateBankInfoContent());
        affRemindUpdateBankInfoEmailContent = MyFileUtil.readText(in);
        affRemindUpdateBankInfoEmailContent = headerFooterEmailContent.replace(bodyTag, affRemindUpdateBankInfoEmailContent);
        affRemindUpdateBankInfoEmailTitle = subsEmailTemplateProperties.getAffRemindUpdateBankInfoTitle();

        in = getClass().getResourceAsStream(emailTemplatePath + subsEmailTemplateProperties.getAffForgotPasswordContent());
        affForgotPasswordEmailContent = MyFileUtil.readText(in);
        affForgotPasswordEmailContent = headerFooterEmailContent.replace(bodyTag, affForgotPasswordEmailContent);
        affForgotPasswordEmailTitle = subsEmailTemplateProperties.getAffForgotPasswordTitle();

        // Order
        in = getClass().getResourceAsStream(emailTemplatePath + subsEmailTemplateProperties.getCustomerOrderCreatedContent());
        customerOrderCreatedEmailContent = MyFileUtil.readText(in);
        customerOrderCreatedEmailContent = headerFooterEmailContent.replace(bodyTag, customerOrderCreatedEmailContent);
        customerOrderCreatedEmailTitle = subsEmailTemplateProperties.getCustomerOrderCreatedTitle();

        in = getClass().getResourceAsStream(emailTemplatePath + subsEmailTemplateProperties.getAffOrderCreatedContent());
        affOrderCreatedEmailContent = MyFileUtil.readText(in);
        affOrderCreatedEmailContent = headerFooterEmailContent.replace(bodyTag, affOrderCreatedEmailContent);
        affOrderCreatedEmailTitle = subsEmailTemplateProperties.getAffOrderCreatedTitle();

        in = getClass().getResourceAsStream(emailTemplatePath + subsEmailTemplateProperties.getAffOrderApprovedContent());
        affOrderApprovedEmailContent = MyFileUtil.readText(in);
        affOrderApprovedEmailContent = headerFooterEmailContent.replace(bodyTag, affOrderApprovedEmailContent);
        affOrderApprovedEmailTitle = subsEmailTemplateProperties.getAffOrderApprovedTitle();

        // Payment

        in = getClass().getResourceAsStream(emailTemplatePath + subsEmailTemplateProperties.getAffPaymentApprovedContent());
        affPaymentApprovedEmailContent = MyFileUtil.readText(in);
        affPaymentApprovedEmailContent = headerFooterEmailContent.replace(bodyTag, affPaymentApprovedEmailContent);
        affPaymentApprovedEmailTitle = subsEmailTemplateProperties.getAffPaymentApprovedTitle();

    }
}
