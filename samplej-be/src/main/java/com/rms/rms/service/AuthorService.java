package com.rms.rms.service;

import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.service.model.Agent;
import com.rms.rms.service.model.Channel;
import com.rms.rms.service.model.SubsAdmin;

/**
 * homertruong
 */

public interface AuthorService {

    Agent checkAffiliateAndSubscriber(String loggedAffiliateId, String subscriberId) throws BusinessException;

    Channel checkChannelAndAffiliate(String loggedChannelId, String affiliateId) throws BusinessException;

    Channel checkChannelAndSubscriber(String loggedChannelId, String subscriberId) throws BusinessException;

    SubsAdmin checkSubsAdminAndAffiliate(String loggedSubsAdminId, String affiliateId) throws BusinessException;

    SubsAdmin checkSubsAdminAndSubscriber(String loggedSubsAdminId, String subscriberId) throws BusinessException;

}