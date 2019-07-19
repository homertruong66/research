package com.rms.rms.service;

import com.rms.rms.common.CustomCriteria;
import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.constant.Constant;
import com.rms.rms.common.constant.SystemRole;
import com.rms.rms.common.dto.ChannelDto;
import com.rms.rms.common.dto.UserDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.exception.InvalidViewModelException;
import com.rms.rms.common.util.MyBeanUtil;
import com.rms.rms.common.util.MyDateUtil;
import com.rms.rms.common.util.MyTokenUtil;
import com.rms.rms.common.view_model.ChannelCreateModel;
import com.rms.rms.common.view_model.ChannelSearchCriteria;
import com.rms.rms.common.view_model.ChannelUpdateModel;
import com.rms.rms.persistence.GenericDao;
import com.rms.rms.persistence.SpecificDao;
import com.rms.rms.service.model.*;
import org.apache.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * homertruong
 */

@Service
@Transactional(value = "transactionManager", propagation = Propagation.SUPPORTS)
public class ChannelServiceImpl implements ChannelService {

    private Logger logger = Logger.getLogger(ChannelServiceImpl.class);

    @Autowired
    private ModelMapper beanMapper;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private AuthenService authenService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private GenericDao genericDao;

    @Autowired
    private SpecificDao specificDao;
    
    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ChannelDto create(ChannelCreateModel createModel) throws BusinessException {
        logger.info("create: " + createModel.toString());

        // process view model
        createModel.escapeHtml();
        String errors = createModel.validate();
        if (errors != null) {
            throw new InvalidViewModelException(errors);
        }
        Channel newDO = beanMapper.map(createModel, Channel.class);

        // validate biz rules
        if (specificDao.isPropertyValueExistent(Channel.class, "domainName", newDO.getDomainName())) {
            throw new BusinessException(BusinessException.CHANNEL_DOMAIN_NAME_EXISTS,
                    String.format(BusinessException.CHANNEL_DOMAIN_NAME_EXISTS_MESSAGE, newDO.getDomainName()));
        }

        // do biz action
        UserDto loggedUserDto = authenService.getLoggedUser();
        SubsAdmin loggedSubsAdmin = genericDao.read(SubsAdmin.class, loggedUserDto.getId(), false);
        String subscriberId = loggedSubsAdmin.getSubscriberId();

          // check Subscriber usage limit on Channel
        PackageConfigApplied pca = validationService.getPackageConfigApplied(subscriberId, false);
        SubsPackageConfig spc = validationService.getSubsPackageConfig(subscriberId, false);
        int maxChannelCount = pca.getChannelCount().intValue() + spc.getChannelCount().intValue();
        SubsPackageStatus subsPackageStatus = validationService.getSubsPackageStatus(subscriberId, true);
        int channelCount = subsPackageStatus.getChannelCount().intValue();
        if (channelCount >= maxChannelCount) {
            throw new BusinessException(BusinessException.SUBSCRIBER_CHANNEL_COUNT_REACH_LIMIT,
                String.format(BusinessException.SUBSCRIBER_CHANNEL_COUNT_REACH_LIMIT_MESSAGE, maxChannelCount));
        }
          // check Subscriber usage expiration
        Date today = MyDateUtil.convertToUTCDate(new Date());
        if (pca.getUsageExpiredAt().before(today)) {
            throw new BusinessException(BusinessException.SUBSCRIBER_USAGE_EXPIRED,
                    String.format(BusinessException.SUBSCRIBER_USAGE_EXPIRED_MESSAGE, pca.getUsageExpiredAt()));
        }

        subsPackageStatus.setChannelCount(Integer.valueOf(channelCount + 1));
        genericDao.update(subsPackageStatus);

        newDO.setSubscriber(loggedSubsAdmin.getSubscriber());
        newDO.setSubscriberId(subscriberId);
        newDO.setFirstName("no first name");
        newDO.setLastName("no last name");
        Channel pdo = genericDao.create(newDO);

        // create a User for Channel
        User newUser = new User();
        newUser.setEmail(MyTokenUtil.generateRandomToken() + subscriberId + "@" + Constant.RMS_DOMAIN_NAME);
        String [] encodingParams = { "secret-key", subscriberId };
        String password = MyTokenUtil.encode(encodingParams);
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
        newUser.setPassword(hashedPassword);   // save hashed password to db
        newUser.setPerson(pdo);
        pdo.setUser(newUser);
        newUser.setStatus(User.STATUS_ACTIVE);
        Set<Role> roles = new HashSet<>();
        Role role = validationService.getRoleByName(SystemRole.ROLE_CHANNEL, false);
        roles.add(role);
        newUser.setRoles(roles);
        genericDao.create(newUser);

        ChannelDto dto = beanMapper.map(pdo, ChannelDto.class);
        dto.getUser().setPassword(password);        // return plain password

        return dto;
    }

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN})
    public ChannelDto get(String id) throws BusinessException {
        logger.info("get: " + id);

        // validate biz rules
        Channel pdo = validationService.getChannel(id, false);

        // do authorization
            // SubsAdmin can only get the Channel of its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        String subscriberId = pdo.getSubscriberId();
        authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);

        return beanMapper.map(pdo, ChannelDto.class);
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN, SystemRole.ROLE_AFFILIATE})
    public SearchResult<ChannelDto> search(SearchCriteria<ChannelSearchCriteria> vmSearchCriteria) throws BusinessException {
        logger.info("search: " + vmSearchCriteria.toString());

        // setup search criteria
        SearchCriteria<Channel> searchCriteria = new SearchCriteria<>();
        beanMapper.map(vmSearchCriteria, searchCriteria);   // map sort, page info
        if (vmSearchCriteria.getCriteria() != null) {
            // map search info
            Channel criteria = beanMapper.map(vmSearchCriteria.getCriteria(), Channel.class);
            searchCriteria.setCriteria(criteria);
        }
        else {
            // no search info found, use default
            searchCriteria.setCriteria(new Channel());
        }

        // do authorization
          // Admin can search all Channels
          // SubsAdmin can only search Channels of its Subscriber
          // Affiliate can only search Channels whose Subscribers he works for
        SearchResult<Channel> searchResult;
        SearchResult<ChannelDto> dtoSearchResult;
        UserDto loggedUserDto = authenService.getLoggedUser();
        boolean isSubsAdmin = SystemRole.hasSubsAdminRole(loggedUserDto.getRoles());
        boolean isAffiliate = SystemRole.hasAffiliateRole(loggedUserDto.getRoles());
        if (isSubsAdmin) {
            SubsAdmin loggedSubsAdmin = genericDao.read(SubsAdmin.class, loggedUserDto.getId(), false);
            CustomCriteria customCriteria = searchCriteria.getCustomCriteria();
            customCriteria.setValue("subscriberId", loggedSubsAdmin.getSubscriberId());
        }
        else if (isAffiliate) {
            CustomCriteria customCriteria = searchCriteria.getCustomCriteria();
            List<String> subscriberIdsByAffiliate = specificDao.getSubscriberIdsByAffiliateId(loggedUserDto.getId());
            if (subscriberIdsByAffiliate.size() == 0) {
                searchResult = genericDao.generateEmptySearchResult(searchCriteria);
                dtoSearchResult = createDtoSearchResult(searchResult);
                return dtoSearchResult;
            }

            if (customCriteria.keySet().contains("subscriberId")) {     // search by subscriberId
                String subscriberId = customCriteria.getValue("subscriberId");
                if (!subscriberIdsByAffiliate.contains(subscriberId)) {
                    searchResult = genericDao.generateEmptySearchResult(searchCriteria);
                    dtoSearchResult = createDtoSearchResult(searchResult);
                    return dtoSearchResult;
                }
                else {
                    customCriteria.setValue("subscriberId", subscriberId);
                }
            }
            else {
                customCriteria.setValue("subscriberId", subscriberIdsByAffiliate);
            }
        }

        // do biz action
        searchResult = genericDao.search(searchCriteria);
        dtoSearchResult = createDtoSearchResult(searchResult);

        return dtoSearchResult;
    }

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ChannelDto update(String id, ChannelUpdateModel updateModel) throws BusinessException {
        logger.info("update: " + id + " - " + updateModel);

        // process view model
        updateModel.escapeHtml();
        String errors = updateModel.validate();
        if (errors != null) {
            throw new InvalidViewModelException(errors);
        }
        Channel detachedDO = beanMapper.map(updateModel, Channel.class);

        // validate biz rules
        Channel existingDO = validationService.getChannel(id, true);
        if (detachedDO.getDomainName() != null && !detachedDO.getDomainName().equals(existingDO.getDomainName())) {
            if (specificDao.isPropertyValueExistent(Channel.class, "domainName", detachedDO.getDomainName())) {
                throw new BusinessException(BusinessException.SUBSCRIBER_DOMAIN_NAME_EXISTS,
                        String.format(BusinessException.SUBSCRIBER_DOMAIN_NAME_EXISTS_MESSAGE, detachedDO.getDomainName()));
            }
        }

        // do authorization
            // SubsAdmin can only update the Channel of its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        String subscriberId = existingDO.getSubscriberId();
        authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);

        // map input to existingDO
        MyBeanUtil.mapIgnoreNullProps(existingDO, detachedDO);

        // do biz action
        genericDao.update(existingDO);

        return beanMapper.map(existingDO, ChannelDto.class);
    }


    // Utilities
    private SearchResult<ChannelDto> createDtoSearchResult(SearchResult<Channel> searchResult) {
        SearchResult<ChannelDto> result = new SearchResult<>();
        beanMapper.map(searchResult, result);
        List<ChannelDto> dtos = new ArrayList<>();
        for (Channel pdo : searchResult.getList()) {
            dtos.add(beanMapper.map(pdo, ChannelDto.class));
        }
        result.setList(dtos);

        return result;
    }
}
