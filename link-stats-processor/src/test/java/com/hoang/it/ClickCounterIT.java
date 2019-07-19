package com.hoang.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hoang.lsp.events.ClickEvent;
import com.hoang.lsp.model.Constant;
import com.hoang.lsp.utils.DateTimeUtils;

@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@ContextConfiguration(value = {"classpath*:intergration-test/integration-test-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ClickCounterIT {

    @Autowired
    ClickGateway clickGateway;
    @Autowired
    RedisTemplate<String, String> redisTemplate;
    @Value("${assert.clickcounter.sleep}")
    long clickAssertionPostpone;

    @SuppressWarnings("static-access")
    @Test
    public void testSendClickEventToQueueAndCount() throws InterruptedException {
        ClickEvent event = createValidClickEvent();
        String hourlyKey = createClickHourlyKey(event.getAccountId(), event.getRedirectionId(), event.getCreatedAt());
        String totalsKey = createClickTotalsKey(event.getAccountId(), event.getRedirectionId());
        clearRedisCacheForKey(Arrays.asList(hourlyKey, totalsKey));
        sendNClicks(50, event);

        Thread.currentThread().sleep(clickAssertionPostpone);
        assertEquals(Integer.valueOf(50), getHourlyClick(hourlyKey));
        assertEquals(Integer.valueOf(50), getTotalsClick(totalsKey));
    }

    @SuppressWarnings("static-access")
    @Test
    public void testSendClickEventsWithBotsFlagToQueueAndVerify() throws InterruptedException {
        ClickEvent event = createBotsClickEventWithBotsFlag();
        String hourlyKey = createClickHourlyKey(event.getAccountId(), event.getRedirectionId(), event.getCreatedAt());
        String totalsKey = createClickTotalsKey(event.getAccountId(), event.getRedirectionId());
        clearRedisCacheForKey(Arrays.asList(hourlyKey, totalsKey));
        sendNClicks(20, event);

        Thread.currentThread().sleep(clickAssertionPostpone);
        assertNull(getHourlyClick(hourlyKey));
        assertNull(getTotalsClick(totalsKey));
    }

    @SuppressWarnings("static-access")
    @Test
    public void testSendClickEventsWithUserAgentBlacklistToQueueAndVerify() throws InterruptedException {
        ClickEvent event = createClickEventWithBlacklistUserAgent();
        String hourlyKey = createClickHourlyKey(event.getAccountId(), event.getRedirectionId(), event.getCreatedAt());
        String totalsKey = createClickTotalsKey(event.getAccountId(), event.getRedirectionId());
        clearRedisCacheForKey(Arrays.asList(hourlyKey, totalsKey));
        sendNClicks(20, event);

        Thread.currentThread().sleep(clickAssertionPostpone);
        assertNull(getHourlyClick(hourlyKey));
        assertNull(getTotalsClick(totalsKey));
    }

    @SuppressWarnings("static-access")
    @Test
    public void testSendClickEventsWithIPBlacklistToQueueAndVerify() throws InterruptedException {
        ClickEvent event = createClickEventWithBlacklistIP();
        String hourlyKey = createClickHourlyKey(event.getAccountId(), event.getRedirectionId(), event.getCreatedAt());
        String totalsKey = createClickTotalsKey(event.getAccountId(), event.getRedirectionId());
        clearRedisCacheForKey(Arrays.asList(hourlyKey, totalsKey));
        sendNClicks(20, event);

        Thread.currentThread().sleep(clickAssertionPostpone);
        assertNull(getHourlyClick(hourlyKey));
        assertNull(getTotalsClick(totalsKey));
    }

    @SuppressWarnings("static-access")
    @Test
    public void testSendParallelValidClickEventsAndBotsEvent() throws InterruptedException {
        ClickEvent validEvent = createValidClickEvent();
        ClickEvent botsEvent = createClickEventWithBlacklistUserAgent();
        String hourlyKey = createClickHourlyKey(validEvent.getAccountId(), validEvent.getRedirectionId(), validEvent.getCreatedAt());
        String totalsKey = createClickTotalsKey(validEvent.getAccountId(), validEvent.getRedirectionId());
        clearRedisCacheForKey(Arrays.asList(hourlyKey, totalsKey));
        sendNClicks(20, validEvent);
        sendNClicks(20, botsEvent);

        Thread.currentThread().sleep(clickAssertionPostpone);
        assertEquals(Integer.valueOf(20), getHourlyClick(hourlyKey));
        assertEquals(Integer.valueOf(20), getTotalsClick(totalsKey));
    }

    private ClickEvent createValidClickEvent() {
        ClickEvent event = new ClickEvent();
        event.setAccountId(100L);
        event.setRedirectionId(new BigInteger("1000"));
        event.setBotFlag(0);
        event.setRemoteAddress("192.168.10.2");
        event.setHttpUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.130 Safari/537.36");
        event.setCreatedAt(new GregorianCalendar(2015, 5, 10, 14, 10, 12)); // 2015-06-10 14:10:12
        return event;
    }

    private ClickEvent createBotsClickEventWithBotsFlag() {
        ClickEvent event = createValidClickEvent();
        event.setBotFlag(1);
        return event;
    }

    private ClickEvent createClickEventWithBlacklistUserAgent() {
        ClickEvent event = createValidClickEvent();
        event.setHttpUserAgent("Mozilla/5.0 (compatible; woriobot support [at] worio [dot] com +http://worio.com)");
        return event;
    }

    private ClickEvent createClickEventWithBlacklistIP() {
        ClickEvent event = createValidClickEvent();
        event.setRemoteAddress("142.4.216.19");;
        return event;
    }

    private void sendNClicks(int n, ClickEvent event) {
        for (int i = 0; i < n; i++) {
            clickGateway.sendClick(event);
        }
    }

    private void clearRedisCacheForKey(List<String> keys) {
        redisTemplate.delete(keys);
    }

    private Integer getHourlyClick(String hourlyClickKey) {
        String valueFromCache = getValueFromCache(hourlyClickKey);
        if (valueFromCache != null) {
            return Integer.parseInt(valueFromCache);
        }
        return null;
    }

    private Integer getTotalsClick(String totalsClickKey) {
        String valueFromCache = getValueFromCache(totalsClickKey);
        if (valueFromCache != null) {
            return Integer.parseInt(valueFromCache);
        }
        return null;
    }

    private String getValueFromCache(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    private String createClickHourlyKey(Long accountId, BigInteger redirectionId, Calendar createdAt) {
        return Constant.HOURLY_PREFIX + "::clicks::" + accountId + "::" + DateTimeUtils.roundTimestampToEarliestHour(createdAt) + "::" + redirectionId;
    }

    private String createClickTotalsKey(Long accountId, BigInteger redirectionId) {
        return Constant.TOTALS_PREFIX + "::clicks::" + accountId + "::" + redirectionId;
    }

}
