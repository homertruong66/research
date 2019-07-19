package com.rms.rms.service;

import com.rms.rms.common.dto.LoginDto;
import com.rms.rms.common.dto.PackageConfigAppliedDto;
import com.rms.rms.common.dto.PersonDto;
import com.rms.rms.common.dto.UserDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.util.MyTokenUtil;
import com.rms.rms.persistence.GenericDao;
import com.rms.rms.persistence.SpecificDao;
import com.rms.rms.service.model.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class AuthenServiceImpl implements AuthenService {

    private Logger logger = Logger.getLogger(AuthenServiceImpl.class);

    @Autowired
    private ModelMapper beanMapper;

    @Autowired
    private GenericDao genericDao;

    @Autowired
    private SpecificDao specificDao;

    @Autowired
    private PersonService userService;

    @Autowired
    private ValidationService validationService;

    @Override
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public LoginDto activate(String activationCode) throws BusinessException {
        logger.info("activate: " + activationCode);

        Map<String, Object> params = new HashMap<>();
        params.put("activationCode", activationCode);
        User user = genericDao.readUnique(User.class, params, false);
        if (user == null) {
            throw new BusinessException(BusinessException.AFFILIATE_ACTIVATION_LINK_INVALID,
                    BusinessException.AFFILIATE_ACTIVATION_LINK_INVALID_MESSAGE);
        }

        // validate activate token
        String[] parts = MyTokenUtil.decode(activationCode);
        if (parts.length != 2) {
            throw new BusinessException(BusinessException.AFFILIATE_ACTIVATION_LINK_INVALID,
                    BusinessException.AFFILIATE_ACTIVATION_LINK_INVALID_MESSAGE);
        }

        String email = parts[0];
        String randomToken = parts[1];
        if (StringUtils.isBlank(email) || StringUtils.isBlank(randomToken)) {
            throw new BusinessException(BusinessException.AFFILIATE_ACTIVATION_LINK_INVALID,
                    BusinessException.AFFILIATE_ACTIVATION_LINK_INVALID_MESSAGE);
        }

        if (!email.equals(user.getEmail())) {
            throw new BusinessException(BusinessException.AFFILIATE_ACTIVATION_LINK_INVALID,
                    BusinessException.AFFILIATE_ACTIVATION_LINK_INVALID_MESSAGE);
        }

        // validate user status
        if (User.STATUS_ACTIVE.equals(user.getStatus())) {
            throw new BusinessException(BusinessException.AFFILIATE_ACTIVATE_FAILED,
                    BusinessException.AFFILIATE_ACTIVATE_FAILED_MESSAGE);
        }

        // activate user
        user.setStatus(User.STATUS_ACTIVE);
        user.setActivationCode("");
        user = genericDao.update(user);

        Affiliate affiliate = genericDao.read(Affiliate.class, user.getId(), false);
        LoginDto loginDto = beanMapper.map(affiliate, LoginDto.class);
        beanMapper.map(user, loginDto);     // get email, roles, status

        return loginDto;
    }

    @Override
    public LoginDto authenticate(String email, String password) {
        logger.info("authenticate: " + email + "/" + password);

        Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        User user = genericDao.readUnique(User.class, params, true);
        if (user == null) {
            return null;
        }
        try {
            if (!BCrypt.checkpw(password, user.getPassword())) {
                return null;
            }
        }
        catch (IllegalArgumentException ex) {
            return null;
        }

        Person person = user.getPerson();
        LoginDto loginDto = beanMapper.map(person, LoginDto.class);
        beanMapper.map(user, loginDto);     // get email, roles, status
        // get subs package for SubsAdmin
        if (person.getDiscriminator().equals(PersonDto.TYPE_SUBS_ADMIN) ) {
            SubsAdmin loggedSubsAdmin = (SubsAdmin) person;
            PackageConfigApplied pca = genericDao.read(PackageConfigApplied.class, loggedSubsAdmin.getSubscriberId(), false);
            PackageConfigAppliedDto currentPackage = beanMapper.map(pca, PackageConfigAppliedDto.class);
            // add with SubsPackageConfig
            SubsPackageConfig spc = genericDao.read(SubsPackageConfig.class, loggedSubsAdmin.getSubscriberId(), false);
            currentPackage.setAffiliateCount(currentPackage.getAffiliateCount() + spc.getAffiliateCount());
            currentPackage.setChannelCount(currentPackage.getChannelCount() + spc.getChannelCount());
            if (!currentPackage.getHasVoucher() && spc.getHasVoucher()) {
                currentPackage.setHasVoucher(Boolean.TRUE);
            }
            loginDto.setCurrentPackage(currentPackage);
        }
        // get nickname, isPhoneVerified if Affiliate logs in
        else if (person.getDiscriminator().equals(PersonDto.TYPE_AFFILIATE) ) {
            Affiliate affiliate = (Affiliate) person;
            loginDto.setNickname(affiliate.getNickname());
            loginDto.setIsPhoneVerified(affiliate.getIsPhoneVerified());
        }
        // get subscriberDomainName if Channel logs in
        else if (person.getDiscriminator().equals(PersonDto.TYPE_CHANNEL) ) {
            Channel channel = (Channel) person;
            loginDto.setSubscriberDomainName(channel.getSubscriber().getDomainName());
        }

        return loginDto;
    }

    @Override
    @Transactional(value = "jdbcTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String deleteToken(String userId) {
        logger.info("deleteAdmin token of user '" + userId + "'");

        specificDao.deleteToken(userId);

        return userId;
    }

    @Override
    public UserDto getLoggedUser() throws BusinessException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }

        return (UserDto) authentication.getPrincipal();
    }

    @Override
    public UserDto getUserByEmail(String email) {
        logger.info("getUserByEmail: " + email);

        Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        User user = genericDao.readUnique(User.class, params, false);
        if (user == null) {
            return null;
        }

        return beanMapper.map(user, UserDto.class);
    }

    @Override
    @Transactional(value = "jdbcTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String insertToken(String userId, String token) {
        logger.info("insert token for user '" + userId + "'");

        int numberOfRowsAffected = specificDao.insertToken(userId, token);
        if (numberOfRowsAffected == 0) {
            logger.error("User '" + userId + "' not found!!!!!!");
        }

        return userId;
    }

}
