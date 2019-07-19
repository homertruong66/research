package com.rms.rms.service;

import com.rms.rms.common.CustomCriteria;
import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.constant.Discriminator;
import com.rms.rms.common.constant.SystemRole;
import com.rms.rms.common.dto.*;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.exception.InvalidViewModelException;
import com.rms.rms.common.util.MyBeanUtil;
import com.rms.rms.common.util.MyDateUtil;
import com.rms.rms.common.util.MyEmailUtil;
import com.rms.rms.common.util.MyTokenUtil;
import com.rms.rms.common.view_model.*;
import com.rms.rms.integration.GetResponseClient;
import com.rms.rms.integration.exception.GetResponseIntegrationException;
import com.rms.rms.integration.model.GetResponseContact;
import com.rms.rms.persistence.GenericDao;
import com.rms.rms.persistence.SpecificDao;
import com.rms.rms.service.model.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.Normalizer;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * homertruong
 */

@Service
@Transactional(value = "transactionManager", propagation = Propagation.SUPPORTS)
public class PersonServiceImpl implements PersonService {

    private Logger logger = Logger.getLogger(PersonServiceImpl.class);

    @Autowired
    private ModelMapper beanMapper;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private AuthenService authenService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SubscriberService subscriberService;

    @Autowired
    private AgentService agentService;

    @Autowired
    private CommissionService commissionService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private GenericDao genericDao;

    @Autowired
    @Qualifier("personDao")
    private GenericDao personDao;

    @Autowired
    private SpecificDao specificDao;

    @Autowired
    private GetResponseClient getResponseClient;

    @Override
    @Secured(SystemRole.ROLE_SUBS_ADMIN)
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SubsAdminDto addRoleForSubsAdmin(String subsAdminId, String roleName) throws BusinessException {
        logger.info("addRoleForSubsAdmin: " + roleName + " - subsAdminId: " + subsAdminId);

        // process view model
        if (StringUtils.isBlank(roleName)) {
            String errors = "'role_name' can't be empty! &&";
            throw new InvalidViewModelException(errors);
        }

        if (!roleName.equals(SystemRole.ROLE_ACCOUNTANT)) {
            throw new BusinessException(BusinessException.ROLE_INVALID,
                    String.format(BusinessException.ROLE_INVALID_MESSAGE,  roleName));
        }

        // validate biz rules
        SubsAdmin pdo = validationService.getSubsAdmin(subsAdminId, true);
        Role role = validationService.getRoleByName(roleName, false);
        User user = pdo.getUser();
        Set<Role> roles = user.getRoles();
        if (roles.contains(role)) {
            throw new BusinessException(BusinessException.SUBS_ADMIN_ROLE_ALREADY_EXIST,
                    String.format(BusinessException.SUBS_ADMIN_ROLE_ALREADY_EXIST_MESSAGE,  subsAdminId, roleName));
        }

        // do authorization
            // SubsAdmin can only add role to SubsAdmins of the same Subscriber
        UserDto loggedUser = authenService.getLoggedUser();
        String subscriberId = pdo.getSubscriberId();
        authorService.checkSubsAdminAndSubscriber(loggedUser.getId(), subscriberId);

        // add user roles
        roles.add(role);
        genericDao.update(user);
        return beanMapper.map(pdo, SubsAdminDto.class);
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN, SystemRole.ROLE_AFFILIATE})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public AffiliateDto assignAffiliate(String affiliateId, AffiliateAssignModel assignModel) throws BusinessException {
        logger.info("assignAffiliate: " + affiliateId + " - " + assignModel.toString());

        // process view model
        assignModel.escapeHtml();
        String errors = assignModel.validate();
        if (errors != null) {
            throw new InvalidViewModelException(errors);
        }
        String subscriberId = assignModel.getSubscriberId();
        String referrer = assignModel.getReferrer();

        // validate biz rules
        Affiliate pdo = validationService.getAffiliate(affiliateId, false);
        Subscriber subscriber = validationService.getSubscriber(subscriberId, false);
        UserDto loggedUserDto = authenService.getLoggedUser();
        boolean isSubsAdmin = SystemRole.hasSubsAdminRole(loggedUserDto.getRoles());
        boolean isAffiliate = SystemRole.hasAffiliateRole(loggedUserDto.getRoles());
        if (referrer != null && !isAffiliate){
            // the referrer valid?
            if (!specificDao.isPropertyValueExistent(Affiliate.class, "nickname", referrer)) {
                throw new BusinessException(BusinessException.AFFILIATE_REFERRER_NOT_FOUND,
                        String.format(BusinessException.AFFILIATE_REFERRER_NOT_FOUND_MESSAGE, referrer));
            }
            Map<String, Object> referrerParams = new HashMap<>();
            referrerParams.put("nickname", referrer);
            Affiliate referringAffiliate = personDao.readUnique(Affiliate.class, referrerParams, false);

            // the referrer belongs to the Subscriber?
            Map<String, Object> agentParams = new HashMap<>();
            agentParams.put("affiliateId", referringAffiliate.getId());
            agentParams.put("subscriberId", subscriberId);
            Agent referringAgent = genericDao.readUnique(Agent.class, agentParams, false);
            if (referringAgent == null) {
                throw new BusinessException(BusinessException.AFFILIATE_REFERRER_NOT_BELONG_TO_SUBSCRIBER,
                        String.format(BusinessException.AFFILIATE_REFERRER_NOT_BELONG_TO_SUBSCRIBER_MESSAGE, referrer, subscriberId));
            }
        }

        // do authorization
          // Admin can assign any Affiliate to any Subscriber
          // SubsAdmin can only assign the Affiliate to its Subscriber
          // Affiliate (referrer) can only assign the Affiliate to one of its Subscribers
        if (isSubsAdmin) {
            authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);
        }
        else if (isAffiliate) {
            authorService.checkAffiliateAndSubscriber(loggedUserDto.getId(), subscriberId);
            Affiliate referringAffiliate = genericDao.read(Affiliate.class, loggedUserDto.getId(), false);
            referrer = referringAffiliate.getNickname();
        }

        // do biz action
        if (isSubscriberInAgents(subscriber.getId(), pdo.getAgents())) {
            throw new BusinessException(BusinessException.AFFILIATE_ALREADY_ASSIGNED_TO_SUBSCRIBER,
                    String.format(BusinessException.AFFILIATE_ALREADY_ASSIGNED_TO_SUBSCRIBER_MESSAGE,
                                  affiliateId, subscriber.getId()));
        }

        // assign Affiliate to input Subscriber
        Agent agent = new Agent();
        agent.setAffiliate(pdo);
        agent.setSubscriber(subscriber);
        agent.setCreatedBy(loggedUserDto.getId());
        agent.setReferrer(referrer);
        agent.setInheritor(referrer);
        agent.setEarning(0.0);
        personDao.create(agent);
        pdo.getAgents().add(agent);

        return beanMapper.map(pdo, AffiliateDto.class);
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void calculateAffiliates() throws BusinessException {
        logger.info("calculateAffiliates: ");

        List<Agent> agents = genericDao.readAll(Agent.class);
        for(Agent agent : agents) {
            if (agent.getReferrer() != null) {
                Affiliate referringAffiliate = validationService.getAffiliateByNickname(agent.getReferrer(), false);
                increaseNumberOfAffiliatesInNetworkOfReferrers(referringAffiliate.getId(), agent.getSubscriberId());
            }
        }
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN, SystemRole.ROLE_AFFILIATE, SystemRole.ROLE_CHANNEL})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public PersonDto changePassword(ChangePasswordModel model) throws BusinessException {
        logger.info("changePassword: " + model);

        // process view model
        model.escapeHtml();
        String errors = model.validate();
        if (errors != null) {
            throw new InvalidViewModelException(errors);
        }
        String oldPassword = model.getOldPassword();
        String newPassword = model.getNewPassword();

        // validate biz rules
        UserDto loggedUserDto = authenService.getLoggedUser();
        User pdo = validationService.getUser(loggedUserDto.getId(), true);
        if (!BCrypt.checkpw(oldPassword, pdo.getPassword())) {
            throw new BusinessException(BusinessException.USER_WRONG_PASSWORD,
                    BusinessException.USER_WRONG_PASSWORD_MESSAGE);
        }

        // do authorization

        // do biz action
        pdo.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt(12)));
        genericDao.update(pdo);

        return beanMapper.map(pdo.getPerson(), PersonDto.class);
    }

    @Override
    @Secured({SystemRole.ROLE_AFFILIATE})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String confirmPhone(String phone) throws BusinessException {
        logger.info("confirmPhone: " + phone);

        // do biz action
        UserDto loggedUserDto = authenService.getLoggedUser();
        Affiliate pdo = genericDao.read(Affiliate.class, loggedUserDto.getId(), true);
        pdo.setIsPhoneVerified(Boolean.TRUE);
        if (phone != null) {
            pdo.setPhone(phone);
        }
        genericDao.update(pdo);

        return "OK";
    }

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public AffiliateDto convertTo(AffiliateConvertModel convertModel) throws BusinessException {
        logger.info("convertTo: " + convertModel.toString());

        // process view model
        convertModel.escapeHtml();
        String errors = convertModel.validate();
        if (errors != null) {
            throw new InvalidViewModelException(errors);
        }
        String orderId = convertModel.getOrderId();
        String email = convertModel.getEmail();
        String nickname = convertModel.getNickname();

        // validate biz rules
        UserDto loggedUserDto = authenService.getLoggedUser();
        Order order = validationService.getOrder(orderId, false);
        Customer customer = order.getCustomer();
        Affiliate affiliate = order.getAffiliate();
        Subscriber subscriber = customer.getSubscriber();
        String subscriberId = subscriber.getId();
        String referrer = affiliate != null ? affiliate.getNickname() : null;
        if (StringUtils.isBlank(email)) {
            email = customer.getEmail();
        }
        if(StringUtils.isBlank(nickname)) {
            nickname = convertFullnameToNickname(customer.getFullname());
        }

        // check Subscriber usage limit on Affiliates
        PackageConfigApplied pca = validationService.getPackageConfigApplied(subscriberId, false);
        SubsPackageConfig spc = validationService.getSubsPackageConfig(subscriberId, false);
        int maxAffiliateCount = pca.getAffiliateCount() + spc.getAffiliateCount();
        SubsPackageStatus subsPackageStatus = validationService.getSubsPackageStatus(subscriberId, true);
        int affiliateCount = subsPackageStatus.getAffiliateCount();
        if (affiliateCount >= maxAffiliateCount) {
            throw new BusinessException(BusinessException.SUBSCRIBER_AFFILIATE_COUNT_REACH_LIMIT,
                    String.format(BusinessException.SUBSCRIBER_AFFILIATE_COUNT_REACH_LIMIT_MESSAGE, maxAffiliateCount));
        }
        // check Subscriber usage expiration
        Date today = MyDateUtil.convertToUTCDate(new Date());
        if (pca.getUsageExpiredAt().before(today)) {
            throw new BusinessException(BusinessException.SUBSCRIBER_USAGE_EXPIRED,
                    String.format(BusinessException.SUBSCRIBER_USAGE_EXPIRED_MESSAGE, pca.getUsageExpiredAt()));
        }

        subsPackageStatus.setAffiliateCount(affiliateCount + 1);
        genericDao.update(subsPackageStatus);

        // do authorization
            // SubsAdmin can only convert Customer to Affiliate for its Subscriber
        authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);

        // do biz action
        User newUser = new User();
        newUser.setEmail(email);

        Affiliate pdo;
        Map<String, Object> userParams = new HashMap<>();
        userParams.put("email", email);
        User existingUser = personDao.readUnique(User.class, userParams, false);
        if (existingUser == null) {     // Affiliate does not exist, create a new Affiliate
            Affiliate newDO = new Affiliate();
            newDO.setAddress(customer.getAddress());
            newDO.setPhone(customer.getPhone());
            newDO.setNickname(nickname);
            String fullname[] = customer.getFullname().split(" ");
            newDO.setFirstName(fullname[0]);
            newDO.setLastName(fullname[fullname.length - 1]);

            // check nickname
            if (specificDao.isPropertyValueExistent(Affiliate.class, "nickname", newDO.getNickname())) {
                throw new BusinessException(BusinessException.AFFILIATE_NICKNAME_ALREADY_EXIST,
                        String.format(BusinessException.AFFILIATE_NICKNAME_ALREADY_EXIST_MESSAGE, newDO.getNickname()));
            }

            // create an Affiliate
            newDO.setIsPhoneVerified(Boolean.FALSE);
            pdo = personDao.create(newDO);

            // create a User for the Affiliate
            Set<Role> roles = new HashSet<>();
            Map<String, Object> params = new HashMap<>();
            params.put("name", SystemRole.ROLE_AFFILIATE);
            Role role = genericDao.readUnique(Role.class, params, false);
            roles.add(role);
            newUser.setRoles(roles);
            // generate activation code by encoding email & random token
            String randomToken = MyTokenUtil.generateRandomToken();
            newUser.setActivationCode(generateActivationToken(newUser.getEmail(), randomToken));
            // use random token as password
            newUser.setPassword(BCrypt.hashpw(randomToken, BCrypt.gensalt(12)));
            newUser.setPerson(newDO);
            newUser.setStatus(User.STATUS_INACTIVE);
            genericDao.create(newUser);
        }
        else {  // Affiliate exists OR email belongs to an existing Admin/SubsAdmin/Channel
            pdo = personDao.read(Affiliate.class, existingUser.getId(), true);
            if (pdo == null) {  // email belongs to an existing Admin/SubsAdmin
                throw new BusinessException(BusinessException.USER_EMAIL_EXISTS,
                        String.format(BusinessException.USER_EMAIL_EXISTS_MESSAGE, newUser.getEmail()));
            }

            if (isSubscriberInAgents(subscriber.getId(), pdo.getAgents())) {
                throw new BusinessException(BusinessException.AFFILIATE_ALREADY_ASSIGNED_TO_SUBSCRIBER,
                        String.format(BusinessException.AFFILIATE_ALREADY_ASSIGNED_TO_SUBSCRIBER_MESSAGE,
                                pdo.getId(), subscriber.getCompanyName()));
            }

            // if Affiliate already belongs to other Subscribers, return confirm message
            if (pdo.getAgents() != null && pdo.getAgents().size() > 0) {
                AffiliateDto dto = new AffiliateDto();
                dto.setId(pdo.getId());
                dto.setReferrer(referrer);
                dto.setConfirmMessage(AffiliateDto.CONFIRM_MESSAGE_FOR_THE_OTHERS);
                dto.setSubscriberId(subscriber.getId());
                return dto;
            }
        }

        // increase number of affiliates of Subscriber
        specificDao.increaseNumberOfAffiliatesOfSubscriber(subscriberId);

        // increase number of affiliates in network of referrers
        if (referrer != null) {
            increaseNumberOfAffiliatesInNetworkOfReferrers(affiliate.getId(), subscriberId);
        }

        // assign Affiliate to input Subscriber
        Agent agent = new Agent();
        agent.setAffiliate(pdo);
        agent.setSubscriber(subscriber);
        agent.setInheritor(referrer);
        agent.setReferrer(referrer);
        agent.setCreatedBy(loggedUserDto.getId());
        agent.setEarning(0.0);
        personDao.create(agent);
        pdo.getAgents().add(agent);

        pdo.setUser(existingUser != null ? existingUser : newUser);
        AffiliateDto dto = beanMapper.map(pdo, AffiliateDto.class);
        dto.setActivationCode(newUser.getActivationCode());    // used to send email if new Affiliate

        order.setIsCustomerConverted(Boolean.TRUE);
        genericDao.update(order);

        return dto;
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN, SystemRole.ROLE_AFFILIATE})
    public Long countAffiliates() throws BusinessException {
        logger.info("countAffiliates: ");

        // do authorization
          // Admin can count all Affiliates
          // SubsAdmin can only count Affiliates of its Subscriber
          // Affiliate can only count Affiliate network under it
        UserDto loggedUserDto = authenService.getLoggedUser();
        boolean isSubsAdmin = SystemRole.hasSubsAdminRole(loggedUserDto.getRoles());
        boolean isAffiliate = SystemRole.hasAffiliateRole(loggedUserDto.getRoles());
        if (isSubsAdmin) {
            SubsAdmin loggedSubsAdmin = genericDao.read(SubsAdmin.class, loggedUserDto.getId(), false);
            return specificDao.countAffiliatesBySubscriberId(loggedSubsAdmin.getSubscriberId());
        }
        else if (isAffiliate) {
            Affiliate loggedAffiliate = genericDao.read(Affiliate.class, loggedUserDto.getId(), false);
            return specificDao.countAffiliatesByReferrer(loggedAffiliate.getNickname());
        }

        return Long.valueOf(genericDao.count(Affiliate.class));
    }

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN})
    public Long countAffiliatesByAffiliateIdAndSubscriberId(String affiliateId, String subscriberId) throws BusinessException {
        logger.info("countAffiliatesByAffiliateIdAndSubscriberId: " + affiliateId + ", subscriberId: " + subscriberId);

        // validate biz rules
        Map<String, Object> agentParams = new HashMap<>();
        agentParams.put("affiliateId", affiliateId);
        agentParams.put("subscriberId", subscriberId);
        Agent agent = genericDao.readUnique(Agent.class, agentParams, false);

        // do authorization
          // SubsAdmin can only count Affiliates under the Affiliate whose Subscribers must contain its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        authorService.checkSubsAdminAndAffiliate(loggedUserDto.getId(), affiliateId);

        return agent.getNumberOfAffiliatesInNetwork();
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN})
    public Long countAffiliatesBySubscriberId(String subscriberId) throws BusinessException {
        logger.info("countAffiliatesBySubscriberId: " + subscriberId);

        // validate biz rules
        validationService.getSubscriber(subscriberId, false);

        // do authorization
          // Admin can count all Affiliates of the Subscriber
          // SubsAdmin can only count Affiliates of its Subscriber who must be the Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        boolean isSubsAdmin = SystemRole.hasSubsAdminRole(loggedUserDto.getRoles());
        if (isSubsAdmin) {
            authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);
        }

        return specificDao.countAffiliatesBySubscriberId(subscriberId);
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public AdminDto createAdmin(AdminCreateModel createModel) throws BusinessException {
        logger.info("createAdmin: " + createModel.toString());

        // process view model
        createModel.escapeHtml();
        String errors = createModel.validate();
        if (errors != null) {
            throw new InvalidViewModelException(errors);
        }
        Admin newDO = beanMapper.map(createModel, Admin.class);
        User newUser = beanMapper.map(createModel, User.class);

        // validate biz rules
        if (specificDao.isPropertyValueExistent(User.class, "email", newUser.getEmail())) {
            throw new BusinessException(BusinessException.USER_EMAIL_EXISTS,
                    String.format(BusinessException.USER_EMAIL_EXISTS_MESSAGE, newUser.getEmail()));
        }

        // do biz action
        Admin pdo = personDao.create(newDO);
        pdo.setUser(newUser);
        String hashedPassword = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt(12));
        newUser.setPassword(hashedPassword);
        newUser.setPerson(pdo);
        newUser.setStatus(User.STATUS_ACTIVE);
        Set<Role> syncedRoles = syncRoles(newUser.getRoles(), null);
        if (syncedRoles.size() == 0 || !isRoleInSet(SystemRole.ROLE_ADMIN, syncedRoles)) {
            Map<String, Object> params = new HashMap<>();
            params.put("name", SystemRole.ROLE_ADMIN);
            Role adminRole = genericDao.readUnique(Role.class, params, false);
            syncedRoles.add(adminRole);
        }
        newUser.setRoles(syncedRoles);
        genericDao.create(newUser);

        return beanMapper.map(pdo, AdminDto.class);
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_CHANNEL, SystemRole.ROLE_SUBS_ADMIN, SystemRole.ROLE_AFFILIATE})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public AffiliateDto createAffiliate(AffiliateCreateModel createModel) throws BusinessException {
        logger.info("createAffiliate: " + createModel.toString());

        // process view model
        createModel.escapeHtml();
        String errors = createModel.validate();
        if (errors != null) {
            throw new InvalidViewModelException(errors);
        }
        Affiliate newDO = beanMapper.map(createModel, Affiliate.class);
        User newUser = beanMapper.map(createModel, User.class);

        // validate biz rules
        UserDto loggedUserDto = authenService.getLoggedUser();
        boolean isSubsAdmin = SystemRole.hasSubsAdminRole(loggedUserDto.getRoles());
        boolean isChannel = SystemRole.hasChannelRole(loggedUserDto.getRoles());
        boolean isAffiliate = SystemRole.hasAffiliateRole(loggedUserDto.getRoles());
        String subscriberDomainName = createModel.getSubscriberDomainName();
        Subscriber subscriber = validationService.getSubscriberByDomainName(subscriberDomainName, false);
        String subscriberId = subscriber.getId();

          // check Subscriber usage limit on Affiliates
        PackageConfigApplied pca = validationService.getPackageConfigApplied(subscriberId, false);
        SubsPackageConfig spc = validationService.getSubsPackageConfig(subscriberId, false);
        int maxAffiliateCount = pca.getAffiliateCount().intValue() + spc.getAffiliateCount().intValue();
        SubsPackageStatus subsPackageStatus = validationService.getSubsPackageStatus(subscriberId, true);
        int affiliateCount = subsPackageStatus.getAffiliateCount().intValue();
        if (affiliateCount >= maxAffiliateCount) {
            throw new BusinessException(BusinessException.SUBSCRIBER_AFFILIATE_COUNT_REACH_LIMIT,
                String.format(BusinessException.SUBSCRIBER_AFFILIATE_COUNT_REACH_LIMIT_MESSAGE, maxAffiliateCount));
        }
          // check Subscriber usage expiration
        Date today = MyDateUtil.convertToUTCDate(new Date());
        if (pca.getUsageExpiredAt().before(today)) {
            throw new BusinessException(BusinessException.SUBSCRIBER_USAGE_EXPIRED,
                    String.format(BusinessException.SUBSCRIBER_USAGE_EXPIRED_MESSAGE, pca.getUsageExpiredAt()));
        }

        subsPackageStatus.setAffiliateCount(Integer.valueOf(affiliateCount + 1));
        genericDao.update(subsPackageStatus);

        String referrer = createModel.getReferrer();
        if (referrer != null && !isAffiliate){
            // the referrer valid?
            if (!specificDao.isPropertyValueExistent(Affiliate.class, "nickname", referrer)) {
                throw new BusinessException(BusinessException.AFFILIATE_REFERRER_NOT_FOUND,
                        String.format(BusinessException.AFFILIATE_REFERRER_NOT_FOUND_MESSAGE, referrer));
            }
            Map<String, Object> referrerParams = new HashMap<>();
            referrerParams.put("nickname", referrer);
            Affiliate referringAffiliate = personDao.readUnique(Affiliate.class, referrerParams, false);

            // the referrer belongs to the Subscriber?
            Map<String, Object> agentParams = new HashMap<>();
            agentParams.put("affiliateId", referringAffiliate.getId());
            agentParams.put("subscriberId", subscriberId);
            Agent referringAgent = genericDao.readUnique(Agent.class, agentParams, false);
            if (referringAgent == null) {
                throw new BusinessException(BusinessException.AFFILIATE_REFERRER_NOT_BELONG_TO_SUBSCRIBER,
                        String.format(BusinessException.AFFILIATE_REFERRER_NOT_BELONG_TO_SUBSCRIBER_MESSAGE, referrer, subscriberId));
            }
        }

        // do authorization
          // Admin can create all Affiliate
          // SubsAdmin and Channel can only create Affiliate for its Subscriber
          // Affiliate can only create Affiliate for its Subscribers
        if (isSubsAdmin) {
            authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);
        }
        else if (isChannel) {
            authorService.checkChannelAndSubscriber(loggedUserDto.getId(), subscriberId);
        }
        else if (isAffiliate) {
            authorService.checkAffiliateAndSubscriber(loggedUserDto.getId(), subscriberId);
            Affiliate referringAffiliate = genericDao.read(Affiliate.class, loggedUserDto.getId(), false);
            referrer = referringAffiliate.getNickname();
        }

        // do biz action
        Affiliate pdo;
        Map<String, Object> userParams = new HashMap<>();
        userParams.put("email", newUser.getEmail());
        User existingUser = personDao.readUnique(User.class, userParams, false);
        if (existingUser == null) {     // Affiliate does not exist, create a new Affiliate
            // check nickname
            if (specificDao.isPropertyValueExistent(Affiliate.class, "nickname", newDO.getNickname())) {
                throw new BusinessException(BusinessException.AFFILIATE_NICKNAME_ALREADY_EXIST,
                        String.format(BusinessException.AFFILIATE_NICKNAME_ALREADY_EXIST_MESSAGE, newDO.getNickname()));
            }

            // create an Affiliate
            newDO.setIsPhoneVerified(Boolean.FALSE);
            pdo = personDao.create(newDO);

            // create a User for the Affiliate
            Set<Role> roles = new HashSet<>();
            Map<String, Object> params = new HashMap<>();
            params.put("name", SystemRole.ROLE_AFFILIATE);
            Role role = genericDao.readUnique(Role.class, params, false);
            roles.add(role);
            newUser.setRoles(roles);
            // generate activation code by encoding email & random token
            String randomToken = MyTokenUtil.generateRandomToken();
            newUser.setActivationCode(generateActivationToken(newUser.getEmail(), randomToken));
            // if password is not provided, use random token as password
            if (StringUtils.isBlank(newUser.getPassword())) {
                newUser.setPassword(BCrypt.hashpw(randomToken, BCrypt.gensalt(12)));
            }
            else {
                newUser.setPassword(BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt(12)));
            }
            newUser.setPerson(newDO);
            newUser.setStatus(User.STATUS_INACTIVE);
            genericDao.create(newUser);
        }
        else {  // Affiliate exists OR email belongs to an existing Admin/SubsAdmin/Channel
            pdo = personDao.read(Affiliate.class, existingUser.getId(), true);
            if (pdo == null) {  // email belongs to an existing Admin/SubsAdmin
                throw new BusinessException(BusinessException.USER_EMAIL_EXISTS,
                        String.format(BusinessException.USER_EMAIL_EXISTS_MESSAGE, newUser.getEmail()));
            }

            if (isSubscriberInAgents(subscriber.getId(), pdo.getAgents())) {
                throw new BusinessException(BusinessException.AFFILIATE_ALREADY_ASSIGNED_TO_SUBSCRIBER,
                        String.format(BusinessException.AFFILIATE_ALREADY_ASSIGNED_TO_SUBSCRIBER_MESSAGE,
                                      pdo.getId(), subscriberDomainName));
            }

            // if Affiliate already belongs to other Subscribers, return confirm message
            if (!isChannel && pdo.getAgents().size() > 0) {
                AffiliateDto dto = new AffiliateDto();
                dto.setId(pdo.getId());
                dto.setReferrer(referrer);
                dto.setConfirmMessage(AffiliateDto.CONFIRM_MESSAGE_FOR_THE_OTHERS);
                dto.setSubscriberId(subscriber.getId());
                return dto;
            }
        }

        // increase number of affiliates of Subscriber
        specificDao.increaseNumberOfAffiliatesOfSubscriber(subscriberId);

        // increase number of affiliates in network of referrers
        if (referrer != null) {
            Affiliate referringAffiliate = validationService.getAffiliateByNickname(referrer, false);
            increaseNumberOfAffiliatesInNetworkOfReferrers(referringAffiliate.getId(), subscriberId);
        }

        // assign Affiliate to input Subscriber
        Agent agent = new Agent();
        agent.setAffiliate(pdo);
        agent.setSubscriber(subscriber);
        agent.setInheritor(referrer);
        agent.setReferrer(referrer);
        agent.setCreatedBy(loggedUserDto.getId());
        agent.setEarning(0.0);
        personDao.create(agent);
        pdo.getAgents().add(agent);

        pdo.setUser(existingUser != null ? existingUser : newUser);
        AffiliateDto dto = beanMapper.map(pdo, AffiliateDto.class);
        dto.setActivationCode(newUser.getActivationCode());    // used to process email if new Affiliate
        dto.setSubscriberId(subscriberId); // used to process push data to GetResponse for Affiliate

        return dto;
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SubsAdminDto createSubsAdmin(String subscriberId, SubsAdminCreateModel createModel) throws BusinessException {
        logger.info("createSubsAdmin: subscriberId: " + subscriberId + " - " + createModel);

        // process view model
        createModel.escapeHtml();
        String errors = createModel.validate();
        if (errors != null) {
            throw new InvalidViewModelException(errors);
        }
        SubsAdmin newDO = beanMapper.map(createModel, SubsAdmin.class);
        User newUser = beanMapper.map(createModel, User.class);

        // validate biz rules
        Subscriber subscriber = validationService.getSubscriber(subscriberId, false);
        if (specificDao.isPropertyValueExistent(User.class, "email", newUser.getEmail())) {
            throw new BusinessException(BusinessException.USER_EMAIL_EXISTS,
                    String.format(BusinessException.USER_EMAIL_EXISTS_MESSAGE, newUser.getEmail()));
        }

        // do authorization
          // Admin can create any SubsAdmin
          // SubsAdmin can only create SubsAdmin of its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        boolean isSubsAdmin = SystemRole.hasSubsAdminRole(loggedUserDto.getRoles());
        if (isSubsAdmin) {
            authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);
        }

        // Set to root SubsAdmin if is first SubsAdmin
        Boolean isRootSubsAdmin = specificDao.countSubsAdminsBySubscriberId(subscriberId) == 0;
        if(isRootSubsAdmin) {
            newDO.setIsRootSubsAdmin(true);
        } else {
            newDO.setIsRootSubsAdmin(false);
        }

        // do biz action
        newDO.setSubscriber(subscriber);
        newDO.setSubscriberId(subscriberId);
        SubsAdmin pdo = personDao.create(newDO);

        // create an account for the SubsAdmin
        Set<Role> roles = new HashSet<>();
        Role subsAdminRole = validationService.getRoleByName(SystemRole.ROLE_SUBS_ADMIN, false);
        roles.add(subsAdminRole);
        newUser.setRoles(roles);

        String hashedPassword = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt(12));
        newUser.setPassword(hashedPassword);
        newUser.setStatus(User.STATUS_ACTIVE);
        newUser.setPerson(pdo);
        pdo.setUser(newUser);
        genericDao.create(newUser);

        return beanMapper.map(pdo, SubsAdminDto.class);
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN})
    //@CacheEvict(value = "rms", key = "'Admin-'.concat(#id)")
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteAdmin(String adminId) throws BusinessException {
        logger.info("deleteAdmin: " + adminId);

        // validate biz rules
        Admin pdo = validationService.getAdmin(adminId, true);

        // do biz action
        personDao.delete(pdo);
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteSubsAdmin(String subsAdminId) throws BusinessException {
        logger.info("deleteSubsAdmin: " + subsAdminId);

        // validate biz rules
        SubsAdmin pdo = validationService.getSubsAdmin(subsAdminId, true);

        // do authorization
          // Admin can delete any SubsAdmin
          // SubsAdmin can only delete SubsAdmin of its own Subscriber
          // SubsAdmin can not delete RootSubsAdmin
        UserDto loggedUserDto = authenService.getLoggedUser();
        String subscriberId = pdo.getSubscriberId();
        boolean isSubsAdmin = SystemRole.hasSubsAdminRole(loggedUserDto.getRoles());
        if (isSubsAdmin) {
            authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);

            if (pdo.getIsRootSubsAdmin() == true) {
                throw new BusinessException(BusinessException.CAN_NOT_DELETE_ROOT_SUBS_ADMIN,
                        String.format(BusinessException.CAN_NOT_DELETE_ROOT_SUBS_ADMIN_MESSAGE,  pdo.getId()));
            }
        }

        personDao.delete(pdo);
    }

    @Override
    @Secured(SystemRole.ROLE_SUBS_ADMIN)
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SubsAdminDto removeRoleForSubsAdmin(String subsAdminId, String roleName) throws BusinessException {
        logger.info("removeRoleForSubsAdmin: " + roleName + " - subsAdminId: " + subsAdminId);

        // process view model
        if (StringUtils.isBlank(roleName)) {
            String errors = "'role_name' can't be empty! &&";
            throw new InvalidViewModelException(errors);
        }

        if (!roleName.equals(SystemRole.ROLE_ACCOUNTANT)) {
            throw new BusinessException(BusinessException.ROLE_INVALID,
                    String.format(BusinessException.ROLE_INVALID_MESSAGE,  roleName));
        }

        // validate biz rules
        SubsAdmin pdo = validationService.getSubsAdmin(subsAdminId, false);
        Role role = validationService.getRoleByName(roleName, false);
        User user = pdo.getUser();
        Set<Role> roles = user.getRoles();
        if (!roles.contains(role)) {
            throw new BusinessException(BusinessException.SUBS_ADMIN_ROLE_NOT_FOUND,
                    String.format(BusinessException.SUBS_ADMIN_ROLE_NOT_FOUND_MESSAGE,  roleName, subsAdminId));
        }

        // do authorization
          // SubsAdmin can only remove Role of the SubsAdmin whose Subscriber is its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        String subscriberId = pdo.getSubscriberId();
        authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);

        // do biz action
        roles.remove(role);
        genericDao.update(user);

        return beanMapper.map(pdo,SubsAdminDto.class);
    }

    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN})
    public byte[] exportAffiliates(Date from, Date to) throws BusinessException {
        logger.info("exportAffiliates: " + " - from: " + from + " -> to: " + to);

        // create excel work book
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Affiliates");

        // create headers
        Row row = sheet.createRow(0);
        Cell cell1 = row.createCell(0);
        cell1.setCellValue("Họ và Tên lót");
        Cell cell2 = row.createCell(1);
        cell2.setCellValue("Tên");
        Cell cell3 = row.createCell(2);
        cell3.setCellValue("Số điện thoại");
        Cell cell4 = row.createCell(3);
        cell4.setCellValue("Email");
        Cell cell5 = row.createCell(4);
        cell5.setCellValue("Thời gian tạo");
        Cell cell6 = row.createCell(5);
        cell6.setCellValue("Trạng thái");

        // add Affiliates page by page
        SearchCriteria<AffiliateSearchCriteria> searchCriteria = new SearchCriteria<>();
        int pageSize = 1000;
        int startRow = 1;
        searchCriteria.setCriteria(new AffiliateSearchCriteria());
        searchCriteria.setPageIndex(1);
        searchCriteria.setPageSize(pageSize);

        CustomCriteria customCriteria = new CustomCriteria();
        customCriteria.setValue("createdAt>=", from);
        customCriteria.setValue("createdAt<=", to);

        // render first page
        searchCriteria.setCustomCriteria(customCriteria);
        SearchResult<AffiliateDto> searchResult = this.searchAffiliates(searchCriteria);    // authorization done in this
        logger.info("Export Page 1: " + searchResult.getList().size() + " items");
        this.renderAffiliatesToExcelSheet(searchResult.getList(), sheet, startRow);
        // render the rest
        int numberOfPages = searchResult.getNumOfPages();
        for (int pageIndex = 2; pageIndex <= numberOfPages; pageIndex++) {
            searchCriteria.setPageIndex(pageIndex);
            searchResult = this.searchAffiliates(searchCriteria);
            logger.info("Export Page " + pageIndex + ": " + searchResult.getList().size() + " items");
            startRow += pageSize;
            this.renderAffiliatesToExcelSheet(searchResult.getList(), sheet, startRow);
        }

        // write excel work book to byte array
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            workbook.write(bos);
        }
        catch (Exception ex) {
            throw new RuntimeException("Can not write data to output stream");
        }
        finally {
            try {
                workbook.close();
                bos.close();
            } catch (IOException e) {
            }
        }
        byte[] contents = bos.toByteArray();

        return contents;
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN})
    // @Cacheable(value = "rms", key = "'Admin-'.concat(#id)") Tip: store object as json string to avoid serialization
    public AdminDto getAdmin(String adminId) throws BusinessException {
        logger.info("getAdmin: " + adminId);

        // validate biz rules
        Admin pdo = validationService.getAdmin(adminId, false);

        return beanMapper.map(pdo, AdminDto.class);
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_AFFILIATE, SystemRole.ROLE_SUBS_ADMIN})
    public AffiliateDto getAffiliate(String affiliateId) throws BusinessException {
        logger.info("getAffiliate: " + affiliateId);

        // validate biz rules
        Affiliate pdo = validationService.getAffiliate(affiliateId, false);

        // do authorization
          // Admin can get any Affiliate
          // SubsAdmin can only get the Affiliate of its Subscriber
          // Affiliate can only get the Affiliate created by it
        UserDto loggedUserDto = authenService.getLoggedUser();
        boolean isSubsAdmin = SystemRole.hasSubsAdminRole(loggedUserDto.getRoles());
        boolean isAffiliate = SystemRole.hasAffiliateRole(loggedUserDto.getRoles());
        if (isSubsAdmin) {
            authorService.checkSubsAdminAndAffiliate(loggedUserDto.getId(), affiliateId);
        }
        else if (isAffiliate && !loggedUserDto.getId().equals(affiliateId)) {
            if (!specificDao.isAffiliateCreatedBy(affiliateId, loggedUserDto.getId())) {
                throw new BusinessException(BusinessException.AFFILIATE_LOGGED_AFFILIATE_NOT_CREATOR,
                    String.format(BusinessException.AFFILIATE_LOGGED_AFFILIATE_NOT_CREATOR_MESSAGE,
                                  loggedUserDto.getId(), affiliateId));
            }
        }

        AffiliateDto affiliateDto = beanMapper.map(pdo, AffiliateDto.class);
        Set<SubscriberDto> subscribers = pdo.getAgents()
                .stream()
                .map(agent -> beanMapper.map(agent.getSubscriber(), SubscriberDto.class))
                .collect(Collectors.toSet());

        affiliateDto.getSubscribers().addAll(subscribers);

        return affiliateDto;
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_CHANNEL, SystemRole.ROLE_SUBS_ADMIN, SystemRole.ROLE_AFFILIATE})
    public AffiliateDto getAffiliateByNickname(String nickname) throws BusinessException {
        logger.info("getAffiliateByNickname: " + nickname);

        // validate biz rules
        Affiliate pdo = validationService.getAffiliateByNickname(nickname, false);

        // do authorization
          // Admin can get any Affiliate
          // SubsAdmin can only get the Affiliate of its Subscriber
          // Affiliate can only get the Affiliate created by it
        UserDto loggedUserDto = authenService.getLoggedUser();
        boolean isSubsAdmin = SystemRole.hasSubsAdminRole(loggedUserDto.getRoles());
        boolean isChannel = SystemRole.hasChannelRole(loggedUserDto.getRoles());
        boolean isAffiliate = SystemRole.hasAffiliateRole(loggedUserDto.getRoles());
        if (isSubsAdmin) {
            authorService.checkSubsAdminAndAffiliate(loggedUserDto.getId(), pdo.getId());
        }
        else if (isChannel) {
            authorService.checkChannelAndAffiliate(loggedUserDto.getId(), pdo.getId());
        }
        else if (isAffiliate && !loggedUserDto.getId().equals(pdo.getId())) {
            if (!specificDao.isAffiliateCreatedBy(pdo.getId(), loggedUserDto.getId())) {
                throw new BusinessException(BusinessException.AFFILIATE_LOGGED_AFFILIATE_NOT_CREATOR,
                        String.format(BusinessException.AFFILIATE_LOGGED_AFFILIATE_NOT_CREATOR_MESSAGE,
                                loggedUserDto.getId(), pdo.getId()));
            }
        }

        AffiliateDto affiliateDto = beanMapper.map(pdo, AffiliateDto.class);
        Set<SubscriberDto> subscribers = pdo.getAgents()
                .stream()
                .map(agent -> beanMapper.map(agent.getSubscriber(), SubscriberDto.class))
                .collect(Collectors.toSet());

        affiliateDto.getSubscribers().addAll(subscribers);

        return affiliateDto;
    }

    @Override@Secured({SystemRole.ROLE_AFFILIATE, SystemRole.ROLE_SUBS_ADMIN})
    public List<AffiliateDto> getAffiliatesInNetwork(AffiliateViewNetworkModel viewNetworkModel) throws BusinessException {
        logger.info("getAffiliatesInNetwork: " + viewNetworkModel);

        List<AffiliateDto> result = new ArrayList<>();

        // process view model
        String errors = viewNetworkModel.validate();
        if (errors != null) {
            throw new InvalidViewModelException(errors);
        }

        // validate biz rules

        // do authorization
          // SubsAdmin can only get Affiliates referred by input referrer of its Subscriber
          // Affiliate can only get Affiliates referred by input referrer of input Subscriber
        List<Affiliate> affiliates = new ArrayList<>();
        String subscriberId = viewNetworkModel.getSubscriberId();
        String referrer = viewNetworkModel.getReferrer();
        UserDto loggedUserDto = authenService.getLoggedUser();
        boolean isSubsAdmin = SystemRole.hasSubsAdminRole(loggedUserDto.getRoles());
        boolean isAffiliate = SystemRole.hasAffiliateRole(loggedUserDto.getRoles());
        if (isSubsAdmin) {
            SubsAdmin loggedSubsAdmin = genericDao.read(SubsAdmin.class, loggedUserDto.getId(), false);
            subscriberId = loggedSubsAdmin.getSubscriberId();
            if (StringUtils.isBlank(referrer)) {
                // get root Affiliates of N networks
                affiliates = specificDao.getAffiliatesBySubscriberAndReferrer(subscriberId, null);
            }
            else {
                affiliates = specificDao.getAffiliatesBySubscriberAndReferrer(subscriberId, referrer);
            }
        }
        else if (isAffiliate) {
            if (StringUtils.isBlank(viewNetworkModel.getSubscriberId())) {
                throw new InvalidViewModelException("'subscriber_id' can't be empty!");
            }

            Affiliate loggedAffiliate = genericDao.read(Affiliate.class, loggedUserDto.getId(), false);
            subscriberId = viewNetworkModel.getSubscriberId();
            authorService.checkAffiliateAndSubscriber(loggedUserDto.getId(), subscriberId);
            if (StringUtils.isBlank(referrer)) {
                // get loggedAffiliate as root Affiliate in its network
                affiliates.add(loggedAffiliate);
            }
            else {
                affiliates = specificDao.getAffiliatesBySubscriberAndReferrer(subscriberId, referrer);
            }
        }

        // do biz action
        for (Affiliate pdo : affiliates) {
            AffiliateDto affiliateDto = beanMapper.map(pdo, AffiliateDto.class);
            Agent agent = validationService.getAgent(pdo.getId(), subscriberId, false);
            affiliateDto.setNumberOfAffiliatesInNetwork(agent.getNumberOfAffiliatesInNetwork());
            result.add(affiliateDto);
        }

        return result;
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_AFFILIATE, SystemRole.ROLE_SUBS_ADMIN})
    public PersonDto getProfile(String id) throws BusinessException {
        logger.info("getProfile: " + id);

        // validate biz rules
        Person pdo = validationService.getPerson(id, true);
        PersonDto dto = beanMapper.map(pdo, PersonDto.class);

        // do authorization
            // User can only get profile of itself
        UserDto loggedUserDto = authenService.getLoggedUser();
        if (!loggedUserDto.getId().equals(id)) {
            throw new BusinessException(BusinessException.USER_DATA_AUTHORIZATION,
                    String.format(BusinessException.USER_DATA_AUTHORIZATION_MESSAGE, id));
        }

        // Add BankDto to PersonDto if isAffiliate
        boolean isAffiliate = SystemRole.hasAffiliateRole(loggedUserDto.getRoles());
        if (isAffiliate) {
            Bank bankPdo = genericDao.read(Bank.class, loggedUserDto.getId(), false);
            if (bankPdo != null) {
                dto.setBank(beanMapper.map(bankPdo, BankDto.class));
            }
        }

        return dto;
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN})
    public SubsAdminDto getSubsAdmin(String subsAdminId) throws BusinessException {
        logger.info("getSubsAdmin: " + subsAdminId);

        // validate biz rules
        SubsAdmin pdo = validationService.getSubsAdmin(subsAdminId, false);

        // do authorization
          // Admin can get any SubsAdmin
          // SubsAdmin can only get SubsAdmin of its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        String subscriberId = pdo.getSubscriberId();
        boolean isSubsAdmin = SystemRole.hasSubsAdminRole(loggedUserDto.getRoles());
        if (isSubsAdmin) {
            authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);
        }

        return beanMapper.map(pdo, SubsAdminDto.class);
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN,SystemRole.ROLE_CHANNEL, SystemRole.ROLE_SUBS_ADMIN, SystemRole.ROLE_AFFILIATE})
    public List<String> getSubsAdminIdsBySubscriberId (String subscriberId) throws BusinessException {
        // do authorization
        // Admin can get SubsAdmins of any Subscriber
        // SubsAdmin can only get SubsAdmins of its Subscriber
        // Affiliate can only get SubsAdmins of Subscriber he/she works for
        UserDto loggedUserDto = authenService.getLoggedUser();
        boolean isSubsAdmin = SystemRole.hasSubsAdminRole(loggedUserDto.getRoles());
        boolean isChannel = SystemRole.hasChannelRole(loggedUserDto.getRoles());
        boolean isAffiliate = SystemRole.hasAffiliateRole(loggedUserDto.getRoles());
        if (isSubsAdmin) {
            authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);
        }
        else if (isChannel) {
            authorService.checkChannelAndSubscriber(loggedUserDto.getId(), subscriberId);
        }
        else if (isAffiliate) {
            authorService.checkAffiliateAndSubscriber(loggedUserDto.getId(), subscriberId);
        }

        return specificDao.getSubsAdminIdsBySubscriberId(subscriberId);
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_CHANNEL, SystemRole.ROLE_SUBS_ADMIN, SystemRole.ROLE_AFFILIATE})
    public List<String> getSubsAdminEmailsBySubscriberId(String subscriberId) throws BusinessException{
        // do authorization
          // Admin can get SubsAdmins of any Subscriber
          // SubsAdmin can only get SubsAdmins of its Subscriber
          // Affiliate can only get SubsAdmins of Subscriber he/she works for
        UserDto loggedUserDto = authenService.getLoggedUser();
        boolean isSubsAdmin = SystemRole.hasSubsAdminRole(loggedUserDto.getRoles());
        boolean isChannel = SystemRole.hasChannelRole(loggedUserDto.getRoles());
        boolean isAffiliate = SystemRole.hasAffiliateRole(loggedUserDto.getRoles());
        if (isSubsAdmin) {
            authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);
        }
        else if (isChannel) {
            authorService.checkChannelAndSubscriber(loggedUserDto.getId(), subscriberId);
        }
        else if (isAffiliate) {
            authorService.checkAffiliateAndSubscriber(loggedUserDto.getId(), subscriberId);
        }

        return specificDao.getSubsAdminEmailsBySubscriberId(subscriberId);
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_CHANNEL, SystemRole.ROLE_SUBS_ADMIN, SystemRole.ROLE_AFFILIATE})
    public List<String> getSubsAdminAccountantIdsBySubscriberId(String subscriberId) throws BusinessException {
        logger.info("getSubsAdminAccountantIdsBySubscriberId: " + subscriberId);

        // process view model

        // validate biz rule
        validationService.getSubscriber(subscriberId, false);

        // do authorization
        // Admin can get SubsAdmins of any Subscriber
        // SubsAdmin can only get SubsAdmins of its Subscriber
        // Affiliate can only get SubsAdmins of Subscriber he/she works for
        UserDto loggedUserDto = authenService.getLoggedUser();
        boolean isSubsAdmin = SystemRole.hasSubsAdminRole(loggedUserDto.getRoles());
        boolean isChannel = SystemRole.hasChannelRole(loggedUserDto.getRoles());
        boolean isAffiliate = SystemRole.hasAffiliateRole(loggedUserDto.getRoles());
        if (isSubsAdmin) {
            authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);
        }
        else if (isChannel) {
            authorService.checkChannelAndSubscriber(loggedUserDto.getId(), subscriberId);
        }
        else if (isAffiliate) {
            authorService.checkAffiliateAndSubscriber(loggedUserDto.getId(), subscriberId);
        }

        // do biz action
        return specificDao.getSubsAdminAccountantIdsBySubscriberId(subscriberId);
    }

    @Override
    public PersonDto getByEmail(String email) throws BusinessException {
        logger.info("getByEmail: " + email);

        Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        User user = genericDao.readUnique(User.class, params, false);
        if (user == null) {
            return null;
        }

        return beanMapper.map(user.getPerson(), PersonDto.class);
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SubsAdminDto grantRootForSubsAdmin(String subsAdminId) throws BusinessException {
        logger.info("grantRootForSubsAdmin: " + subsAdminId);

        // validate biz rule
        SubsAdmin pdo = validationService.getSubsAdmin(subsAdminId, true);
        Boolean isRootSubsAdmin = pdo.getIsRootSubsAdmin() == true;
        if (isRootSubsAdmin) {
            throw new BusinessException(BusinessException.SUBS_ADMIN_IS_ALREADY_ROOT,
                    String.format(BusinessException.SUBS_ADMIN_IS_ALREADY_ROOT_MESSAGE,  pdo.getId()));
        }

        // do biz action
        pdo.setIsRootSubsAdmin(true);
        genericDao.update(pdo);

        return beanMapper.map(pdo, SubsAdminDto.class);
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_CHANNEL, SystemRole.ROLE_AFFILIATE, SystemRole.ROLE_SUBS_ADMIN})
    public boolean isAffiliateExistent(String email) throws BusinessException {
        logger.info("isAffiliateExistent: " + email);

        Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        User user = genericDao.readUnique(User.class, params, false);
        if (user == null) {
            return false;
        }

        Affiliate affiliate = genericDao.read(Affiliate.class, user.getId(), false);
        if (affiliate == null) {
            return false;
        }

        return true;
    }

    @Override
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public PersonDto resetPassword (String email, String newPassword) throws BusinessException {
        logger.info("resetPassword: " + email);

        // validate biz rules
        User pdo = validationService.getUserByEmail(email, true);

        // do biz action
        pdo.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt(12)));
        genericDao.update(pdo);

        return beanMapper.map(pdo.getPerson(), PersonDto.class);
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SubsAdminDto revokeRootForSubsAdmin(String subsAdminId) throws BusinessException {
        logger.info("revokeRootForSubsAdmin: " + subsAdminId);

        // validate biz rule
        SubsAdmin pdo = validationService.getSubsAdmin(subsAdminId, true);
        Boolean isRootSubsAdmin = pdo.getIsRootSubsAdmin() == true;
        if (!isRootSubsAdmin) {
            throw new BusinessException(BusinessException.SUBS_ADMIN_IS_NOT_ROOT_SUBS_ADMIN,
                    String.format(BusinessException.SUBS_ADMIN_IS_NOT_ROOT_SUBS_ADMIN_MESSAGE,  pdo.getId()));
        }

        // do biz action
        pdo.setIsRootSubsAdmin(false);
        genericDao.update(pdo);

        return beanMapper.map(pdo, SubsAdminDto.class);
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN})
    public SearchResult<AdminDto> searchAdmins(SearchCriteria<AdminSearchCriteria> vmSearchCriteria) throws BusinessException {
        logger.info("searchAdmins: " + vmSearchCriteria.toString());

        // setup search criteria
        SearchCriteria<Admin> searchCriteria = new SearchCriteria<>();
        beanMapper.map(vmSearchCriteria, searchCriteria);       // map sort, page info
        if (vmSearchCriteria.getCriteria() != null) {
            // map search info
            Admin criteria = beanMapper.map(vmSearchCriteria.getCriteria(), Admin.class);
            searchCriteria.setCriteria(criteria);
        }
        else {
            // no search info found, use default
            searchCriteria.setCriteria(new Admin());
        }

        // process custom criteria
        SearchResult<Admin> searchResult;
        SearchResult<AdminDto> dtoSearchResult;
        CustomCriteria customCriteria = searchCriteria.getCustomCriteria();
        if (customCriteria.keySet().contains("email")) {
            List<String> userIds = specificDao.getUserIdsByEmail(customCriteria.getValue("email"));
            if (userIds.size() == 0) {
                searchResult = genericDao.generateEmptySearchResult(searchCriteria);
                dtoSearchResult = createAdminDtoSearchResult(searchResult);
                return dtoSearchResult;
            }
            customCriteria.remove("email");
            customCriteria.setValue("id", userIds);
        }

        // do biz action
        searchCriteria.getCriteria().setDiscriminator(Discriminator.ADMIN);
        searchResult = personDao.search(searchCriteria);
        dtoSearchResult = createAdminDtoSearchResult(searchResult);

        return dtoSearchResult;
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN, SystemRole.ROLE_AFFILIATE})
    public SearchResult<AffiliateDto> searchAffiliates(SearchCriteria<AffiliateSearchCriteria> vmSearchCriteria) throws BusinessException {
        logger.info("searchAffiliates: " + vmSearchCriteria.toString());

        // setup search criteria
        SearchCriteria<Affiliate> searchCriteria = new SearchCriteria<>();
        beanMapper.map(vmSearchCriteria, searchCriteria);       // map sort, page info
        if (vmSearchCriteria.getCriteria() != null) {
            // map search info
            Affiliate criteria = beanMapper.map(vmSearchCriteria.getCriteria(), Affiliate.class);
            searchCriteria.setCriteria(criteria);
        }
        else {
            // no search info found, use default
            searchCriteria.setCriteria(new Affiliate());
        }

        UserDto loggedUserDto = authenService.getLoggedUser();
        boolean isAdmin = SystemRole.hasAdminRole(loggedUserDto.getRoles());
        boolean isSubsAdmin = SystemRole.hasSubsAdminRole(loggedUserDto.getRoles());
        boolean isAffiliate = SystemRole.hasAffiliateRole(loggedUserDto.getRoles());

        // process custom criteria
        SearchResult<Affiliate> searchResult;
        SearchResult<AffiliateDto> dtoSearchResult;
        List<String> affiliateIds = new ArrayList<>();
        CustomCriteria customCriteria = searchCriteria.getCustomCriteria();

          // by email and status?
        if (customCriteria.keySet().contains("email") && customCriteria.keySet().contains("status")) {
            String email = customCriteria.getValue("email");
            String status = customCriteria.getValue("status");
            List<String> userIdsByEmailAndStatus = specificDao.getUserIdsByEmailAndStatus(email, status);
            if (userIdsByEmailAndStatus.size() == 0) {
                searchResult = genericDao.generateEmptySearchResult(searchCriteria);
                dtoSearchResult = createAffiliateDtoSearchResult(searchResult);
                return dtoSearchResult;
            }
            customCriteria.remove("email");
            customCriteria.remove("status");
            affiliateIds.addAll(userIdsByEmailAndStatus);
        }
          // by email only?
        else if (customCriteria.keySet().contains("email")) {
            String email = customCriteria.getValue("email");
            List<String> userIdsByEmail = specificDao.getUserIdsByEmail(email);
            if (userIdsByEmail.size() == 0) {
                searchResult = genericDao.generateEmptySearchResult(searchCriteria);
                dtoSearchResult = createAffiliateDtoSearchResult(searchResult);
                return dtoSearchResult;
            }
            customCriteria.remove("email");
            affiliateIds.addAll(userIdsByEmail);
        }
          // by status only?
        else if (customCriteria.keySet().contains("status")) {
            String status = customCriteria.getValue("status");
            List<String> userIdsByStatus = specificDao.getUserIdsByStatus(status);
            if (userIdsByStatus.size() == 0) {
                searchResult = genericDao.generateEmptySearchResult(searchCriteria);
                dtoSearchResult = createAffiliateDtoSearchResult(searchResult);
                return dtoSearchResult;
            }
            customCriteria.remove("status");
            affiliateIds.addAll(userIdsByStatus);
        }

          // by subscriberId for role ADMIN?
        if (customCriteria.keySet().contains("subscriberId")) {
            if (isAdmin) {
                String subscriberId = customCriteria.getValue("subscriberId");
                List<String> affiliateIdsBySubscriberId = specificDao.getAffiliateIdsBySubscriberId(subscriberId);
                if (affiliateIdsBySubscriberId.size() == 0) {
                    searchResult = genericDao.generateEmptySearchResult(searchCriteria);
                    dtoSearchResult = createAffiliateDtoSearchResult(searchResult);
                    return dtoSearchResult;
                }
                customCriteria.remove("subscriberId");
                if (affiliateIds.size() == 0) {
                    affiliateIds.addAll(affiliateIdsBySubscriberId);
                }
                else {
                    affiliateIds.retainAll(affiliateIdsBySubscriberId); // affiliateIds AND affiliateIdsBySubscriberId
                    if (affiliateIds.size() == 0) {
                        searchResult = genericDao.generateEmptySearchResult(searchCriteria);
                        dtoSearchResult = createAffiliateDtoSearchResult(searchResult);
                        return dtoSearchResult;
                    }
                }
            }
            else {
                customCriteria.remove("subscriberId");
            }
        }

          // by not status?
        if (customCriteria.keySet().contains("status!=")) {
            String notStatus = customCriteria.getValue("status!=");
            List<String> affiliateIdsByNotStatus = specificDao.getAffiliateIdsByNotStatus(notStatus);
            if (affiliateIdsByNotStatus.size() == 0) {
                searchResult = genericDao.generateEmptySearchResult(searchCriteria);
                dtoSearchResult = createAffiliateDtoSearchResult(searchResult);
                return dtoSearchResult;
            }
            customCriteria.remove("status!=");
            if (affiliateIds.size() == 0) {
                affiliateIds.addAll(affiliateIdsByNotStatus);
            }
            else {
                affiliateIds.retainAll(affiliateIdsByNotStatus);    // affiliateIds AND affiliateIdsByNotStatus
                if (affiliateIds.size() == 0) {
                    searchResult = genericDao.generateEmptySearchResult(searchCriteria);
                    dtoSearchResult = createAffiliateDtoSearchResult(searchResult);
                    return dtoSearchResult;
                }
            }
        }

        // do authorization
          // Admin can search all Affiliates
          // SubsAdmin can only search Affiliates of its Subscriber
          // Affiliate can only search Affiliates created by it
        List<String> authAffiliateIds = new ArrayList<>();
        if (!isAdmin) {
            if (isSubsAdmin) {
                SubsAdmin loggedSubsAdmin = genericDao.read(SubsAdmin.class, loggedUserDto.getId(), false);
                authAffiliateIds = specificDao.getAffiliateIdsBySubscriberId(loggedSubsAdmin.getSubscriberId());
            }
            else if (isAffiliate) {
                Affiliate loggedAffiliate = validationService.getAffiliate(loggedUserDto.getId(), false);
                authAffiliateIds = specificDao.getAffiliateIdsCreatedByAffiliateId(loggedAffiliate.getId());
            }

            if (authAffiliateIds.size() == 0) {
                searchResult = genericDao.generateEmptySearchResult(searchCriteria);
                dtoSearchResult = createAffiliateDtoSearchResult(searchResult);
                return dtoSearchResult;
            }

            if (affiliateIds.size() > 0) {  // search by custom criteria
                affiliateIds.retainAll(authAffiliateIds);
                if (affiliateIds.size() == 0) {
                    searchResult = genericDao.generateEmptySearchResult(searchCriteria);
                    dtoSearchResult = createAffiliateDtoSearchResult(searchResult);
                    return dtoSearchResult;
                }
            }
            else {      // no search criteria, retrieve Affiliates authorized by role
                affiliateIds.addAll(authAffiliateIds);
            }
        }

        if (affiliateIds.size() > 0) {
            customCriteria.setValue("id", affiliateIds);
        }

        // do biz action
        searchCriteria.getCriteria().setDiscriminator(Discriminator.AFFILIATE);
        searchResult = personDao.search(searchCriteria);
        dtoSearchResult = createAffiliateDtoSearchResult(searchResult);

        return dtoSearchResult;
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN})
    public SearchResult<SubsAdminDto> searchSubsAdmins(SearchCriteria<SubsAdminSearchCriteria> vmSearchCriteria) throws BusinessException {
        logger.info("searchSubsAdmins: " + vmSearchCriteria.toString());

        // setup search criteria
        SearchCriteria<SubsAdmin> searchCriteria = new SearchCriteria<>();
        beanMapper.map(vmSearchCriteria, searchCriteria);       // map sort, page info
        if (vmSearchCriteria.getCriteria() != null) {
            // map search info
            SubsAdmin criteria = beanMapper.map(vmSearchCriteria.getCriteria(), SubsAdmin.class);
            searchCriteria.setCriteria(criteria);
        }
        else {
            // no search info found, use default
            searchCriteria.setCriteria(new SubsAdmin());
        }

        // process custom criteria
        SearchResult<SubsAdmin> searchResult;
        SearchResult<SubsAdminDto> dtoSearchResult;
        List<String> subsAdminIds = new ArrayList<>();
        CustomCriteria customCriteria = searchCriteria.getCustomCriteria();
        if (customCriteria.keySet().contains("email")) {
            List<String> userIds = specificDao.getUserIdsByEmail(customCriteria.getValue("email"));
            if (userIds.size() == 0) {
                searchResult = genericDao.generateEmptySearchResult(searchCriteria);
                dtoSearchResult = createSubsAdminDtoSearchResult(searchResult);
                return dtoSearchResult;
            }
            customCriteria.remove("email");
            subsAdminIds.addAll(userIds);
        }

        // do authorization
          // Admin can search all SubsAdmins
          // SubsAdmin can only search SubsAdmins of its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        boolean isSubsAdmin = SystemRole.hasSubsAdminRole(loggedUserDto.getRoles());
        if (isSubsAdmin) {
            SubsAdmin subsAdmin = genericDao.read(SubsAdmin.class, loggedUserDto.getId(), false);
            List<String> authSubsAdminIds = specificDao.getSubsAdminIdsBySubscriberId(subsAdmin.getSubscriberId());
            if (subsAdminIds.size() > 0) {  // search by custom criteria
                subsAdminIds.retainAll(authSubsAdminIds);
                if (subsAdminIds.size() == 0) {
                    searchResult = genericDao.generateEmptySearchResult(searchCriteria);
                    dtoSearchResult = createSubsAdminDtoSearchResult(searchResult);
                    return dtoSearchResult;
                }
            }
            else {
                subsAdminIds.addAll(authSubsAdminIds);
            }
        }

        if (subsAdminIds.size() > 0) {
            customCriteria.setValue("id", subsAdminIds);
        }

        // do biz action
        searchCriteria.getCriteria().setDiscriminator(Discriminator.SUBS_ADMIN);
        searchResult = personDao.search(searchCriteria);
        dtoSearchResult = createSubsAdminDtoSearchResult(searchResult);

        return dtoSearchResult;
    }

    @Override
    @Secured(SystemRole.ROLE_SUBS_ADMIN)
    public void sendCustomEmailsToAffiliates(CustomEmailModel customEmailModel) throws BusinessException, IOException {
        logger.info("sendCustomEmailsToAffiliates: " + customEmailModel.toString());

        // process view model
        String errors = customEmailModel.validate();
        if ( errors != null ) {
            throw new InvalidViewModelException(errors);
        }

        // validate biz rule
        List<Affiliate> affiliates = new ArrayList<>();
        for (String affiliateId: customEmailModel.getAffiliateIds()) {
            Affiliate affiliate = validationService.getAffiliate(affiliateId, false);
            affiliates.add(affiliate);
        }

        // do authorization
        UserDto loggedUserDto = authenService.getLoggedUser();
        if (!specificDao.isAffiliatesSameSubscriber(customEmailModel.getAffiliateIds())) {
            throw new BusinessException(BusinessException.AFFILIATES_NOT_SAME_SUBSCRIBER,
                    String.format(BusinessException.AFFILIATES_NOT_SAME_SUBSCRIBER_MESSAGE, customEmailModel.getAffiliateIds().toString()));
        }
        authorService.checkSubsAdminAndAffiliate(loggedUserDto.getId(),customEmailModel.getAffiliateIds().get(0));

        // do biz action
        List<Email> emails = new ArrayList<>();
        for (Affiliate affiliate: affiliates) {
            String emailContent = emailService.getSystemEmailTemplate();
            String title = customEmailModel.getTitle();
            String name =  String.join(" ",affiliate.getLastName(),affiliate.getFirstName());
            emailContent = emailContent.replace("[title]", title);
            emailContent = emailContent.replace("[content]", customEmailModel.getContent());

            Email email = MyEmailUtil.createEmailFromEmailTemplate(title,emailContent,affiliate.getUser().getEmail(),name);
            emails.add(email);
        }
        emailService.send(emails);
    }

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN, SystemRole.ROLE_CHANNEL})
    public AffiliateDto sendDataToGetResponse(AffiliateDto affiliateDto) throws BusinessException {
        logger.info("sendDataToGetResponse: " + affiliateDto);

        SubsGetResponseConfig subsGetResponseConfig = validationService.getSubsGetResponseConfig(affiliateDto.getSubscriberId(), false);
        if (subsGetResponseConfig.getApiKey() == null || subsGetResponseConfig.getCampaignDefaultId() == null) {
            throw new BusinessException(BusinessException.SUBS_GETRESPONSE_CONFIG_CAMPAIGN_DEFAULT_APIKEY_NOT_FOUND,
                    String.format(BusinessException.SUBS_GETRESPONSE_CONFIG_CAMPAIGN_DEFAULT_APIKEY_NOT_FOUND_MESSAGE,
                            subsGetResponseConfig.getId()));
        }

        String affiliateEmail = affiliateDto.getUser().getEmail();
        String apiKey = subsGetResponseConfig.getApiKey();
        SubsGetResponseConfigDto subsGetResponseConfigDto = beanMapper.map(subsGetResponseConfig, SubsGetResponseConfigDto.class);
        try {
            GetResponseContact contact = getResponseClient.getContactByEmail(affiliateEmail, apiKey);
            if (contact != null && contact.getId() != null) {
                this.updateGetResponseSuccess(affiliateDto.getId(), true);
                affiliateDto.setIsGetResponseSuccess(true);
                return affiliateDto;
            }

            boolean success = getResponseClient.createContactFromAffiliateDto(affiliateDto, apiKey, subsGetResponseConfigDto);
            if (success) {
                this.updateGetResponseSuccess(affiliateDto.getId(), true);
                affiliateDto.setIsGetResponseSuccess(true);
            }
            else {
                this.updateGetResponseSuccess(affiliateDto.getId(), false);
                affiliateDto.setIsGetResponseSuccess(false);
            }
        }
        catch (GetResponseIntegrationException grie) {
            logger.error("sendDataToGetResponse failed: " + grie.getMessage(), grie);
        }

        return affiliateDto;
    }

    @Override
    @Secured({SystemRole.ROLE_CHANNEL})
    public LoginDto signInAffiliate(AffiliateSignInModel signInModel) throws BusinessException {
        logger.info("signInAffiliate: " + signInModel.toString());

        // process view model
        signInModel.escapeHtml();
        String errors = signInModel.validate();
        if (errors != null) {
            throw new InvalidViewModelException(errors);
        }
        String email = signInModel.getEmail();
        String password = signInModel.getPassword();

        // validate biz rules
        LoginDto loginDto = authenService.authenticate(email, password);
        if (loginDto == null) {
            throw new BusinessException(BusinessException.USER_NOT_FOUND,
                    String.format(BusinessException.USER_NOT_FOUND_MESSAGE, email));
        }

        if (loginDto.getStatus().equals(User.STATUS_INACTIVE)) {
            throw new BusinessException(BusinessException.USER_INACTIVE,
                    String.format(BusinessException.USER_INACTIVE_MESSAGE, email));
        }

        Affiliate affiliate = genericDao.read(Affiliate.class, loginDto.getId(), false);
        if (affiliate == null) {
            throw new BusinessException(BusinessException.AFFILIATE_NOT_FOUND,
                    String.format(BusinessException.AFFILIATE_NOT_FOUND_MESSAGE, email));
        }

        // check if Affiliate belongs to Channel's Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        Channel loggedChannel = genericDao.read(Channel.class, loggedUserDto.getId(), false);
        Map<String, Object> agentParams = new HashMap<>();
        agentParams.put("affiliateId", affiliate.getId());
        agentParams.put("subscriberId", loggedChannel.getSubscriberId());
        Agent agent = genericDao.readUnique(Agent.class, agentParams, false);
        if (agent == null) { // Affiliate not belongs to Channel's Subscriber, return confirm message
            LoginDto dto = new LoginDto();
            dto.setId(affiliate.getId());
            dto.setSubscriberId(loggedChannel.getSubscriberId());
            dto.setConfirmMessage(AffiliateDto.CONFIRM_MESSAGE_FOR_CHANNEL);

            return dto;
        }

        return loginDto;
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public AdminDto updateAdmin(String adminId, AdminUpdateModel updateModel) throws BusinessException {
        logger.info("updateAdmin: " + adminId + " - " + updateModel.toString());

        // process view model
        updateModel.escapeHtml();
        String errors = updateModel.validate();
        if (errors != null) {
            throw new InvalidViewModelException(errors);
        }
        Admin detachedDO = beanMapper.map(updateModel, Admin.class);

        // validate biz rules
        Admin existingDO = validationService.getAdmin(adminId, true);
        User user = detachedDO.getUser();
        if (user != null) {
            User existingUser = validationService.getUser(user.getId(), true);
            if (user.getRoles() != null && user.getRoles().size() > 0) {
                Set<Role> syncedRoles = syncRoles(user.getRoles(), existingUser.getRoles());
                if (syncedRoles.size() == 0 || !isRoleInSet(SystemRole.ROLE_ADMIN, syncedRoles)) {
                    Role adminRole = validationService.getRoleByName(SystemRole.ROLE_ADMIN, false);
                    syncedRoles.add(adminRole);
                }

                existingUser.setRoles(syncedRoles);
                user.setRoles(null);
            }

            // map input to existingUser
            MyBeanUtil.mapIgnoreNullProps(existingUser, user);

            // do update user
            genericDao.update(existingUser);
        }

        // map input to existingDO
        MyBeanUtil.mapIgnoreNullProps(existingDO, detachedDO);

        // do biz action
        personDao.update(existingDO);

        return beanMapper.map(existingDO, AdminDto.class);
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN, SystemRole.ROLE_AFFILIATE})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public AffiliateDto updateAffiliate(String affiliateId, AffiliateUpdateModel updateModel) throws BusinessException {
        logger.info("updateAffiliate: " + affiliateId + " - " + updateModel.toString());

        // process view model
        updateModel.escapeHtml();
        String errors = updateModel.validate();
        if (errors != null) {
            throw new InvalidViewModelException(errors);
        }
        Affiliate detachedDO = beanMapper.map(updateModel, Affiliate.class);

        // validate biz rules
        Affiliate existingDO = validationService.getAffiliate(affiliateId, true);

        // do authorization
          // Admin can update any Affiliate
          // SubsAdmin can only update the Affiliate of its Subscriber
          // Affiliate can only update the Affiliate be created by it
        UserDto loggedUserDto = authenService.getLoggedUser();
        boolean isSubsAdmin = SystemRole.hasSubsAdminRole(loggedUserDto.getRoles());
        boolean isAffiliate = SystemRole.hasAffiliateRole(loggedUserDto.getRoles());
        if (isSubsAdmin) {
            authorService.checkSubsAdminAndAffiliate(loggedUserDto.getId(), affiliateId);
        }
        else if (isAffiliate && !loggedUserDto.getId().equals(affiliateId)) {
            if (!specificDao.isAffiliateCreatedBy(affiliateId, loggedUserDto.getId())) {
                throw new BusinessException(BusinessException.AFFILIATE_LOGGED_AFFILIATE_NOT_CREATOR,
                    String.format(BusinessException.AFFILIATE_LOGGED_AFFILIATE_NOT_CREATOR_MESSAGE,
                                  loggedUserDto.getId(), affiliateId));
            }
        }

        // map input to existingDO
        MyBeanUtil.mapIgnoreNullProps(existingDO, detachedDO);

        // do biz action
        personDao.update(existingDO);

        return beanMapper.map(existingDO, AffiliateDto.class);
    }

    @Override
    @Transactional(value = "jdbcTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateGetResponseSuccess(String id, boolean success) {
        logger.info("updateGetResponseSuccess: " + id + " success: " + success);

        int numberOfRowsAffected = specificDao.updateGetResponseSuccessOfAffiliate(id, success);
        if (numberOfRowsAffected == 0) {
            logger.error("Affiliate '" + id + "' not found !");
        }
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN, SystemRole.ROLE_AFFILIATE})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public PersonDto updateProfile(String id, PersonUpdateModel updateModel) throws BusinessException {
        logger.info("updateProfile: " + id + ", updateModel: " + updateModel);

        // process view model
        updateModel.escapeHtml();
        String errors = updateModel.validate();
        if (errors != null) {
            throw new InvalidViewModelException(errors);
        }
        Person detachedDO = beanMapper.map(updateModel, Person.class);
        BankUpdateModel bankUpdateModel = updateModel.getBank();

        // validate biz rules
        Person existingDO = validationService.getPerson(id, true);
        if (detachedDO.getProvinceId() != null) {
            if (detachedDO.getProvinceId().equals("")) {  // reset prop
                existingDO.setProvince(null);
                existingDO.setProvinceId(null);
                detachedDO.setProvinceId(null);
            }
            else {
                Domain province = validationService.getDomain(detachedDO.getProvinceId(), false);
                detachedDO.setProvince(province);
            }
        }

        // do authorization
          // User can only update profile of itself
        UserDto loggedUserDto = authenService.getLoggedUser();
        if (!loggedUserDto.getId().equals(id)) {
            throw new BusinessException(BusinessException.USER_DATA_AUTHORIZATION,
                    String.format(BusinessException.USER_DATA_AUTHORIZATION_MESSAGE, id));
        }

        boolean isAffiliate = SystemRole.hasAffiliateRole(loggedUserDto.getRoles());
        if (isAffiliate && bankUpdateModel != null) {
            Affiliate affiliate = validationService.getAffiliate(id, true);
            Bank existingBank = affiliate.getBank();
            if(existingBank == null) {
                Bank newBank = beanMapper.map(bankUpdateModel, Bank.class);
                newBank.setAffiliate(affiliate);
                Bank bankPdo = genericDao.create(newBank);
                affiliate.setBank(bankPdo);
            }
            else {
                Bank detachedBank = beanMapper.map(bankUpdateModel, Bank.class);
                MyBeanUtil.mapIgnoreNullProps(existingBank, detachedBank);
                genericDao.update(existingBank);
            }
        }

        // map input to existingDO
        MyBeanUtil.mapIgnoreNullProps(existingDO, detachedDO);

        // do biz action
        personDao.update(existingDO);
        User userExistingDO = validationService.getUser(existingDO.getId(), true);
        userExistingDO.setStatus(User.STATUS_PROFILE_UPDATED);
        personDao.update(userExistingDO);

        return beanMapper.map(existingDO, PersonDto.class);
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SubsAdminDto updateSubsAdmin(String subsAdminId, SubsAdminUpdateModel updateModel) throws BusinessException {
        logger.info("updateSubsAdmin: " + subsAdminId + " - " + updateModel.toString());

        // process view model
        updateModel.escapeHtml();
        String errors = updateModel.validate();
        if (errors != null) {
            throw new InvalidViewModelException(errors);
        }
        SubsAdmin detachedDO = beanMapper.map(updateModel, SubsAdmin.class);

        // validate biz rules
        SubsAdmin existingDO = validationService.getSubsAdmin(subsAdminId, true);

        // map input to existingDO
        MyBeanUtil.mapIgnoreNullProps(existingDO, detachedDO);

        // do authorization
          // Admin can update any SubsAdmin
          // SubsAdmin can only update SubsAdmin of its Subscriber
          // SubsAdmin can not update RootSubsAdmin
        UserDto loggedUserDto = authenService.getLoggedUser();
        String subscriberId = existingDO.getSubscriberId();
        boolean isSubsAdmin = SystemRole.hasSubsAdminRole(loggedUserDto.getRoles());
        if (isSubsAdmin) {
            authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);

            if (existingDO.getIsRootSubsAdmin() == true) {
                throw new BusinessException(BusinessException.CAN_NOT_UPDATE_ROOT_SUBS_ADMIN,
                        String.format(BusinessException.CAN_NOT_UPDATE_ROOT_SUBS_ADMIN_MESSAGE,  existingDO.getId()));
            }
        }

        // do biz action
        personDao.update(existingDO);

        return beanMapper.map(existingDO, SubsAdminDto.class);
    }

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN})
    public AffiliateViewDto viewAffiliate(String id) throws BusinessException {
        logger.info("viewAffiliate: " + id);

        // process view model

        // validate biz rules
        UserDto loggedUserDto = authenService.getLoggedUser();
        SubsAdmin subsAdmin = validationService.getSubsAdmin(loggedUserDto.getId(), false);
        Subscriber subscriber = validationService.getSubscriber(subsAdmin.getSubscriberId(), false);
        String subscriberId = subscriber.getId();

        // do authorization
            // SubsAdmin can view Affiliate of its Subscriber
        authorService.checkSubsAdminAndAffiliate(loggedUserDto.getId(), id);

        // do biz action
        AffiliateViewDto dto = new AffiliateViewDto();
        AffiliateDto affiliateDto = this.getAffiliate(id);
        dto.setAffiliate(affiliateDto);
        dto.setAffiliateCount(this.countAffiliatesByAffiliateIdAndSubscriberId(id, subscriberId));
        dto.setCustomerCount(customerService.countByAffiliateId(id));
        dto.setOrderCount(orderService.countByAffiliateId(id));
        dto.setSalesValue(orderService.getRevenueByAffiliateId(id, null));
        dto.setClicksByDate(specificDao.getShareClicksByDateByAffiliateIdAndSubscriberId(id, subscriberId));

        // AffiliateView Order revenue
        Double orderRevenue = specificDao.getOrderRevenueByAffiliateIdAndSubscriberId(id, subscriberId, null);
        dto.setOrderRevenue(orderRevenue);

        // AffiliateView Approved Order revenue = approvedOrderRevenue + commissionsDoneOrderRevenue
        Double approvedOrderRevenue = specificDao.getOrderRevenueByAffiliateIdAndSubscriberId(id, subscriberId, Order.STATUS_APPROVED);
        Double commissionsDoneOrderRevenue = specificDao.getOrderRevenueByAffiliateIdAndSubscriberId(id, subscriberId, Order.STATUS_COMMISSIONS_DONE);
        dto.setApprovedOrderRevenue(approvedOrderRevenue + commissionsDoneOrderRevenue);

        // AffiliateView NonApproved Order revenue = AffiliateView Order revenue - AffiliateView Approved Order revenue
        Double nonApprovedOrderRevenue = dto.getOrderRevenue() - dto.getApprovedOrderRevenue();
        dto.setNonApprovedOrderRevenue(nonApprovedOrderRevenue);

//        // AffiliateView Network revenue
//        List<String> affiliateIds = new ArrayList<>();
//        List<String> presenteeIds = new ArrayList<>();
//        List<String> referrers = new ArrayList<>();
//        referrers.add(affiliateDto.getNickname());
//        do {
//            presenteeIds.clear();
//            for (String referrer : referrers) {
//                List<String> referrerPresenteeIds = specificDao.getAffiliateIdsBySubscriberIdAndReferrer(subscriberId, referrer);
//                if (!CollectionUtils.isEmpty(referrerPresenteeIds)) {
//                    presenteeIds.addAll(referrerPresenteeIds);
//                }
//            }
//            if (!CollectionUtils.isEmpty(presenteeIds)) {
//                referrers = specificDao.getAffiliateNicknamesByAffiliateIds(presenteeIds);
//                affiliateIds.addAll(presenteeIds);
//            }
//        }
//        while (!CollectionUtils.isEmpty(presenteeIds));
//
//        Double networkRevenue = specificDao.getOrderRevenueByAffiliateIdsAndSubscriberId(affiliateIds, subscriberId, null);
//        dto.setNetworkRevenue(networkRevenue);

        return dto;
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_AFFILIATE})
    public AffiliateTransactionDto viewAffiliateTransaction(AffiliateTransactionModel viewModel) throws BusinessException {
        logger.info("viewAffiliateTransaction: " + viewModel.toString());

        // process view model
        viewModel.escapeHtml();
        String errors = viewModel.validate();
        if (errors != null) {
            throw new InvalidViewModelException(errors);
        }
        String affiliateId = viewModel.getAffiliateId();
        String subscriberId = viewModel.getSubscriberId();
        Date fromDate = viewModel.getFromDate();
        Date toDate = viewModel.getToDate();

        // validate biz rules
        AffiliateTransactionDto dto = new AffiliateTransactionDto();

        // do authorization
            // Admin can view transaction of all Affiliate
            // Affiliate can only view transaction of it
        UserDto loggedUserDto = authenService.getLoggedUser();
        boolean isAffiliate = SystemRole.hasAffiliateRole(loggedUserDto.getRoles());
        AffiliateDto affiliateDto;
        if (isAffiliate) {
            affiliateDto = this.getAffiliate(loggedUserDto.getId());
        } else {
            if (affiliateId == null) {
                throw new InvalidViewModelException("'affiliate_id' can't be empty !");
            }
            affiliateDto = this.getAffiliate(affiliateId);
        }

        // do biz action
        dto.setAffiliate(affiliateDto);

        Agent agentPdo = validationService.getAgent(affiliateDto.getId(), subscriberId, false);
        dto.setAgent(beanMapper.map(agentPdo, AgentDto.class));

        SearchResult<CommissionDto> commissionSearchResult = commissionService.search(new SearchCriteria<CommissionSearchCriteria>() {{
            this.setCriteria(new CommissionSearchCriteria() {{
                this.setAffiliateId(affiliateDto.getId());
                this.setSubscriberId(subscriberId);
            }});
            this.setCustomCriteria(new CustomCriteria() {{
                this.setValue("createdAt>=", fromDate);
                this.setValue("createdAt<=", toDate);
            }});
            this.setPageSize(SearchCriteria.MAX_PAGE_SIZE);
        }});
        if (commissionSearchResult != null && commissionSearchResult.getList().size() > 0) {
            dto.setCommissions(commissionSearchResult.getList());
        }

        SearchResult<PaymentDto> paymentSearchResult = paymentService.search(new SearchCriteria<PaymentSearchCriteria>() {{
            this.setCriteria(new PaymentSearchCriteria() {{
                this.setAffiliateId(affiliateDto.getId());
                this.setSubscriberId(subscriberId);
            }});
            this.setCustomCriteria(new CustomCriteria() {{
                this.setValue("createdAt>=", fromDate);
                this.setValue("createdAt<=", toDate);
            }});
            this.setPageSize(SearchCriteria.MAX_PAGE_SIZE);
        }});
        if (paymentSearchResult != null && paymentSearchResult.getList().size() > 0) {
            dto.setPayments(paymentSearchResult.getList());
        }
        return dto;
    }

    // Utilities
    private Role checkExistingRole(String roleName) throws BusinessException {
        return validationService.getRoleByName(roleName, false);
    }

    private SearchResult<AdminDto> createAdminDtoSearchResult(SearchResult<Admin> searchResult) {
        SearchResult<AdminDto> result = new SearchResult<>();
        beanMapper.map(searchResult, result);
        List<AdminDto> dtos = new ArrayList<>();
        for (Admin pdo : searchResult.getList()) {
            dtos.add(beanMapper.map(pdo, AdminDto.class));
        }
        result.setList(dtos);

        return result;
    }

    private SearchResult<AffiliateDto> createAffiliateDtoSearchResult(SearchResult<Affiliate> searchResult) {
        SearchResult<AffiliateDto> result = new SearchResult<>();
        beanMapper.map(searchResult, result);
        List<AffiliateDto> dtos = new ArrayList<>();
        for (Affiliate pdo : searchResult.getList()) {
            AffiliateDto affiliateDto = beanMapper.map(pdo, AffiliateDto.class);
            Set<SubscriberDto> subscribers = pdo.getAgents()
                    .stream()
                    .map(agent -> beanMapper.map(agent.getSubscriber(), SubscriberDto.class))
                    .collect(Collectors.toSet());
            affiliateDto.getSubscribers().addAll(subscribers);

            dtos.add(affiliateDto);
        }
        result.setList(dtos);

        return result;
    }

    private SearchResult<SubsAdminDto> createSubsAdminDtoSearchResult(SearchResult<SubsAdmin> searchResult) {
        SearchResult<SubsAdminDto> result = new SearchResult<>();
        beanMapper.map(searchResult, result);
        List<SubsAdminDto> dtos = new ArrayList<>();
        for (SubsAdmin pdo : searchResult.getList()) {
            dtos.add(beanMapper.map(pdo, SubsAdminDto.class));
        }
        result.setList(dtos);

        return result;
    }

    private String generateActivationToken(String email, String activationCode) {
        return MyTokenUtil.encode(new String[]{email, activationCode});
    }

    private void increaseNumberOfAffiliatesInNetworkOfReferrers(String affiliateId, String subscriberId) throws BusinessException {
        if(StringUtils.isBlank(affiliateId) || StringUtils.isBlank(subscriberId)) {
           return;
        }

        // increase number of Affiliates in network for the input referrer
        specificDao.increaseAffiliateNumberOfAnAffiliateInASubscriber(affiliateId, subscriberId);

        // call recursively to update data for the input referrer's ancestors
        Map<String, Object> agentParams = new HashMap<>();
        agentParams.put("affiliateId", affiliateId);
        agentParams.put("subscriberId", subscriberId);
        Agent referringAgent = genericDao.readUnique(Agent.class, agentParams, false);
        if( !StringUtils.isBlank(referringAgent.getReferrer()) ) {
            Affiliate referringAffiliate = validationService.getAffiliateByNickname(referringAgent.getReferrer(), false);
            increaseNumberOfAffiliatesInNetworkOfReferrers(referringAffiliate.getId(), subscriberId);
        }
    }

    private boolean isRoleInSet(String roleName, Set<Role> roles) {
        if (roles == null || roles.size() == 0) {
            return false;
        }

        for (Role role : roles) {
            if (role.getName().equals(roleName)) {
                return true;
            }
        }

        return false;
    }

    private boolean isSubscriberInAgents(String subscriberId, Set<Agent> agents) {
        if (subscriberId == null || agents == null || agents.size() == 0) {
            return false;
        }

        for (Agent agent: agents) {
            if (subscriberId.equals(agent.getSubscriberId())) {
                return true;
            }
        }

        return false;
    }

    private void renderAffiliatesToExcelSheet(List<AffiliateDto> affiliateDtos, XSSFSheet sheet, int startRow) {
        for (AffiliateDto affiliateDto : affiliateDtos) {
            Row row = sheet.createRow(startRow++);
            Cell cell1 = row.createCell(0);
            cell1.setCellValue(affiliateDto.getLastName());
            Cell cell2 = row.createCell(1);
            cell2.setCellValue(affiliateDto.getFirstName());
            Cell cell3 = row.createCell(2);
            cell3.setCellValue(affiliateDto.getPhone());
            Cell cell4 = row.createCell(3);
            cell4.setCellValue(affiliateDto.getUser().getEmail());
            Cell cell5 = row.createCell(4);
            cell5.setCellValue(affiliateDto.getCreatedAt().toString());
            Cell cell6 = row.createCell(5);
            cell6.setCellValue(affiliateDto.getUser().getStatus());
        }
    }

    private Set<Role> syncRoles(Set<Role> newRoles, Set<Role> currentRoles) throws BusinessException {
        Set<Role> roles = new HashSet<Role>();
        if (currentRoles == null) {
            for (Role role : newRoles) {
                roles.add(checkExistingRole(role.getName()));
            }
            return roles;
        }
        else { // updateAdmin case
            Set<Role> tmp = new HashSet<>();
            for (Role role : newRoles) {
                tmp.add(checkExistingRole(role.getName()));
            }
            currentRoles.retainAll(tmp);
            currentRoles.addAll(tmp);
            return currentRoles;
        }
    }

    public String convertFullnameToNickname(String str) {
        String temp = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("").toLowerCase().replaceAll(" ", "").replaceAll("đ", "d");
    }

}
