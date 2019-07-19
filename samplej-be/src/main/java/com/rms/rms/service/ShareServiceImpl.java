package com.rms.rms.service;

import com.rms.rms.common.CustomCriteria;
import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.cache.ChannelInCache;
import com.rms.rms.common.cache.ClickInfoInCache;
import com.rms.rms.common.cache.ShareInCache;
import com.rms.rms.common.config.properties.CacheServerProperties;
import com.rms.rms.common.constant.SystemRole;
import com.rms.rms.common.dto.ShareDto;
import com.rms.rms.common.dto.UserDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.exception.DataAccessException;
import com.rms.rms.common.exception.InvalidViewModelException;
import com.rms.rms.common.util.MyBeanUtil;
import com.rms.rms.common.util.MyDateUtil;
import com.rms.rms.common.util.MyJsonUtil;
import com.rms.rms.common.view_model.ClickInfoUpdateModel;
import com.rms.rms.common.view_model.ShareSearchCriteria;
import com.rms.rms.common.view_model.ShareStatsUpdateModel;
import com.rms.rms.persistence.GenericDao;
import com.rms.rms.persistence.SpecificDao;
import com.rms.rms.service.model.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * homertruong
 */

@Service
@Transactional(value = "transactionManager", propagation = Propagation.SUPPORTS)
public class ShareServiceImpl implements ShareService {

    private Logger logger = Logger.getLogger(ShareServiceImpl.class);

    @Autowired
    private ModelMapper beanMapper;

    @Autowired
    private CacheServerProperties cacheServerProperties;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

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
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN})
    public Long countClicksByChannelId(String channelId) throws BusinessException {
        logger.info("countClicksByChannelId: " + channelId);

        // validate biz rules
        Channel channel = validationService.getChannel(channelId, false);

        // do authorization
          // Admin can get Clicks from all Shares of the Channel
          // SubsAdmin can only get Clicks from Shares of the Channel whose Subscriber must be its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        String subscriberId = channel.getSubscriberId();
        boolean isSubsAdmin = SystemRole.hasSubsAdminRole(loggedUserDto.getRoles());
        if (isSubsAdmin) {
            authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);
        }

        return specificDao.countShareClicksByChannelId(channelId);
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN, SystemRole.ROLE_AFFILIATE})
    public List<Map<Date, Integer>> getClicksByDate() throws BusinessException {
        logger.info("getClicksByDate: ");

        // do authorization
          // Admin can get Clicks from all Shares
          // SubsAdmin can only get Clicks from Shares of its Subscriber
          // Affiliate can only get Clicks from its Shares
        UserDto loggedUserDto = authenService.getLoggedUser();
        boolean isSubsAdmin = SystemRole.hasSubsAdminRole(loggedUserDto.getRoles());
        boolean isAffiliate = SystemRole.hasAffiliateRole(loggedUserDto.getRoles());
        if (isSubsAdmin) {
            SubsAdmin loggedSubsAdmin = genericDao.read(SubsAdmin.class, loggedUserDto.getId(), false);
            return specificDao.getShareClicksByDateBySubscriberId(loggedSubsAdmin.getSubscriberId());
        }
        else if (isAffiliate) {
            return specificDao.getShareClicksByDateByAffiliateId(loggedUserDto.getId());
        }

        return specificDao.getShareClicksByDate();
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN})
    public List<Map<Date, Integer>> getClicksByDateByChannelId(String channelId) throws BusinessException {
        logger.info("getClicksByDateByChannelId: " + channelId);

        // validate biz rules
        Channel channel = validationService.getChannel(channelId, false);

        // do authorization
          // Admin can get Clicks from all Shares from the Channel
          // SubsAdmin can only get Clicks from Shares from the Channel whose Subscriber must be its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        String subscriberId = channel.getSubscriberId();
        boolean isSubsAdmin = SystemRole.hasSubsAdminRole(loggedUserDto.getRoles());
        if (isSubsAdmin) {
            authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);
        }

        return specificDao.getShareClicksByDateByChannelId(channelId);
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN})
    public List<Map<Date, Integer>> getClicksByDateBySubscriberId(String subscriberId) throws BusinessException {
        logger.info("getClicksByDateBySubscriberId: " + subscriberId);

        // validate biz rules
        validationService.getSubscriber(subscriberId, false);

        // do authorization
          // Admin can get Clicks from all Shares of the Subscriber
          // SubsAdmin can only get Clicks from Shares of its Subscriber who must be the Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        boolean isSubsAdmin = SystemRole.hasSubsAdminRole(loggedUserDto.getRoles());
        if (isSubsAdmin) {
            authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);
        }

        return specificDao.getShareClicksByDateBySubscriberId(subscriberId);
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN, SystemRole.ROLE_AFFILIATE})
    public SearchResult<ShareDto> search(SearchCriteria<ShareSearchCriteria> vmSearchCriteria, boolean withAssociation)
            throws BusinessException
    {
        logger.info("search: " + vmSearchCriteria.toString());

        // setup search criteria
        SearchCriteria<Share> searchCriteria = new SearchCriteria<>();
        beanMapper.map(vmSearchCriteria, searchCriteria);       // map sort, page info
        if (vmSearchCriteria.getCriteria() != null) {
            // map search info
            Share criteria = beanMapper.map(vmSearchCriteria.getCriteria(), Share.class);
            searchCriteria.setCriteria(criteria);
        }
        else {
            // no search info found, use default
            searchCriteria.setCriteria(new Share());
        }

        // do authorization
          // Admin can search all Shares
          // SubsAdmin can only search Shares of its Subscriber
          // Affiliate can only search its Shares
        SearchResult<Share> searchResult;
        SearchResult<ShareDto> dtoSearchResult;

        UserDto loggedUserDto = authenService.getLoggedUser();
        boolean isSubsAdmin = SystemRole.hasSubsAdminRole(loggedUserDto.getRoles());
        boolean isAffiliate = SystemRole.hasAffiliateRole(loggedUserDto.getRoles());

        if (isSubsAdmin) {
            SubsAdmin subsAdmin = genericDao.read(SubsAdmin.class, loggedUserDto.getId(), false);
            List<String> channelIds = specificDao.getChannelIdsBySubscriberId(subsAdmin.getSubscriberId());
            if (CollectionUtils.isEmpty(channelIds)) {
                searchResult = genericDao.generateEmptySearchResult(searchCriteria);
                dtoSearchResult = createDtoSearchResult(searchResult);
                return dtoSearchResult;
            }
            CustomCriteria customCriteria = searchCriteria.getCustomCriteria();
            customCriteria.setValue("channelId", channelIds);
        }
        else if (isAffiliate) {
            CustomCriteria customCriteria = searchCriteria.getCustomCriteria();
            customCriteria.setValue("affiliateId", loggedUserDto.getId());
        }

        // do biz action
        searchResult = genericDao.search(searchCriteria);
        triggerLazyLoad(searchResult.getList());
        dtoSearchResult = createDtoSearchResult(searchResult);

        return dtoSearchResult;
    }

    @Override
    @Secured({SystemRole.ROLE_CHANNEL})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ShareDto updateStats(ShareStatsUpdateModel statsUpdateModel) throws BusinessException {
        logger.info("updateStats: " + statsUpdateModel);

        // process view model
        String errors = statsUpdateModel.validate();
        if (errors != null) {
            throw new InvalidViewModelException(errors);
        }
        if (StringUtils.isBlank(statsUpdateModel.getClickInfo().getOs())) {
            statsUpdateModel.getClickInfo().setOs(ClickInfo.OS_WINDOWS);
        }
        UserDto loggedUserDto = authenService.getLoggedUser();
        Channel channel = validationService.getChannel(loggedUserDto.getId(), false);
        Affiliate affiliate = validationService.getAffiliateByNickname(statsUpdateModel.getNickname(), false);

        // validate biz rules
        ChannelInCache channelInCache = this.getChannel(
            String.format(CHANNEL_KEY_TEMPLATE, channel.getDomainName()), channel.getDomainName()
        );

        if (channelInCache == null) {
            throw new BusinessException(BusinessException.CHANNEL_NOT_FOUND,
                    String.format(BusinessException.CHANNEL_NOT_FOUND_MESSAGE, channel.getDomainName()));
        }

        // check Subscriber package
        String subscriberId = channel.getSubscriberId();
        PackageConfigApplied pca = validationService.getPackageConfigApplied(subscriberId, false);
        if (!pca.getHasShareStats()) {
            throw new BusinessException(BusinessException.SUBSCRIBER_NOT_HAVE_SHARE_STATS,
                    String.format(BusinessException.SUBSCRIBER_NOT_HAVE_SHARE_STATS_MESSAGE, subscriberId));
        }

        Date today = MyDateUtil.convertToUTCDate(new Date());
        if (pca.getUsageExpiredAt().before(today)) {
            throw new BusinessException(BusinessException.SUBSCRIBER_USAGE_EXPIRED,
                    String.format(BusinessException.SUBSCRIBER_USAGE_EXPIRED_MESSAGE, pca.getUsageExpiredAt()));
        }

        // get or create Share
        String affiliateId = affiliate.getId();
        String url = clearFbclidParameter(statsUpdateModel.getUrl());
        String currentDate = MyDateUtil.getYYMMDDString();
        String shareKey = String.format(SHARE_KEY_TEMPLATE, affiliateId, url, currentDate);

        // acquire the lock
        String randomValue = Math.random() + "";
        while ( true ) {
            logger.debug("Acquiring lock (" + randomValue + ") ...shareKey: " + shareKey);
            if ( acquireLock(shareKey, randomValue) ) {
                break;
            }

            logger.debug("Waiting for lock (" + randomValue + ") ...");
            Thread.currentThread();
            try {
                Thread.sleep(cacheServerProperties.getAcquireLockSleep());
            }
            catch (InterruptedException e) {
            }
        }

        // process the biz
        ShareInCache shareInCache = this.getShare(shareKey, affiliateId, url, currentDate);
        if ( shareInCache == null ) { // this url has not been shared before, create a new Share
            Share share = new Share();
            share.setChannel(channel);
            share.setChannelId(channelInCache.getId());
            share.setClickCount(1); // first click
            share.setUrl(url);
            share.setStatsDate(currentDate);
            share.setAffiliate(affiliate);

            Link link = new Link();
            Map<String, Object> postParams = new HashMap<>();
            postParams.put("url", url);
            Post post = genericDao.readFirst(Post.class, postParams);
            if (post != null) {     // Aff shared the url from Post
                beanMapper.map(post, link);
                link.setId(null);
            }
            else {  // Aff shared the arbitrary url
                link.setUrl(url);
            }
            genericDao.create(link);

            share.setLink(link);
            genericDao.create(share);

            shareInCache = beanMapper.map(share, ShareInCache.class);
        }
        else { // Share exists, increase click count by one in cache
            shareInCache.setClickCount(shareInCache.getClickCount() + 1);
        }

        // do biz action
        ClickInfoUpdateModel clickInfoUpdateModel = statsUpdateModel.getClickInfo();
        String clickInfoKey = String.format(CLICK_INFO_KEY_TEMPLATE,
            shareInCache.getId(),
            clickInfoUpdateModel.getCountry(),
            clickInfoUpdateModel.getDeviceType(),
            clickInfoUpdateModel.getOs(),
            currentDate
        );

        ClickInfoInCache clickInfoInCache = this.getClickInfo(
            clickInfoKey,
            shareInCache.getId(),
            clickInfoUpdateModel.getCountry(),
            clickInfoUpdateModel.getDeviceType(),
            clickInfoUpdateModel.getOs(),
            currentDate
        );

        if ( clickInfoInCache == null ) {
            ClickInfo newClickInfo = beanMapper.map(statsUpdateModel.getClickInfo(), ClickInfo.class);
            newClickInfo.setCount(1); // first click for new clickInfo
            newClickInfo.setShareId(shareInCache.getId());
            newClickInfo.setStatsDate(currentDate);

            genericDao.create(newClickInfo);
            clickInfoInCache = beanMapper.map(newClickInfo, ClickInfoInCache.class);
            shareInCache.getClickInfos().add(clickInfoInCache);
        }
        else {
            logger.info("increase count for Click Info Id ='" + clickInfoInCache.getId() + "'");
            clickInfoInCache.setCount(clickInfoInCache.getCount() + 1);

            // increase click for existing clickInfo in ShareInCache
            final String clickInfoInCacheId = clickInfoInCache.getId();
            shareInCache.getClickInfos().removeIf(cIInCache -> cIInCache.getId().equals(clickInfoInCacheId));
            shareInCache.getClickInfos().add(clickInfoInCache);
        }

        // save share to redis
        saveToRedis(shareKey, clickInfoKey, shareInCache, clickInfoInCache);

        // release lock
        releaseLockKey(shareKey, randomValue);

        return beanMapper.map(shareInCache, ShareDto.class);
    }

    @Override
    @Transactional(value = "jdbcTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateClickCount(String id, int clicks) throws BusinessException {
        logger.info("updateClickCount for Share Id =" + id + ", clicks = " + clicks);

        int numberOfRowsAffected = specificDao.updateShareClickCount(id, clicks);
        if (numberOfRowsAffected == 0) {
            logger.error("Share Id='" + id + "' not found!!!!!!");
        }
    }

    @Override
    @Transactional(value = "jdbcTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateClickInfoCount(String clickInfoId, int clicks) {
        logger.info("updateClickInfoCount for ClickInfo Id =" + clickInfoId + ", clicks = " + clicks);

        int numberOfRowsAffected = specificDao.updateClickInfoCount(clickInfoId, clicks);
        if (numberOfRowsAffected == 0) {
            logger.error("ClickInfo Id='" + clickInfoId + "' not found!!!!!!");
        }
    }


    // Utilities
    private boolean acquireLock (String key, String randomValue) {
        boolean acquired = false;
        String lockKey = "lock." + key;
        if ( redisTemplate.opsForValue().setIfAbsent(lockKey, randomValue) ) {
            redisTemplate.opsForValue().set(lockKey, randomValue, cacheServerProperties.getLockKeyExpired(), TimeUnit.SECONDS);
            acquired = true;
        }

        return acquired;
    }

    private SearchResult<ShareDto> createDtoSearchResult (SearchResult<Share> searchResult) {
        SearchResult<ShareDto> result = new SearchResult<>();
        beanMapper.map(searchResult, result);
        List<ShareDto> dtos = new ArrayList<>();
        for ( Share pdo : searchResult.getList() ) {
            dtos.add(beanMapper.map(pdo, ShareDto.class));
        }
        result.setList(dtos);

        return result;
    }

    private ChannelInCache getChannel (String key, String domainName) {
        String valueFromRedis = redisTemplate.opsForValue().get(key);
        if ( StringUtils.isNotBlank(valueFromRedis) ) {
            return MyJsonUtil.gson.fromJson(valueFromRedis, ChannelInCache.class);
        }

        Map<String, Object> params = new HashMap<>();
        params.put("domainName", domainName);

        Channel channel = genericDao.readUnique(Channel.class, params, false);
        if ( channel != null ) {
            ChannelInCache channelInCache = beanMapper.map(channel, ChannelInCache.class);
            redisTemplate.opsForValue().set(key, MyJsonUtil.gson.toJson(channelInCache));
            return channelInCache;
        }
        return null;
    }

    private ClickInfoInCache getClickInfo (String key, String shareId, String country, String deviceType, String os, String currentDate) {
        String valueFromRedis = redisTemplate.opsForValue().get(key);
        if ( StringUtils.isNotBlank(valueFromRedis) ) {
            return MyJsonUtil.gson.fromJson(valueFromRedis, ClickInfoInCache.class);
        }

        Map<String, Object> params = new HashMap<>();
        params.put("country", country);
        params.put("deviceType", deviceType);
        params.put("os", os);
        params.put("shareId", shareId);
        params.put("statsDate", currentDate);

        ClickInfo clickInfo = genericDao.readUnique(ClickInfo.class, params, false);
        if ( clickInfo != null ) {
            return beanMapper.map(clickInfo, ClickInfoInCache.class);
        }
        return null;
    }

    private ShareInCache getShare (String key, String affiliateId, String url, String currentDate) {
        String valueFromRedis = redisTemplate.opsForValue().get(key);
        if ( StringUtils.isNotBlank(valueFromRedis) ) {
            return MyJsonUtil.gson.fromJson(valueFromRedis, ShareInCache.class);
        }

        Map<String, Object> shareParams = new HashMap<>();
        shareParams.put("affiliateId", affiliateId);
        shareParams.put("url", url);
        shareParams.put("statsDate", currentDate);

        Share share = genericDao.readUnique(Share.class, shareParams, false);
        if ( share != null ) {
            return beanMapper.map(share, ShareInCache.class);
        }
        return null;
    }

    private void releaseLockKey (String key, String randomValue) {
        String lockKey = "lock." + key;
        String rv = redisTemplate.opsForValue().get(lockKey);
        if ( rv != null && rv.equals(randomValue) ) {
            redisTemplate.delete(lockKey);
        }
    }

    private void saveToRedis (String shareKey, String clickInfoKey, ShareInCache shareInCache, ClickInfoInCache clickInfoInCache) {
        redisTemplate.executePipelined(
            new SessionCallback() {
                @Override
                public Object execute (RedisOperations redisOperations) throws DataAccessException {
                    redisOperations.multi();

                    // save Share
                    redisOperations.opsForValue().set(shareKey, MyJsonUtil.gson.toJson(shareInCache),
                            cacheServerProperties.getClickExpired(), TimeUnit.SECONDS
                    );
                    redisOperations.opsForSet().add(RECENTLY_MODIFIED_SHARE_KEY_SET_ID, shareKey);

                    // save ClickInfo
                    redisOperations.opsForValue().set(clickInfoKey, MyJsonUtil.gson.toJson(clickInfoInCache),
                            cacheServerProperties.getClickExpired(), TimeUnit.SECONDS
                    );
                    redisOperations.opsForSet().add(RECENTLY_MODIFIED_CLICK_INFO_KEY_SET_ID, clickInfoKey);

                    return redisOperations.exec();
                }
            }
        );
    }

    private void triggerLazyLoad (List<Share> shares) {
        for ( Share pdo : shares ) {
            MyBeanUtil.triggerLazyLoad(pdo.getClickInfos());
        }
    }

    private String clearFbclidParameter(String url) {
        if (StringUtils.isBlank(url)) {
            return null;
        }

        if (url.contains("&fbclid")) {
            return url.split("&fbclid")[0];
        }
        else if (url.contains("/?fbclid")) {
            return url.split("/\\?fbclid")[0];
        }
        else if (url.contains("?fbclid")) {
            return url.split("\\?fbclid")[0];
        }
        else {
            return url;
        }
    }

}
