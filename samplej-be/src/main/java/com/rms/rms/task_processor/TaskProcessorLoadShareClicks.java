package com.rms.rms.task_processor;

import com.rms.rms.common.cache.ClickInfoInCache;
import com.rms.rms.common.cache.ShareInCache;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.util.MyJsonUtil;
import com.rms.rms.service.ShareService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ExecutorService;

@Component
@EnableScheduling
public class TaskProcessorLoadShareClicks {

    private Logger logger = Logger.getLogger(TaskProcessorLoadShareClicks.class);

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ShareService shareService;

    @Autowired
    @Qualifier("taskExecutorService")
    private ExecutorService taskExecutorService;

    @Scheduled(fixedDelayString = "${application.loader-scheduler}")
    public void process() {
        logger.info("............ TaskProcessorLoadShareClicks is running ............");
        updateClickCount();
        logger.info("............ TaskProcessorLoadShareClicks is ending .............");
    }


    // Utilities
    private void updateClickCount() {
        // process recently modified keys from 2 sets
        Set<String> shareKeys = redisTemplate.opsForSet().members(ShareService.RECENTLY_MODIFIED_SHARE_KEY_SET_ID);
        Set<String> clickInfoKeys = redisTemplate.opsForSet().members(ShareService.RECENTLY_MODIFIED_CLICK_INFO_KEY_SET_ID);

        // delete recently modified keys in 2 sets to make sure we don't hit db to reprocess them
        // we might not care about losing recently modified keys, they can be added when any new click comes
        redisTemplate.delete(Arrays.asList(ShareService.RECENTLY_MODIFIED_SHARE_KEY_SET_ID,
                ShareService.RECENTLY_MODIFIED_CLICK_INFO_KEY_SET_ID));

        logger.info("updateClickCount, shareKeys : " + shareKeys.size() + ", clickInfoKeys : " + clickInfoKeys.size());

        Runnable shareClickTask = () -> {
            shareKeys.forEach(shareKey -> {
                String value = redisTemplate.opsForValue().get(shareKey);
                if( StringUtils.isNotBlank(value) ) {
                    ShareInCache shareInCache = MyJsonUtil.gson.fromJson(value, ShareInCache.class);
                    try {
                        shareService.updateClickCount(shareInCache.getId(), shareInCache.getClickCount());
                    } catch( BusinessException e ) {
                        logger.error("Save data from cache to db failed, shareKey:" + shareKey, e);
                    }
                }

            });
        };

        Runnable clickInfoTask = () -> {
            clickInfoKeys.forEach(clickInfoKey -> {
                String value = redisTemplate.opsForValue().get(clickInfoKey);
                if( StringUtils.isNotBlank(value) ) {
                    ClickInfoInCache clickInfoInCache = MyJsonUtil.gson.fromJson(value, ClickInfoInCache.class);
                    shareService.updateClickInfoCount(clickInfoInCache.getId(), clickInfoInCache.getCount());
                }

            });
        };

        taskExecutorService.submit(shareClickTask);
        taskExecutorService.submit(clickInfoTask);
    }

}
