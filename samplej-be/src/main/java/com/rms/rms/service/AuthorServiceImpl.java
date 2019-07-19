package com.rms.rms.service;

import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.persistence.GenericDao;
import com.rms.rms.service.model.Agent;
import com.rms.rms.service.model.Channel;
import com.rms.rms.service.model.SubsAdmin;
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
public class AuthorServiceImpl implements AuthorService {

    private Logger logger = Logger.getLogger(AuthorServiceImpl.class);

    @Autowired
    private ValidationService validationService;

    @Autowired
    private GenericDao genericDao;

    @Override
    public Agent checkAffiliateAndSubscriber(String loggedAffiliateId, String subscriberId) throws BusinessException {
        Map<String, Object> params = new HashMap<>();
        params.put("affiliateId", loggedAffiliateId);
        params.put("subscriberId", subscriberId);
        Agent agent = genericDao.readUnique(Agent.class, params,false);
        if (agent == null) {   // loggedAffiliate does not belong to input Subscriber
            throw new BusinessException(BusinessException.AUTHORIZATION_LOGGED_USER_NOT_BELONG_TO_SUBSCRIBER,
                String.format(BusinessException.AUTHORIZATION_LOGGED_USER_NOT_BELONG_TO_SUBSCRIBER_MESSAGE,
                              loggedAffiliateId, subscriberId));
        }

        return agent;
    }

    @Override
    public Channel checkChannelAndAffiliate(String loggedChannelId, String affiliateId) throws BusinessException {
        // get subscriberId from Channel
        Channel loggedChannel = validationService.getChannel(loggedChannelId, false);
        String loggedSubscriberId = loggedChannel.getSubscriberId();

        // get Agent
        Map<String, Object> params = new HashMap<>();
        params.put("subscriberId", loggedSubscriberId);
        params.put("affiliateId", affiliateId);
        Agent agent = genericDao.readUnique(Agent.class, params, false);
        if (agent == null) {
            throw new BusinessException(BusinessException.AUTHORIZATION_LOGGED_CHANNEL_AFFILIATE_NOT_SAME_SUBSCRIBER,
                    String.format(BusinessException.AUTHORIZATION_LOGGED_CHANNEL_AFFILIATE_NOT_SAME_SUBSCRIBER_MESSAGE,
                                  loggedChannelId, affiliateId));
        }

        return loggedChannel;
    }

    @Override
    public Channel checkChannelAndSubscriber(String loggedChannelId, String subscriberId) throws BusinessException {
        Channel loggedChannel = validationService.getChannel(loggedChannelId, false);
        String loggedSubscriberId = loggedChannel.getSubscriberId();
        if (!loggedSubscriberId.equals(subscriberId)) {
            throw new BusinessException(BusinessException.AUTHORIZATION_LOGGED_USER_NOT_BELONG_TO_SUBSCRIBER,
                String.format(BusinessException.AUTHORIZATION_LOGGED_USER_NOT_BELONG_TO_SUBSCRIBER_MESSAGE,
                              loggedChannelId, subscriberId));
        }

        return loggedChannel;
    }

    @Override
    public SubsAdmin checkSubsAdminAndAffiliate(String loggedSubsAdminId, String affiliateId) throws BusinessException {
        // get subscriberId from SubsAdmin
        SubsAdmin loggedSubsAdmin = validationService.getSubsAdmin(loggedSubsAdminId, false);
        String loggedSubscriberId = loggedSubsAdmin.getSubscriberId();

        // get Agent
        Map<String, Object> params = new HashMap<>();
        params.put("subscriberId", loggedSubscriberId);
        params.put("affiliateId", affiliateId);
        Agent agent = genericDao.readUnique(Agent.class, params, false);
        if (agent == null) {
            throw new BusinessException(BusinessException.AUTHORIZATION_LOGGED_SUBS_ADMIN_AFFILIATE_NOT_SAME_SUBSCRIBER,
                String.format(BusinessException.AUTHORIZATION_LOGGED_SUBS_ADMIN_AFFILIATE_NOT_SAME_SUBSCRIBER_MESSAGE,
                              loggedSubsAdminId, affiliateId));
        }

        return loggedSubsAdmin;
    }

    @Override
    public SubsAdmin checkSubsAdminAndSubscriber(String loggedSubsAdminId, String subscriberId) throws BusinessException {
        SubsAdmin loggedSubsAdmin = validationService.getSubsAdmin(loggedSubsAdminId, false);
        String loggedSubscriberId = loggedSubsAdmin.getSubscriberId();
        if (!loggedSubscriberId.equals(subscriberId)) {
            throw new BusinessException(BusinessException.AUTHORIZATION_LOGGED_USER_NOT_BELONG_TO_SUBSCRIBER,
                String.format(BusinessException.AUTHORIZATION_LOGGED_USER_NOT_BELONG_TO_SUBSCRIBER_MESSAGE,
                             loggedSubsAdminId, subscriberId));
        }

        return loggedSubsAdmin;
    }
}
