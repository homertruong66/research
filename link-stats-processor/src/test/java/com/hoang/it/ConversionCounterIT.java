package com.hoang.it;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hoang.lsp.core.GoalType;
import com.hoang.lsp.events.ConversionEvent;
import com.hoang.lsp.model.Constant;
import com.hoang.lsp.model.Link;
import com.hoang.lsp.model.LinkStats;
import com.hoang.lsp.utils.DateTimeUtils;

@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@ContextConfiguration(value = {"classpath*:intergration-test/integration-test-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ConversionCounterIT {

    @Autowired
    ConversionGateway conversionGateway;
    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    ConsumptionsDbUtilities consumptionsDbUtilities;

    @Autowired
    LinkDbUtilities linkDbUtilities;

    @Value("${assert.conversion.counter.sleep}")
    long conversionAssertionPostpone;

    Link link;

    @Before
    public void init() {
        this.link = this.linkDbUtilities.createRedirection(150L, new BigInteger("999999"));
        this.consumptionsDbUtilities.deleteLinkStatsFromArchiveDb(this.link.getAccountId(), this.link.getId());
        this.consumptionsDbUtilities.deleteLinkStatsFromTotalsDb(this.link.getAccountId(), this.link.getId());
    }

    @After
    public void tearDown() {
        this.consumptionsDbUtilities.deleteLinkStatsFromArchiveDb(this.link.getAccountId(), this.link.getId());
        this.consumptionsDbUtilities.deleteLinkStatsFromTotalsDb(this.link.getAccountId(), this.link.getId());
        this.linkDbUtilities.deleteRedirection(this.link.getId());
    }

    @SuppressWarnings("static-access")
    @Test
    public void testSendGoal1EventToQueueAndCount() throws InterruptedException {
        GoalType goalType = GoalType.GOAL_1;
        ConversionEvent event = createValidConversionEventForGoal(goalType);
        String goalHourlyKey = createGoalHourlyKey(event.getAccountId(), event.getRedirectionId(), event.getConvertedAt(), goalType);
        String goalTotalsKey = createGoalTotalsKey(event.getAccountId(), event.getRedirectionId(), goalType);
        clearRedisCacheForKey(Arrays.asList(goalHourlyKey, goalTotalsKey));

        sendNConversions(30, event);

        Thread.currentThread().sleep(conversionAssertionPostpone);
        assertEquals(Long.valueOf(30), getConversionValue(goalHourlyKey));
        assertEquals(Long.valueOf(30), getConversionValue(goalTotalsKey));
        LinkStats linkStatsFromArchive = this.consumptionsDbUtilities.queryLinkStats(event.getAccountId(), event.getRedirectionId(), "archive");
        assertEquals(Integer.valueOf(30), linkStatsFromArchive.getGoal1Increment());
        LinkStats linkStatsFromTotals = this.consumptionsDbUtilities.queryLinkStats(event.getAccountId(), event.getRedirectionId(), "totals");
        assertEquals(Integer.valueOf(30), linkStatsFromTotals.getGoal1Increment());
    }


    @SuppressWarnings("static-access")
    @Test
    public void testSendGoal2EventToQueueAndCount() throws InterruptedException {
        GoalType goalType = GoalType.GOAL_2;
        ConversionEvent event = createValidConversionEventForGoal(goalType);
        String goalHourlyKey = createGoalHourlyKey(event.getAccountId(), event.getRedirectionId(), event.getConvertedAt(), goalType);
        String goalTotalsKey = createGoalTotalsKey(event.getAccountId(), event.getRedirectionId(), goalType);
        clearRedisCacheForKey(Arrays.asList(goalHourlyKey, goalTotalsKey));
        sendNConversions(69, event);

        Thread.currentThread().sleep(conversionAssertionPostpone);
        assertEquals(Long.valueOf(69), getConversionValue(goalHourlyKey));
        assertEquals(Long.valueOf(69), getConversionValue(goalTotalsKey));
        LinkStats linkStatsFromArchive = this.consumptionsDbUtilities.queryLinkStats(event.getAccountId(), event.getRedirectionId(), "archive");
        assertEquals(Integer.valueOf(69), linkStatsFromArchive.getGoal2Increment());
        LinkStats linkStatsFromTotals = this.consumptionsDbUtilities.queryLinkStats(event.getAccountId(), event.getRedirectionId(), "totals");
        assertEquals(Integer.valueOf(69), linkStatsFromTotals.getGoal2Increment());
    }

    @SuppressWarnings("static-access")
    @Test
    public void testSendGoal3EventToQueueAndCount() throws InterruptedException {
        GoalType goalType = GoalType.GOAL_3;
        ConversionEvent event = createValidConversionEventForGoal(goalType);
        String goalHourlyKey = createGoalHourlyKey(event.getAccountId(), event.getRedirectionId(), event.getConvertedAt(), goalType);
        String goalTotalsKey = createGoalTotalsKey(event.getAccountId(), event.getRedirectionId(), goalType);
        clearRedisCacheForKey(Arrays.asList(goalHourlyKey, goalTotalsKey));
        sendNConversions(40, event);

        Thread.currentThread().sleep(conversionAssertionPostpone);
        assertEquals(Long.valueOf(40), getConversionValue(goalHourlyKey));
        assertEquals(Long.valueOf(40), getConversionValue(goalTotalsKey));
        LinkStats linkStatsFromArchive = this.consumptionsDbUtilities.queryLinkStats(event.getAccountId(), event.getRedirectionId(), "archive");
        assertEquals(Integer.valueOf(40), linkStatsFromArchive.getGoal3Increment());
        LinkStats linkStatsFromTotals = this.consumptionsDbUtilities.queryLinkStats(event.getAccountId(), event.getRedirectionId(), "totals");
        assertEquals(Integer.valueOf(40), linkStatsFromTotals.getGoal3Increment());
    }


    @SuppressWarnings("static-access")
    @Test
    public void testSendGoal4EventToQueueAndCount() throws InterruptedException {
        GoalType goalType = GoalType.GOAL_4;
        ConversionEvent event = createValidConversionEventForGoal(goalType);
        String goalHourlyKey = createGoalHourlyKey(event.getAccountId(), event.getRedirectionId(), event.getConvertedAt(), goalType);
        String goalTotalsKey = createGoalTotalsKey(event.getAccountId(), event.getRedirectionId(), goalType);
        clearRedisCacheForKey(Arrays.asList(goalHourlyKey, goalTotalsKey));
        sendNConversions(55, event);

        Thread.currentThread().sleep(conversionAssertionPostpone);
        assertEquals(Long.valueOf(55), getConversionValue(goalHourlyKey));
        assertEquals(Long.valueOf(55), getConversionValue(goalTotalsKey));
        LinkStats linkStatsFromArchive = this.consumptionsDbUtilities.queryLinkStats(event.getAccountId(), event.getRedirectionId(), "archive");
        assertEquals(Integer.valueOf(55), linkStatsFromArchive.getGoal4Increment());
        LinkStats linkStatsFromTotals = this.consumptionsDbUtilities.queryLinkStats(event.getAccountId(), event.getRedirectionId(), "totals");
        assertEquals(Integer.valueOf(55), linkStatsFromTotals.getGoal4Increment());
    }

    @SuppressWarnings("static-access")
    @Test
    public void testSendGoal5EventToQueueAndCount() throws InterruptedException {
        GoalType goalType = GoalType.GOAL_5;
        ConversionEvent event = createValidConversionEventForGoal(goalType);
        String goalHourlyKey = createGoalHourlyKey(event.getAccountId(), event.getRedirectionId(), event.getConvertedAt(), goalType);
        String goalTotalsKey = createGoalTotalsKey(event.getAccountId(), event.getRedirectionId(), goalType);
        clearRedisCacheForKey(Arrays.asList(goalHourlyKey, goalTotalsKey));
        sendNConversions(90, event);

        Thread.currentThread().sleep(conversionAssertionPostpone);
        assertEquals(Long.valueOf(90), getConversionValue(goalHourlyKey));
        assertEquals(Long.valueOf(90), getConversionValue(goalTotalsKey));
        LinkStats linkStatsFromArchive = this.consumptionsDbUtilities.queryLinkStats(event.getAccountId(), event.getRedirectionId(), "archive");
        assertEquals(Integer.valueOf(90), linkStatsFromArchive.getGoal5Increment());
        LinkStats linkStatsFromTotals = this.consumptionsDbUtilities.queryLinkStats(event.getAccountId(), event.getRedirectionId(), "totals");
        assertEquals(Integer.valueOf(90), linkStatsFromTotals.getGoal5Increment());
    }

    @SuppressWarnings("static-access")
    @Test
    public void testSendGoal6EventToQueueAndCount() throws InterruptedException {
        GoalType goalType = GoalType.GOAL_6;
        ConversionEvent event = createValidConversionEventForGoal(goalType);
        String goalHourlyKey = createGoalHourlyKey(event.getAccountId(), event.getRedirectionId(), event.getConvertedAt(), goalType);
        String goalTotalsKey = createGoalTotalsKey(event.getAccountId(), event.getRedirectionId(), goalType);
        clearRedisCacheForKey(Arrays.asList(goalHourlyKey, goalTotalsKey));
        sendNConversions(80, event);

        Thread.currentThread().sleep(conversionAssertionPostpone);
        assertEquals(Long.valueOf(80), getConversionValue(goalHourlyKey));
        assertEquals(Long.valueOf(80), getConversionValue(goalTotalsKey));
        LinkStats linkStatsFromArchive = this.consumptionsDbUtilities.queryLinkStats(event.getAccountId(), event.getRedirectionId(), "archive");
        assertEquals(Integer.valueOf(80), linkStatsFromArchive.getGoal6Increment());
        LinkStats linkStatsFromTotals = this.consumptionsDbUtilities.queryLinkStats(event.getAccountId(), event.getRedirectionId(), "totals");
        assertEquals(Integer.valueOf(80), linkStatsFromTotals.getGoal6Increment());
    }

    @SuppressWarnings("static-access")
    @Test
    public void testSendGoal7EventToQueueAndCount() throws InterruptedException {
        GoalType goalType = GoalType.GOAL_7;
        ConversionEvent event = createValidConversionEventForGoal(goalType);
        String goalHourlyKey = createGoalHourlyKey(event.getAccountId(), event.getRedirectionId(), event.getConvertedAt(), goalType);
        String goalTotalsKey = createGoalTotalsKey(event.getAccountId(), event.getRedirectionId(), goalType);
        clearRedisCacheForKey(Arrays.asList(goalHourlyKey, goalTotalsKey));
        sendNConversions(70, event);

        Thread.currentThread().sleep(conversionAssertionPostpone);
        assertEquals(Long.valueOf(70), getConversionValue(goalHourlyKey));
        assertEquals(Long.valueOf(70), getConversionValue(goalTotalsKey));
        LinkStats linkStatsFromArchive = this.consumptionsDbUtilities.queryLinkStats(event.getAccountId(), event.getRedirectionId(), "archive");
        assertEquals(Integer.valueOf(70), linkStatsFromArchive.getGoal7Increment());
        LinkStats linkStatsFromTotals = this.consumptionsDbUtilities.queryLinkStats(event.getAccountId(), event.getRedirectionId(), "totals");
        assertEquals(Integer.valueOf(70), linkStatsFromTotals.getGoal7Increment());
    }

    @SuppressWarnings("static-access")
    @Test
    public void testSendGoal8EventToQueueAndCount() throws InterruptedException {
        GoalType goalType = GoalType.GOAL_8;
        ConversionEvent event = createValidConversionEventForGoal(goalType);
        String goalHourlyKey = createGoalHourlyKey(event.getAccountId(), event.getRedirectionId(), event.getConvertedAt(), goalType);
        String goalTotalsKey = createGoalTotalsKey(event.getAccountId(), event.getRedirectionId(), goalType);
        clearRedisCacheForKey(Arrays.asList(goalHourlyKey, goalTotalsKey));
        sendNConversions(75, event);

        Thread.currentThread().sleep(conversionAssertionPostpone);
        assertEquals(Long.valueOf(75), getConversionValue(goalHourlyKey));
        assertEquals(Long.valueOf(75), getConversionValue(goalTotalsKey));
        LinkStats linkStatsFromArchive = this.consumptionsDbUtilities.queryLinkStats(event.getAccountId(), event.getRedirectionId(), "archive");
        assertEquals(Integer.valueOf(75), linkStatsFromArchive.getGoal8Increment());
        LinkStats linkStatsFromTotals = this.consumptionsDbUtilities.queryLinkStats(event.getAccountId(), event.getRedirectionId(), "totals");
        assertEquals(Integer.valueOf(75), linkStatsFromTotals.getGoal8Increment());
    }

    @SuppressWarnings("static-access")
    @Test
    public void testSendGoal9EventToQueueAndCount() throws InterruptedException {
        GoalType goalType = GoalType.GOAL_9;
        ConversionEvent event = createValidConversionEventForGoal(goalType);
        String goalHourlyKey = createGoalHourlyKey(event.getAccountId(), event.getRedirectionId(), event.getConvertedAt(), goalType);
        String goalTotalsKey = createGoalTotalsKey(event.getAccountId(), event.getRedirectionId(), goalType);
        clearRedisCacheForKey(Arrays.asList(goalHourlyKey, goalTotalsKey));
        sendNConversions(79, event);

        Thread.currentThread().sleep(conversionAssertionPostpone);
        assertEquals(Long.valueOf(79), getConversionValue(goalHourlyKey));
        assertEquals(Long.valueOf(79), getConversionValue(goalTotalsKey));
        LinkStats linkStatsFromArchive = this.consumptionsDbUtilities.queryLinkStats(event.getAccountId(), event.getRedirectionId(), "archive");
        assertEquals(Integer.valueOf(79), linkStatsFromArchive.getGoal9Increment());
        LinkStats linkStatsFromTotals = this.consumptionsDbUtilities.queryLinkStats(event.getAccountId(), event.getRedirectionId(), "totals");
        assertEquals(Integer.valueOf(79), linkStatsFromTotals.getGoal9Increment());
    }

    @SuppressWarnings("static-access")
    @Test
    public void testSendGoal10EventToQueueAndCount() throws InterruptedException {
        GoalType goalType = GoalType.GOAL_10;
        ConversionEvent event = createValidConversionEventForGoal(goalType);
        String goalHourlyKey = createGoalHourlyKey(event.getAccountId(), event.getRedirectionId(), event.getConvertedAt(), goalType);
        String goalTotalsKey = createGoalTotalsKey(event.getAccountId(), event.getRedirectionId(), goalType);
        clearRedisCacheForKey(Arrays.asList(goalHourlyKey, goalTotalsKey));
        sendNConversions(72, event);

        Thread.currentThread().sleep(conversionAssertionPostpone);
        assertEquals(Long.valueOf(72), getConversionValue(goalHourlyKey));
        assertEquals(Long.valueOf(72), getConversionValue(goalTotalsKey));
        LinkStats linkStatsFromArchive = this.consumptionsDbUtilities.queryLinkStats(event.getAccountId(), event.getRedirectionId(), "archive");
        assertEquals(Integer.valueOf(72), linkStatsFromArchive.getGoal10Increment());
        LinkStats linkStatsFromTotals = this.consumptionsDbUtilities.queryLinkStats(event.getAccountId(), event.getRedirectionId(), "totals");
        assertEquals(Integer.valueOf(72), linkStatsFromTotals.getGoal10Increment());
    }

    @SuppressWarnings("static-access")
    @Test
    public void testSendPageViewEventToQueueAndCount() throws InterruptedException {
        GoalType goalType = GoalType.PAGE_VIEW;
        ConversionEvent event = createValidConversionEventForGoal(goalType);
        String goalHourlyKey = createGoalHourlyKey(event.getAccountId(), event.getRedirectionId(), event.getConvertedAt(), goalType);
        String goalTotalsKey = createGoalTotalsKey(event.getAccountId(), event.getRedirectionId(), goalType);
        clearRedisCacheForKey(Arrays.asList(goalHourlyKey, goalTotalsKey));
        sendNConversions(150, event);

        Thread.currentThread().sleep(conversionAssertionPostpone);
        assertEquals(Long.valueOf(150), getConversionValue(goalHourlyKey));
        assertEquals(Long.valueOf(150), getConversionValue(goalTotalsKey));
        LinkStats linkStatsFromArchive = this.consumptionsDbUtilities.queryLinkStats(event.getAccountId(), event.getRedirectionId(), "archive");
        assertEquals(Integer.valueOf(150), linkStatsFromArchive.getPageViewsIncrement());
        LinkStats linkStatsFromTotals = this.consumptionsDbUtilities.queryLinkStats(event.getAccountId(), event.getRedirectionId(), "totals");
        assertEquals(Integer.valueOf(150), linkStatsFromTotals.getPageViewsIncrement());
    }

    @SuppressWarnings("static-access")
    @Test
    public void testSendGoalValue1EventToQueueAndCount() throws InterruptedException {
        GoalType goalType = GoalType.GOAL_1;
        Long goalValue = 99L;
        ConversionEvent event = createValidConversionEventForGoalValue(goalType, goalValue);
        String goalHourlyKey = createGoalHourlyKey(event.getAccountId(), event.getRedirectionId(), event.getConvertedAt(), goalType);
        String goalTotalsKey = createGoalTotalsKey(event.getAccountId(), event.getRedirectionId(), goalType);
        String goalValueHourlyKey = createGoalValueHourlyKey(event.getAccountId(), event.getRedirectionId(), event.getConvertedAt(), goalType);
        String goalValueTotalsKey = createGoalValueTotalsKey(event.getAccountId(), event.getRedirectionId(), goalType);
        clearRedisCacheForKey(Arrays.asList(goalHourlyKey, goalTotalsKey, goalValueHourlyKey, goalValueTotalsKey));
        sendNConversions(30, event);

        Thread.currentThread().sleep(conversionAssertionPostpone);
        assertEquals(Long.valueOf(30), getConversionValue(goalHourlyKey));
        assertEquals(Long.valueOf(30), getConversionValue(goalTotalsKey));
        assertEquals(Long.valueOf(99 * 30), getConversionValue(goalValueHourlyKey));
        assertEquals(Long.valueOf(99 * 30), getConversionValue(goalValueTotalsKey));
        LinkStats linkStatsFromArchive = this.consumptionsDbUtilities.queryLinkStats(event.getAccountId(), event.getRedirectionId(), "archive");
        assertEquals(Integer.valueOf(99 * 30), linkStatsFromArchive.getGoal1ValueIncrement());
        LinkStats linkStatsFromTotals = this.consumptionsDbUtilities.queryLinkStats(event.getAccountId(), event.getRedirectionId(), "totals");
        assertEquals(Integer.valueOf(99 * 30), linkStatsFromTotals.getGoal1ValueIncrement());
    }

    @SuppressWarnings("static-access")
    @Test
    public void testSendGoalValue2EventToQueueAndCount() throws InterruptedException {
        GoalType goalType = GoalType.GOAL_2;
        Long goalValue = 199L;
        ConversionEvent event = createValidConversionEventForGoalValue(goalType, goalValue);
        String goalHourlyKey = createGoalHourlyKey(event.getAccountId(), event.getRedirectionId(), event.getConvertedAt(), goalType);
        String goalTotalsKey = createGoalTotalsKey(event.getAccountId(), event.getRedirectionId(), goalType);
        String goalValueHourlyKey = createGoalValueHourlyKey(event.getAccountId(), event.getRedirectionId(), event.getConvertedAt(), goalType);
        String goalValueTotalsKey = createGoalValueTotalsKey(event.getAccountId(), event.getRedirectionId(), goalType);
        clearRedisCacheForKey(Arrays.asList(goalHourlyKey, goalTotalsKey, goalValueHourlyKey, goalValueTotalsKey));
        sendNConversions(50, event);

        Thread.currentThread().sleep(conversionAssertionPostpone);
        assertEquals(Long.valueOf(50), getConversionValue(goalHourlyKey));
        assertEquals(Long.valueOf(50), getConversionValue(goalTotalsKey));
        assertEquals(Long.valueOf(199 * 50), getConversionValue(goalValueHourlyKey));
        assertEquals(Long.valueOf(199 * 50), getConversionValue(goalValueTotalsKey));
        LinkStats linkStatsFromArchive = this.consumptionsDbUtilities.queryLinkStats(event.getAccountId(), event.getRedirectionId(), "archive");
        assertEquals(Integer.valueOf(199 * 50), linkStatsFromArchive.getGoal2ValueIncrement());
        LinkStats linkStatsFromTotals = this.consumptionsDbUtilities.queryLinkStats(event.getAccountId(), event.getRedirectionId(), "totals");
        assertEquals(Integer.valueOf(199 * 50), linkStatsFromTotals.getGoal2ValueIncrement());
    }

    @SuppressWarnings("static-access")
    @Test
    public void testSendGoalValue3EventToQueueAndCount() throws InterruptedException {
        GoalType goalType = GoalType.GOAL_3;
        Long goalValue = 299L;
        ConversionEvent event = createValidConversionEventForGoalValue(goalType, goalValue);
        String goalHourlyKey = createGoalHourlyKey(event.getAccountId(), event.getRedirectionId(), event.getConvertedAt(), goalType);
        String goalTotalsKey = createGoalTotalsKey(event.getAccountId(), event.getRedirectionId(), goalType);
        String goalValueHourlyKey = createGoalValueHourlyKey(event.getAccountId(), event.getRedirectionId(), event.getConvertedAt(), goalType);
        String goalValueTotalsKey = createGoalValueTotalsKey(event.getAccountId(), event.getRedirectionId(), goalType);
        clearRedisCacheForKey(Arrays.asList(goalHourlyKey, goalTotalsKey, goalValueHourlyKey, goalValueTotalsKey));
        sendNConversions(60, event);

        Thread.currentThread().sleep(conversionAssertionPostpone);
        assertEquals(Long.valueOf(60), getConversionValue(goalHourlyKey));
        assertEquals(Long.valueOf(60), getConversionValue(goalTotalsKey));
        assertEquals(Long.valueOf(299 * 60), getConversionValue(goalValueHourlyKey));
        assertEquals(Long.valueOf(299 * 60), getConversionValue(goalValueTotalsKey));
        LinkStats linkStatsFromArchive = this.consumptionsDbUtilities.queryLinkStats(event.getAccountId(), event.getRedirectionId(), "archive");
        assertEquals(Integer.valueOf(299 * 60), linkStatsFromArchive.getGoal3ValueIncrement());
        LinkStats linkStatsFromTotals = this.consumptionsDbUtilities.queryLinkStats(event.getAccountId(), event.getRedirectionId(), "totals");
        assertEquals(Integer.valueOf(299 * 60), linkStatsFromTotals.getGoal3ValueIncrement());
    }

    @SuppressWarnings("static-access")
    @Test
    public void testSendGoalValue4EventToQueueAndCount() throws InterruptedException {
        GoalType goalType = GoalType.GOAL_4;
        Long goalValue = 399L;
        ConversionEvent event = createValidConversionEventForGoalValue(goalType, goalValue);
        String goalHourlyKey = createGoalHourlyKey(event.getAccountId(), event.getRedirectionId(), event.getConvertedAt(), goalType);
        String goalTotalsKey = createGoalTotalsKey(event.getAccountId(), event.getRedirectionId(), goalType);
        String goalValueHourlyKey = createGoalValueHourlyKey(event.getAccountId(), event.getRedirectionId(), event.getConvertedAt(), goalType);
        String goalValueTotalsKey = createGoalValueTotalsKey(event.getAccountId(), event.getRedirectionId(), goalType);
        clearRedisCacheForKey(Arrays.asList(goalHourlyKey, goalTotalsKey, goalValueHourlyKey, goalValueTotalsKey));
        sendNConversions(70, event);

        Thread.currentThread().sleep(conversionAssertionPostpone);
        assertEquals(Long.valueOf(70), getConversionValue(goalHourlyKey));
        assertEquals(Long.valueOf(70), getConversionValue(goalTotalsKey));
        assertEquals(Long.valueOf(399 * 70), getConversionValue(goalValueHourlyKey));
        assertEquals(Long.valueOf(399 * 70), getConversionValue(goalValueTotalsKey));
        LinkStats linkStatsFromArchive = this.consumptionsDbUtilities.queryLinkStats(event.getAccountId(), event.getRedirectionId(), "archive");
        assertEquals(Integer.valueOf(399 * 70), linkStatsFromArchive.getGoal4ValueIncrement());
        LinkStats linkStatsFromTotals = this.consumptionsDbUtilities.queryLinkStats(event.getAccountId(), event.getRedirectionId(), "totals");
        assertEquals(Integer.valueOf(399 * 70), linkStatsFromTotals.getGoal4ValueIncrement());
    }

    @SuppressWarnings("static-access")
    @Test
    public void testSendGoalValue5EventToQueueAndCount() throws InterruptedException {
        GoalType goalType = GoalType.GOAL_5;
        Long goalValue = 499L;
        ConversionEvent event = createValidConversionEventForGoalValue(goalType, goalValue);
        String goalHourlyKey = createGoalHourlyKey(event.getAccountId(), event.getRedirectionId(), event.getConvertedAt(), goalType);
        String goalTotalsKey = createGoalTotalsKey(event.getAccountId(), event.getRedirectionId(), goalType);
        String goalValueHourlyKey = createGoalValueHourlyKey(event.getAccountId(), event.getRedirectionId(), event.getConvertedAt(), goalType);
        String goalValueTotalsKey = createGoalValueTotalsKey(event.getAccountId(), event.getRedirectionId(), goalType);
        clearRedisCacheForKey(Arrays.asList(goalHourlyKey, goalTotalsKey, goalValueHourlyKey, goalValueTotalsKey));
        sendNConversions(80, event);

        Thread.currentThread().sleep(conversionAssertionPostpone);
        assertEquals(Long.valueOf(80), getConversionValue(goalHourlyKey));
        assertEquals(Long.valueOf(80), getConversionValue(goalTotalsKey));
        assertEquals(Long.valueOf(499 * 80), getConversionValue(goalValueHourlyKey));
        assertEquals(Long.valueOf(499 * 80), getConversionValue(goalValueTotalsKey));
        LinkStats linkStatsFromArchive = this.consumptionsDbUtilities.queryLinkStats(event.getAccountId(), event.getRedirectionId(), "archive");
        assertEquals(Integer.valueOf(499 * 80), linkStatsFromArchive.getGoal5ValueIncrement());
        LinkStats linkStatsFromTotals = this.consumptionsDbUtilities.queryLinkStats(event.getAccountId(), event.getRedirectionId(), "totals");
        assertEquals(Integer.valueOf(499 * 80), linkStatsFromTotals.getGoal5ValueIncrement());
    }

    @SuppressWarnings("static-access")
    @Test
    public void testSendGoalValue6EventToQueueAndCount() throws InterruptedException {
        GoalType goalType = GoalType.GOAL_6;
        Long goalValue = 599L;
        ConversionEvent event = createValidConversionEventForGoalValue(goalType, goalValue);
        String goalHourlyKey = createGoalHourlyKey(event.getAccountId(), event.getRedirectionId(), event.getConvertedAt(), goalType);
        String goalTotalsKey = createGoalTotalsKey(event.getAccountId(), event.getRedirectionId(), goalType);
        String goalValueHourlyKey = createGoalValueHourlyKey(event.getAccountId(), event.getRedirectionId(), event.getConvertedAt(), goalType);
        String goalValueTotalsKey = createGoalValueTotalsKey(event.getAccountId(), event.getRedirectionId(), goalType);
        clearRedisCacheForKey(Arrays.asList(goalHourlyKey, goalTotalsKey, goalValueHourlyKey, goalValueTotalsKey));
        sendNConversions(90, event);

        Thread.currentThread().sleep(conversionAssertionPostpone);
        assertEquals(Long.valueOf(90), getConversionValue(goalHourlyKey));
        assertEquals(Long.valueOf(90), getConversionValue(goalTotalsKey));
        assertEquals(Long.valueOf(599 * 90), getConversionValue(goalValueHourlyKey));
        assertEquals(Long.valueOf(599 * 90), getConversionValue(goalValueTotalsKey));
        LinkStats linkStatsFromArchive = this.consumptionsDbUtilities.queryLinkStats(event.getAccountId(), event.getRedirectionId(), "archive");
        assertEquals(Integer.valueOf(599 * 90), linkStatsFromArchive.getGoal6ValueIncrement());
        LinkStats linkStatsFromTotals = this.consumptionsDbUtilities.queryLinkStats(event.getAccountId(), event.getRedirectionId(), "totals");
        assertEquals(Integer.valueOf(599 * 90), linkStatsFromTotals.getGoal6ValueIncrement());
    }

    @SuppressWarnings("static-access")
    @Test
    public void testSendGoalValue7EventToQueueAndCount() throws InterruptedException {
        GoalType goalType = GoalType.GOAL_7;
        Long goalValue = 699L;
        ConversionEvent event = createValidConversionEventForGoalValue(goalType, goalValue);
        String goalHourlyKey = createGoalHourlyKey(event.getAccountId(), event.getRedirectionId(), event.getConvertedAt(), goalType);
        String goalTotalsKey = createGoalTotalsKey(event.getAccountId(), event.getRedirectionId(), goalType);
        String goalValueHourlyKey = createGoalValueHourlyKey(event.getAccountId(), event.getRedirectionId(), event.getConvertedAt(), goalType);
        String goalValueTotalsKey = createGoalValueTotalsKey(event.getAccountId(), event.getRedirectionId(), goalType);
        clearRedisCacheForKey(Arrays.asList(goalHourlyKey, goalTotalsKey, goalValueHourlyKey, goalValueTotalsKey));
        sendNConversions(100, event);

        Thread.currentThread().sleep(conversionAssertionPostpone);
        assertEquals(Long.valueOf(100), getConversionValue(goalHourlyKey));
        assertEquals(Long.valueOf(100), getConversionValue(goalTotalsKey));
        assertEquals(Long.valueOf(699 * 100), getConversionValue(goalValueHourlyKey));
        assertEquals(Long.valueOf(699 * 100), getConversionValue(goalValueTotalsKey));
        LinkStats linkStatsFromArchive = this.consumptionsDbUtilities.queryLinkStats(event.getAccountId(), event.getRedirectionId(), "archive");
        assertEquals(Integer.valueOf(699 * 100), linkStatsFromArchive.getGoal7ValueIncrement());
        LinkStats linkStatsFromTotals = this.consumptionsDbUtilities.queryLinkStats(event.getAccountId(), event.getRedirectionId(), "totals");
        assertEquals(Integer.valueOf(699 * 100), linkStatsFromTotals.getGoal7ValueIncrement());
    }

    @SuppressWarnings("static-access")
    @Test
    public void testSendGoalValue8EventToQueueAndCount() throws InterruptedException {
        GoalType goalType = GoalType.GOAL_8;
        Long goalValue = 799L;
        ConversionEvent event = createValidConversionEventForGoalValue(goalType, goalValue);
        String goalHourlyKey = createGoalHourlyKey(event.getAccountId(), event.getRedirectionId(), event.getConvertedAt(), goalType);
        String goalTotalsKey = createGoalTotalsKey(event.getAccountId(), event.getRedirectionId(), goalType);
        String goalValueHourlyKey = createGoalValueHourlyKey(event.getAccountId(), event.getRedirectionId(), event.getConvertedAt(), goalType);
        String goalValueTotalsKey = createGoalValueTotalsKey(event.getAccountId(), event.getRedirectionId(), goalType);
        clearRedisCacheForKey(Arrays.asList(goalHourlyKey, goalTotalsKey, goalValueHourlyKey, goalValueTotalsKey));
        sendNConversions(110, event);

        Thread.currentThread().sleep(conversionAssertionPostpone);
        assertEquals(Long.valueOf(110), getConversionValue(goalHourlyKey));
        assertEquals(Long.valueOf(110), getConversionValue(goalTotalsKey));
        assertEquals(Long.valueOf(799 * 110), getConversionValue(goalValueHourlyKey));
        assertEquals(Long.valueOf(799 * 110), getConversionValue(goalValueTotalsKey));
        LinkStats linkStatsFromArchive = this.consumptionsDbUtilities.queryLinkStats(event.getAccountId(), event.getRedirectionId(), "archive");
        assertEquals(Integer.valueOf(799 * 110), linkStatsFromArchive.getGoal8ValueIncrement());
        LinkStats linkStatsFromTotals = this.consumptionsDbUtilities.queryLinkStats(event.getAccountId(), event.getRedirectionId(), "totals");
        assertEquals(Integer.valueOf(799 * 110), linkStatsFromTotals.getGoal8ValueIncrement());
    }

    @SuppressWarnings("static-access")
    @Test
    public void testSendGoalValue9EventToQueueAndCount() throws InterruptedException {
        GoalType goalType = GoalType.GOAL_9;
        Long goalValue = 899L;
        ConversionEvent event = createValidConversionEventForGoalValue(goalType, goalValue);
        String goalHourlyKey = createGoalHourlyKey(event.getAccountId(), event.getRedirectionId(), event.getConvertedAt(), goalType);
        String goalTotalsKey = createGoalTotalsKey(event.getAccountId(), event.getRedirectionId(), goalType);
        String goalValueHourlyKey = createGoalValueHourlyKey(event.getAccountId(), event.getRedirectionId(), event.getConvertedAt(), goalType);
        String goalValueTotalsKey = createGoalValueTotalsKey(event.getAccountId(), event.getRedirectionId(), goalType);
        clearRedisCacheForKey(Arrays.asList(goalHourlyKey, goalTotalsKey, goalValueHourlyKey, goalValueTotalsKey));
        sendNConversions(120, event);

        Thread.currentThread().sleep(conversionAssertionPostpone);
        assertEquals(Long.valueOf(120), getConversionValue(goalHourlyKey));
        assertEquals(Long.valueOf(120), getConversionValue(goalTotalsKey));
        assertEquals(Long.valueOf(899 * 120), getConversionValue(goalValueHourlyKey));
        assertEquals(Long.valueOf(899 * 120), getConversionValue(goalValueTotalsKey));
        LinkStats linkStatsFromArchive = this.consumptionsDbUtilities.queryLinkStats(event.getAccountId(), event.getRedirectionId(), "archive");
        assertEquals(Integer.valueOf(899 * 120), linkStatsFromArchive.getGoal9ValueIncrement());
        LinkStats linkStatsFromTotals = this.consumptionsDbUtilities.queryLinkStats(event.getAccountId(), event.getRedirectionId(), "totals");
        assertEquals(Integer.valueOf(899 * 120), linkStatsFromTotals.getGoal9ValueIncrement());
    }

    @SuppressWarnings("static-access")
    @Test
    public void testSendGoalValue10EventToQueueAndCount() throws InterruptedException {
        GoalType goalType = GoalType.GOAL_10;
        Long goalValue = 999L;
        ConversionEvent event = createValidConversionEventForGoalValue(goalType, goalValue);
        String goalHourlyKey = createGoalHourlyKey(event.getAccountId(), event.getRedirectionId(), event.getConvertedAt(), goalType);
        String goalTotalsKey = createGoalTotalsKey(event.getAccountId(), event.getRedirectionId(), goalType);
        String goalValueHourlyKey = createGoalValueHourlyKey(event.getAccountId(), event.getRedirectionId(), event.getConvertedAt(), goalType);
        String goalValueTotalsKey = createGoalValueTotalsKey(event.getAccountId(), event.getRedirectionId(), goalType);
        clearRedisCacheForKey(Arrays.asList(goalHourlyKey, goalTotalsKey, goalValueHourlyKey, goalValueTotalsKey));
        sendNConversions(130, event);

        Thread.currentThread().sleep(conversionAssertionPostpone);
        assertEquals(Long.valueOf(130), getConversionValue(goalHourlyKey));
        assertEquals(Long.valueOf(130), getConversionValue(goalTotalsKey));
        assertEquals(Long.valueOf(999 * 130), getConversionValue(goalValueHourlyKey));
        assertEquals(Long.valueOf(999 * 130), getConversionValue(goalValueTotalsKey));
        LinkStats linkStatsFromArchive = this.consumptionsDbUtilities.queryLinkStats(event.getAccountId(), event.getRedirectionId(), "archive");
        assertEquals(Integer.valueOf(999 * 130), linkStatsFromArchive.getGoal10ValueIncrement());
        LinkStats linkStatsFromTotals = this.consumptionsDbUtilities.queryLinkStats(event.getAccountId(), event.getRedirectionId(), "totals");
        assertEquals(Integer.valueOf(999 * 130), linkStatsFromTotals.getGoal10ValueIncrement());
    }

    private ConversionEvent createValidConversionEventForGoal(GoalType goalType) {
        ConversionEvent event = new ConversionEvent();
        event.setAccountId(this.link.getAccountId());
        event.setRedirectionId(this.link.getId());
        event.setConvertedAt(new GregorianCalendar(2015, 5, 10, 14, 10, 12)); // 2015-06-10 14:10:12
        event.setType(goalType);
        return event;
    }

    private ConversionEvent createValidConversionEventForGoalValue(GoalType goalType, Long goalValue) {
        ConversionEvent event = new ConversionEvent();
        event.setAccountId(this.link.getAccountId());
        event.setRedirectionId(this.link.getId());
        event.setConvertedAt(new GregorianCalendar(2015, 5, 10, 14, 10, 12)); // 2015-06-10 14:10:12
        event.setType(goalType);
        event.setValue(goalValue);
        return event;
    }

    private void sendNConversions(int n, ConversionEvent event) {
        for (int i = 0; i < n; i++) {
            conversionGateway.sendConversion(event);
        }
    }

    private void clearRedisCacheForKey(List<String> keys) {
        redisTemplate.delete(keys);
    }

    private Long getConversionValue(String key) {
        String valueFromCache = getValueFromCache(key);
        if (valueFromCache != null) {
            return Long.parseLong(valueFromCache);
        }
        return null;
    }

    private String getValueFromCache(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    private String createGoalHourlyKey(Long accountId, BigInteger redirectionId, Calendar createdAt, GoalType goalType) {
        return Constant.HOURLY_PREFIX + "::" + goalType.getValue() + "::" + accountId + "::" + DateTimeUtils.roundTimestampToEarliestHour(createdAt) + "::" + redirectionId;
    }

    private String createGoalTotalsKey(Long accountId, BigInteger redirectionId, GoalType goalType) {
        return Constant.TOTALS_PREFIX + "::" + goalType.getValue() + "::" + accountId + "::" + redirectionId;
    }

    private String createGoalValueHourlyKey(Long accountId, BigInteger redirectionId, Calendar createdAt, GoalType goalType) {
        return Constant.HOURLY_PREFIX + "::" + getGoalLabelValue(goalType) + "::" + accountId + "::" + DateTimeUtils.roundTimestampToEarliestHour(createdAt) + "::" + redirectionId;
    }

    private String createGoalValueTotalsKey(Long accountId, BigInteger redirectionId, GoalType goalType) {
        return Constant.TOTALS_PREFIX + "::" + getGoalLabelValue(goalType) + "::" + accountId + "::" + redirectionId;
    }

    private String getGoalLabelValue(final GoalType goalType) {
        return goalType.getValue() + "_value";
    }

}
