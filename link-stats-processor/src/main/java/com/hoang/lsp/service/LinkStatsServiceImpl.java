package com.hoang.lsp.service;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.hoang.lsp.core.GoalType;
import com.hoang.lsp.core.MetricType;
import com.hoang.lsp.dao.LinkStatsCache;
import com.hoang.lsp.dao.LinkStatsDao;
import com.hoang.lsp.events.ClickEvent;
import com.hoang.lsp.events.ConversionEvent;
import com.hoang.lsp.exception.BusinessException;
import com.hoang.lsp.model.IncrementEvent;
import com.hoang.lsp.model.LinkStats;
import com.hoang.lsp.task.ClickMessageProcessingTask;
import com.hoang.lsp.task.ConversionMessageProcessingTask;
import com.hoang.lsp.task.LoaderExceptionHandlerTask;
import com.hoang.lsp.utils.ConvertUtils;

@Service("linkStatsService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class LinkStatsServiceImpl implements LinkStatsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LinkStatsServiceImpl.class);

    @Autowired
    private LinkStatsCache linkStatsCache;

    @Autowired
    private LinkStatsDao linkStatsDao;

    @Autowired
    private LinkService linkService;

    @Autowired
    private ThreadPoolService threadPoolService;

    @Qualifier("exceptionHandlerThreadPool")
    @Autowired
    private ThreadPoolExecutor exceptionHandlerThreadPool;

    @Value("${exception.handler.max.retries}")
    private int maxRetries;

    @Override
    public void doIncreaseClick (ClickEvent clickEvent) {
        Long accountId = clickEvent.getAccountId();
        BigInteger redirectionId = clickEvent.getRedirectionId();
        Calendar createdAt = clickEvent.getCreatedAt();
        this.linkStatsCache.updateLastClickedAt(accountId, redirectionId, createdAt);
        this.linkStatsCache.increaseClickIncrement(accountId, redirectionId, createdAt);
    }

    @Override
    public void doIncreaseConversion (ConversionEvent conversionEvent) {
        Long accountId = conversionEvent.getAccountId();
        BigInteger redirectionId = conversionEvent.getRedirectionId();
        Calendar convertedAt = conversionEvent.getConvertedAt();
        GoalType goalType = conversionEvent.getType();
        Long goalValue = conversionEvent.getValue();
        this.linkStatsCache.updateLastClickedAt(accountId, redirectionId, convertedAt);
        this.linkStatsCache.increaseConversionIncrement(accountId, redirectionId, convertedAt, goalType, goalValue);
    }

    @Override
    public void loadClickStats () throws Exception {
        LOGGER.info("loading recently modified click sets from Redis and save share stats to Consumptions DB...");
        MetricType metricType = MetricType.CLICK;

        // get recently modified key set
        Set<String> hourlyKeySet = linkStatsCache.getRecentlyModifiedSet(metricType, true);
        Set<String> totalKeySet = linkStatsCache.getRecentlyModifiedSet(metricType, false);

        // get recently modified share stats list and save to Consumptions DB
        List<LinkStats> hourlyRMLinkStatsList = linkStatsCache.getLinkStatsList(metricType, hourlyKeySet, true);
        List<LinkStats> totalsRMLinkStatsList = linkStatsCache.getLinkStatsList(metricType, totalKeySet, false);
        if ( hourlyRMLinkStatsList != null && hourlyRMLinkStatsList.size() > 0 ) {

            if ( LOGGER.isDebugEnabled() ) {
                LOGGER.debug("hourly recently modified share stats list: ");
                printLinkStatsList(hourlyRMLinkStatsList);

                LOGGER.debug("totals recently modified share stats list: ");
                printLinkStatsList(totalsRMLinkStatsList);
            }

            setMetaData(totalsRMLinkStatsList);

//            Map<BigInteger, List<LinkStats>> linksStats = loadAncestors(hourlyRMLinkStatsList);
//            Map<BigInteger, List<LinkStats>> descendantLinksStats = loadEmptyDescendants(hourlyRMLinkStatsList);
//            for (Map.Entry<BigInteger, List<LinkStats>> descendantLinksStat : descendantLinksStats.entrySet()) {
//                linksStats.get(descendantLinksStat.getKey()).addAll(descendantLinksStat.getValue());
//            }
//            if ( MapUtils.isNotEmpty(linksStats) ) {
//                int retry = 0;
//                do {
//                    try {
//                        saveIncrements(linksStats);
//                        break;
//                    }
//                    catch (Exception e) {
//                        retry++;
//                        if ( retry == maxRetries ) {
//                            runLinkStatsExceptionHandler(linksStats);
//                        }
//                    }
//                }
//                while ( retry < maxRetries );
//            }

            saveConsumptionLinkStatsBatch(metricType, hourlyRMLinkStatsList, totalsRMLinkStatsList);
        }
    }

    @Override
    public void loadConversionStats (GoalType goalType) throws Exception {
        LOGGER.info("loading recently modified " + goalType + " sets from Redis and save share stats to Consumptions DB......");
        final MetricType metricType = ConvertUtils.convertToMetricType(goalType);

        // get recently modified key set
        Set<String> hourlyKeySet = linkStatsCache.getRecentlyModifiedSet(metricType, true);
        Set<String> totalsKeySet = linkStatsCache.getRecentlyModifiedSet(metricType, false);

        // get recently modified share stats list and save to Consumptions DB
        List<LinkStats> hourlyRMLinkStatsList = linkStatsCache.getLinkStatsList(metricType, hourlyKeySet, true);
        List<LinkStats> totalsRMLinkStatsList = linkStatsCache.getLinkStatsList(metricType, totalsKeySet, false);
        if ( hourlyRMLinkStatsList != null && hourlyRMLinkStatsList.size() > 0 ) {
            if ( LOGGER.isDebugEnabled() ) {
                LOGGER.debug("hourly recently modified share stats list: ");
                printLinkStatsList(hourlyRMLinkStatsList);

                LOGGER.debug("totals recently modified share stats list: ");
                printLinkStatsList(totalsRMLinkStatsList);
            }
            setMetaData(totalsRMLinkStatsList);

//            Map<BigInteger, List<LinkStats>> linksStats = loadAncestors(hourlyRMLinkStatsList);
//            Map<BigInteger, List<LinkStats>> descendantLinksStats = loadEmptyDescendants(hourlyRMLinkStatsList);
//            for (Map.Entry<BigInteger, List<LinkStats>> descendantLinksStat : descendantLinksStats.entrySet()) {
//                linksStats.get(descendantLinksStat.getKey()).addAll(descendantLinksStat.getValue());
//            }
//            if ( MapUtils.isNotEmpty(linksStats) ) {
//                int retry = 0;
//                do {
//                    try {
//                        saveIncrements(linksStats);
//                        break;
//                    }
//                    catch (Exception e) {
//                        retry++;
//                        if ( retry == maxRetries ) {
//                            runLinkStatsExceptionHandler(linksStats);
//                        }
//                    }
//                }
//                while ( retry < maxRetries );
//            }

            saveConsumptionLinkStatsBatch(metricType, hourlyRMLinkStatsList, totalsRMLinkStatsList);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
    public void loadLastClickAt () throws Exception {
        LOGGER.info("loading recently modified lastClickedAt sets from Redis and lastClickedAts to Consumptions DB...");
        final MetricType metricType = MetricType.LAST_CLICKED_AT;

        // get recently modified key set
        Set<String> lastClickAtKeySet = linkStatsCache.getRecentlyModifiedSet(metricType, false);

        // get recently modified share stats list and save to Consumptions DB
        List<LinkStats> lastClickAtLinkStatsList = linkStatsCache.getLinkStatsList(metricType, lastClickAtKeySet, false);
        if ( lastClickAtKeySet != null && lastClickAtKeySet.size() > 0 ) {

            if ( LOGGER.isDebugEnabled() ) {
                LOGGER.debug("hourly recently modified share stats list: ");
                printLinkStatsList(lastClickAtLinkStatsList);
            }

            setMetaData(lastClickAtLinkStatsList);

            linkStatsDao.saveTotalsLinkStatsBatch(metricType, lastClickAtLinkStatsList);
        }
    }

    @Override
    public void increaseClick (ClickEvent clickEvent) throws BusinessException {
        if ( isValidClickEvent(clickEvent) ) {
            long startTime = System.currentTimeMillis();
            ClickMessageProcessingTask task = new ClickMessageProcessingTask(clickEvent, startTime);
            this.threadPoolService.execute(task);
        }
        else {
            throw new BusinessException(
                BusinessException.INVALID_EVENT,
                "Invalid ClickEvent '" + clickEvent.getDomain() + "_" + clickEvent.getStub() + "': rejected"
            );
        }
    }

    @Override
    public void increaseConversion (ConversionEvent conversionEvent) throws BusinessException {
        if ( isValidConversionEvent(conversionEvent) ) {
            long startTime = System.currentTimeMillis();
            ConversionMessageProcessingTask task = new ConversionMessageProcessingTask(conversionEvent, startTime);
            this.threadPoolService.execute(task);
        }
        else {
            throw new BusinessException(
                BusinessException.INVALID_EVENT,
                "Invalid ConversionEvent '" + conversionEvent.getAwesmUrl() + "': rejected"
            );
        }
    }

    ////// 	Utilities 	//////
    private boolean isValidClickEvent (ClickEvent clickEvent) {
        boolean isValid = true;
        if ( clickEvent.getAccountId() == null || clickEvent.getRedirectionId() == null || clickEvent.getCreatedAt() == null ) {
            isValid = false;
        }

        return isValid;
    }

    private boolean isValidConversionEvent (ConversionEvent conversionEvent) {
        boolean isValid = true;
        if ( conversionEvent.getAccountId() == null || conversionEvent.getRedirectionId() == null || conversionEvent.getConvertedAt() == null || conversionEvent.getType() == null ) {
            isValid = false;
        }

        return isValid;
    }

    private void printLinkStatsList (List<LinkStats> linkStatsList) {
        for (LinkStats linkStats : linkStatsList) {
            if ( linkStats == null ) {
                continue;
            }
            LOGGER.debug(linkStats.toString());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    private void saveIncrements (Map<BigInteger, List<LinkStats>> linksStats) {
        LOGGER.debug("save increment values with transaction support");
        linkStatsDao.saveIncrements(linksStats);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    private void saveConsumptionLinkStatsBatch (MetricType metricType, List<LinkStats> hourlyRMLinkStatsList,
                                                List<LinkStats> totalsRMLinkStatsList) {
        LOGGER.debug("save consumption values with transaction support");
        linkStatsDao.saveHourlyLinkStatsBatch(metricType, hourlyRMLinkStatsList);
        linkStatsDao.saveTotalsLinkStatsBatch(metricType, totalsRMLinkStatsList);
    }

    private void setMetaData (List<LinkStats> linkStatsList) throws Exception {
        if ( CollectionUtils.isEmpty(linkStatsList) ) {
            return;
        }

        for (LinkStats linkStats : linkStatsList) {
//            Link link = linkService.getFromCache(linkStats.getRedirectionId(), linkStats.getAccountId(), true);
//            if ( link == null ) {
//                LOGGER.error("RedirectionId=" + linkStats.getRedirectionId() + " is no longer existent.");
//                continue;
//            }
//
//            linkStats.setDomainId(link.getDomainId());
//            linkStats.setCampaignId(link.getCampaignId());
//            linkStats.setOriginalUrlId(link.getOriginalUrlId());
//            linkStats.setParentId(link.getParentId());
//            linkStats.setCreateType(link.getCreateType());
//            linkStats.setAwesmId(link.getDomain() + "_" + link.getStub());
//            linkStats.setShareType(link.getShareType());
//            linkStats.setUserId(link.getUserId());
//            linkStats.setNotes(link.getNotes());
//            linkStats.setTag2(link.getTag2());
//            linkStats.setTag3(link.getTag3());
//            linkStats.setTag4(link.getTag4());
//            linkStats.setTag5(link.getTag5());
//            linkStats.setSourceTag(link.getSourceTag());
//            linkStats.setSharerId(link.getSnowballId());
//
//            String postId = link.getPostId();
//            if ( postId != null ) {
//                try {
//                    linkStats.setPostId(Long.parseLong(postId));
//                }
//                catch (Exception ex) {
//                    LOGGER.error("Failed to set PostId/Mention to LinkStats with redirectionId=" + linkStats.getRedirectionId(), ex);
//                }
//            }
//
//            String conversion1 = link.getConversion1();
//            if ( conversion1 != null ) {
//                try {
//                    linkStats.setAccountUserId(Long.parseLong(conversion1));
//                }
//                catch (Exception ex) {
//                    LOGGER.error("Failed to set AccountUserId to LinkStats with redirectionId=" + linkStats.getRedirectionId(), ex);
//                }
//            }
        }
    }

    private Map<BigInteger, List<LinkStats>> loadEmptyDescendants (List<LinkStats> linkStatsList) throws Exception {
        Map<BigInteger, List<LinkStats>> result = new HashMap<>();
        for (LinkStats linkStats : linkStatsList) {
//            List<BigInteger> ids = linkDao.fetchDescendants(linkStats.getRedirectionId());
//            if ( CollectionUtils.isNotEmpty(ids) ) {
//                List<LinkStats> descendants = new ArrayList<>();
//                for (BigInteger id : ids) {
//                    LinkStats descendant = new LinkStats();
//                    descendant.setRedirectionId(id);
//                    descendant.setClickedDate(linkStats.getClickedDate());
//                    descendant.setAccountId(linkStats.getAccountId());
//                    descendants.add(descendant);
//                }
//                setMetaData(descendants);
//                result.put(linkStats.getRedirectionId(), descendants);
//            }
        }
        return result;
    }

    private void runLinkStatsExceptionHandler (Map<BigInteger, List<LinkStats>> linksStats) {
        for (List<LinkStats> linkStatsList : linksStats.values()) {
            List<IncrementEvent> incrementEventList = Lists.transform(
                linkStatsList, IncrementEvent.getLinkStatsToIncrementEventFunction());
            LoaderExceptionHandlerTask task = new LoaderExceptionHandlerTask(incrementEventList);
            exceptionHandlerThreadPool.submit(task);
        }
    }

}
