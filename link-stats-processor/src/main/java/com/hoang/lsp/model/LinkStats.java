package com.hoang.lsp.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Calendar;

public class LinkStats implements Serializable {

    private static final long serialVersionUID = 1L;

    // Primary key
    // Hourly: accountId-hour-redirectionId
    // Total: accountId-redirectionId
    private Long       accountId;
    private Calendar   hour;
    private BigInteger redirectionId;

    // Metadata ref
    private Long       domainId;
    private BigInteger campaignId;
    private BigInteger originalUrlId;
    private Long       accountUserId;
    private BigInteger parentId;
    private Long       postId;

    // Stats metrics
    private Integer mentions;
    private Integer reach;
    private Integer pageViewsIncrement;
    private Integer clicksIncrement;
    private Integer goal1Increment;
    private Integer goal1ValueIncrement;
    private Integer goal2Increment;
    private Integer goal2ValueIncrement;
    private Integer goal3Increment;
    private Integer goal3ValueIncrement;
    private Integer goal4Increment;
    private Integer goal4ValueIncrement;
    private Integer goal5Increment;
    private Integer goal5ValueIncrement;
    private Integer goal6Increment;
    private Integer goal6ValueIncrement;
    private Integer goal7Increment;
    private Integer goal7ValueIncrement;
    private Integer goal8Increment;
    private Integer goal8ValueIncrement;
    private Integer goal9Increment;
    private Integer goal9ValueIncrement;
    private Integer goal10Increment;
    private Integer goal10ValueIncrement;

    // increments metric
    private String  clickedDate;
    private Integer multiGenClicksIncrement;

    private Integer multiGenGoal1Increment;
    private Integer multiGenGoal1ValueIncrement;

    private Integer multiGenGoal2Increment;
    private Integer multiGenGoal2ValueIncrement;

    private Integer multiGenGoal3Increment;
    private Integer multiGenGoal3ValueIncrement;

    private Integer multiGenGoal4Increment;
    private Integer multiGenGoal4ValueIncrement;

    private Integer multiGenGoal5Increment;
    private Integer multiGenGoal5ValueIncrement;

    private Integer multiGenGoal6Increment;
    private Integer multiGenGoal6ValueIncrement;

    private Integer multiGenGoal7Increment;
    private Integer multiGenGoal7ValueIncrement;

    private Integer multiGenGoal8Increment;
    private Integer multiGenGoal8ValueIncrement;

    private Integer multiGenGoal9Increment;
    private Integer multiGenGoal9ValueIncrement;

    private Integer multiGenGoal10Increment;
    private Integer multiGenGoal10ValueIncrement;

    private Integer multiGenPageviewsIncrement;

    private Integer firstGenConversions;
    private Integer multiGenConversions;

    // Others
    private String   awesmId;
    private String   redirectUrl;
    private String   channel;
    private String   campaign;
    private String   shareType;
    private String   createType;
    private String   sharerId;
    private String   userId;
    private String   notes;
    private Integer  reactions1;
    private Integer  reactions2;
    private Calendar sharedAt;
    private Calendar updatedAt;
    private Calendar createdAt;
    private String   tag2;
    private String   tag3;
    private String   tag4;
    private String   tag5;
    private String   sourceTag;
    private Calendar lastClickedAt;

    public Long getAccountId () {
        return accountId;
    }

    public void setAccountId (Long accountId) {
        this.accountId = accountId;
    }

    public Calendar getHour () {
        return hour;
    }

    public void setHour (Calendar hour) {
        this.hour = hour;
    }

    public BigInteger getRedirectionId () {
        return redirectionId;
    }

    public void setRedirectionId (BigInteger redirectionId) {
        this.redirectionId = redirectionId;
    }

    public Long getAccountUserId () {
        return accountUserId;
    }

    public void setAccountUserId (Long accountUserId) {
        this.accountUserId = accountUserId;
    }

    public BigInteger getCampaignId () {
        return campaignId;
    }

    public void setCampaignId (BigInteger campaignId) {
        this.campaignId = campaignId;
    }

    public Long getDomainId () {
        return domainId;
    }

    public void setDomainId (Long domainId) {
        this.domainId = domainId;
    }

    public BigInteger getOriginalUrlId () {
        return originalUrlId;
    }

    public void setOriginalUrlId (BigInteger originalUrlId) {
        this.originalUrlId = originalUrlId;
    }

    public BigInteger getParentId () {
        return parentId;
    }

    public void setParentId (BigInteger parentId) {
        this.parentId = parentId;
    }

    public Long getPostId () {
        return postId;
    }

    public void setPostId (Long postId) {
        this.postId = postId;
    }

    public Integer getMentions () {
        return mentions;
    }

    public void setMentions (Integer mentions) {
        this.mentions = mentions;
    }

    public Integer getReach () {
        return reach;
    }

    public void setReach (Integer reach) {
        this.reach = reach;
    }

    public Integer getPageViewsIncrement () {
        return pageViewsIncrement;
    }

    public void setPageViewsIncrement (Integer pageViewsIncrement) {
        this.pageViewsIncrement = pageViewsIncrement;
    }

    public Integer getClicksIncrement () {
        return clicksIncrement;
    }

    public void setClicksIncrement (Integer clicksIncrement) {
        this.clicksIncrement = clicksIncrement;
    }

    public Integer getGoal1Increment () {
        return goal1Increment;
    }

    public void setGoal1Increment (Integer goal1Increment) {
        this.goal1Increment = goal1Increment;
    }

    public Integer getGoal1ValueIncrement () {
        return goal1ValueIncrement;
    }

    public void setGoal1ValueIncrement (Integer goal1ValueIncrement) {
        this.goal1ValueIncrement = goal1ValueIncrement;
    }

    public Integer getGoal2Increment () {
        return goal2Increment;
    }

    public void setGoal2Increment (Integer goal2Increment) {
        this.goal2Increment = goal2Increment;
    }

    public Integer getGoal2ValueIncrement () {
        return goal2ValueIncrement;
    }

    public void setGoal2ValueIncrement (Integer goal2ValueIncrement) {
        this.goal2ValueIncrement = goal2ValueIncrement;
    }

    public Integer getGoal3Increment () {
        return goal3Increment;
    }

    public void setGoal3Increment (Integer goal3Increment) {
        this.goal3Increment = goal3Increment;
    }

    public Integer getGoal3ValueIncrement () {
        return goal3ValueIncrement;
    }

    public void setGoal3ValueIncrement (Integer goal3ValueIncrement) {
        this.goal3ValueIncrement = goal3ValueIncrement;
    }

    public Integer getGoal4Increment () {
        return goal4Increment;
    }

    public void setGoal4Increment (Integer goal4Increment) {
        this.goal4Increment = goal4Increment;
    }

    public Integer getGoal4ValueIncrement () {
        return goal4ValueIncrement;
    }

    public void setGoal4ValueIncrement (Integer goal4ValueIncrement) {
        this.goal4ValueIncrement = goal4ValueIncrement;
    }

    public Integer getGoal5Increment () {
        return goal5Increment;
    }

    public void setGoal5Increment (Integer goal5Increment) {
        this.goal5Increment = goal5Increment;
    }

    public Integer getGoal5ValueIncrement () {
        return goal5ValueIncrement;
    }

    public void setGoal5ValueIncrement (Integer goal5ValueIncrement) {
        this.goal5ValueIncrement = goal5ValueIncrement;
    }

    public Integer getGoal6Increment () {
        return goal6Increment;
    }

    public void setGoal6Increment (Integer goal6Increment) {
        this.goal6Increment = goal6Increment;
    }

    public Integer getGoal6ValueIncrement () {
        return goal6ValueIncrement;
    }

    public void setGoal6ValueIncrement (Integer goal6ValueIncrement) {
        this.goal6ValueIncrement = goal6ValueIncrement;
    }

    public Integer getGoal7Increment () {
        return goal7Increment;
    }

    public void setGoal7Increment (Integer goal7Increment) {
        this.goal7Increment = goal7Increment;
    }

    public Integer getGoal7ValueIncrement () {
        return goal7ValueIncrement;
    }

    public void setGoal7ValueIncrement (Integer goal7ValueIncrement) {
        this.goal7ValueIncrement = goal7ValueIncrement;
    }

    public Integer getGoal8Increment () {
        return goal8Increment;
    }

    public void setGoal8Increment (Integer goal8Increment) {
        this.goal8Increment = goal8Increment;
    }

    public Integer getGoal8ValueIncrement () {
        return goal8ValueIncrement;
    }

    public void setGoal8ValueIncrement (Integer goal8ValueIncrement) {
        this.goal8ValueIncrement = goal8ValueIncrement;
    }

    public Integer getGoal9Increment () {
        return goal9Increment;
    }

    public void setGoal9Increment (Integer goal9Increment) {
        this.goal9Increment = goal9Increment;
    }

    public Integer getGoal9ValueIncrement () {
        return goal9ValueIncrement;
    }

    public void setGoal9ValueIncrement (Integer goal9ValueIncrement) {
        this.goal9ValueIncrement = goal9ValueIncrement;
    }

    public Integer getGoal10Increment () {
        return goal10Increment;
    }

    public void setGoal10Increment (Integer goal10Increment) {
        this.goal10Increment = goal10Increment;
    }

    public Integer getGoal10ValueIncrement () {
        return goal10ValueIncrement;
    }

    public void setGoal10ValueIncrement (Integer goal10ValueIncrement) {
        this.goal10ValueIncrement = goal10ValueIncrement;
    }

    public String getAwesmId () {
        return awesmId;
    }

    public void setAwesmId (String awesmId) {
        this.awesmId = awesmId;
    }

    public String getShareType () {
        return shareType;
    }

    public void setShareType (String shareType) {
        this.shareType = shareType;
    }

    public String getCreateType () {
        return createType;
    }

    public void setCreateType (String createType) {
        this.createType = createType;
    }

    public String getSharerId () {
        return sharerId;
    }

    public void setSharerId (String sharerId) {
        this.sharerId = sharerId;
    }

    public String getUserId () {
        return userId;
    }

    public void setUserId (String userId) {
        this.userId = userId;
    }

    public String getNotes () {
        return notes;
    }

    public void setNotes (String notes) {
        this.notes = notes;
    }

    public Integer getReactions1 () {
        return reactions1;
    }

    public void setReactions1 (Integer reactions1) {
        this.reactions1 = reactions1;
    }

    public Integer getReaations2 () {
        return reactions2;
    }

    public void setReaations2 (Integer reactions2) {
        this.reactions2 = reactions2;
    }

    public Calendar getSharedAt () {
        return sharedAt;
    }

    public void setSharedAt (Calendar sharedAt) {
        this.sharedAt = sharedAt;
    }

    public Calendar getUpdatedAt () {
        return updatedAt;
    }

    public void setUpdatedAt (Calendar updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Calendar getCreatedAt () {
        return createdAt;
    }

    public void setCreatedAt (Calendar createdAt) {
        this.createdAt = createdAt;
    }

    public String getTag2 () {
        return tag2;
    }

    public void setTag2 (String tag2) {
        this.tag2 = tag2;
    }

    public String getTag3 () {
        return tag3;
    }

    public void setTag3 (String tag3) {
        this.tag3 = tag3;
    }

    public String getTag4 () {
        return tag4;
    }

    public void setTag4 (String tag4) {
        this.tag4 = tag4;
    }

    public String getTag5 () {
        return tag5;
    }

    public void setTag5 (String tag5) {
        this.tag5 = tag5;
    }

    public String getSourceTag () {
        return sourceTag;
    }

    public void setSourceTag (String sourceTag) {
        this.sourceTag = sourceTag;
    }

    public Calendar getLastClickedAt () {
        return lastClickedAt;
    }

    public void setLastClickedAt (Calendar lastClickedAt) {
        this.lastClickedAt = lastClickedAt;
    }

    public String getClickedDate () {
        return clickedDate;
    }

    public void setClickedDate (String clickedDate) {
        this.clickedDate = clickedDate;
    }

    public Integer getMultiGenClicksIncrement () {
        return multiGenClicksIncrement;
    }

    public void setMultiGenClicksIncrement (Integer multiGenClicksIncrement) {
        this.multiGenClicksIncrement = multiGenClicksIncrement;
    }

    public Integer getMultiGenGoal1Increment () {
        return multiGenGoal1Increment;
    }

    public void setMultiGenGoal1Increment (Integer multiGenGoal1Increment) {
        this.multiGenGoal1Increment = multiGenGoal1Increment;
    }

    public Integer getMultiGenGoal1ValueIncrement () {
        return multiGenGoal1ValueIncrement;
    }

    public void setMultiGenGoal1ValueIncrement (Integer multiGenGoal1ValueIncrement) {
        this.multiGenGoal1ValueIncrement = multiGenGoal1ValueIncrement;
    }

    public Integer getMultiGenGoal2Increment () {
        return multiGenGoal2Increment;
    }

    public void setMultiGenGoal2Increment (Integer multiGenGoal2Increment) {
        this.multiGenGoal2Increment = multiGenGoal2Increment;
    }

    public Integer getMultiGenGoal2ValueIncrement () {
        return multiGenGoal2ValueIncrement;
    }

    public void setMultiGenGoal2ValueIncrement (Integer multiGenGoal2ValueIncrement) {
        this.multiGenGoal2ValueIncrement = multiGenGoal2ValueIncrement;
    }

    public Integer getMultiGenGoal3Increment () {
        return multiGenGoal3Increment;
    }

    public void setMultiGenGoal3Increment (Integer multiGenGoal3Increment) {
        this.multiGenGoal3Increment = multiGenGoal3Increment;
    }

    public Integer getMultiGenGoal3ValueIncrement () {
        return multiGenGoal3ValueIncrement;
    }

    public void setMultiGenGoal3ValueIncrement (Integer multiGenGoal3ValueIncrement) {
        this.multiGenGoal3ValueIncrement = multiGenGoal3ValueIncrement;
    }

    public Integer getMultiGenGoal4Increment () {
        return multiGenGoal4Increment;
    }

    public void setMultiGenGoal4Increment (Integer multiGenGoal4Increment) {
        this.multiGenGoal4Increment = multiGenGoal4Increment;
    }

    public Integer getMultiGenGoal4ValueIncrement () {
        return multiGenGoal4ValueIncrement;
    }

    public void setMultiGenGoal4ValueIncrement (Integer multiGenGoal4ValueIncrement) {
        this.multiGenGoal4ValueIncrement = multiGenGoal4ValueIncrement;
    }

    public Integer getMultiGenGoal5Increment () {
        return multiGenGoal5Increment;
    }

    public void setMultiGenGoal5Increment (Integer multiGenGoal5Increment) {
        this.multiGenGoal5Increment = multiGenGoal5Increment;
    }

    public Integer getMultiGenGoal5ValueIncrement () {
        return multiGenGoal5ValueIncrement;
    }

    public void setMultiGenGoal5ValueIncrement (Integer multiGenGoal5ValueIncrement) {
        this.multiGenGoal5ValueIncrement = multiGenGoal5ValueIncrement;
    }

    public Integer getMultiGenGoal6Increment () {
        return multiGenGoal6Increment;
    }

    public void setMultiGenGoal6Increment (Integer multiGenGoal6Increment) {
        this.multiGenGoal6Increment = multiGenGoal6Increment;
    }

    public Integer getMultiGenGoal6ValueIncrement () {
        return multiGenGoal6ValueIncrement;
    }

    public void setMultiGenGoal6ValueIncrement (Integer multiGenGoal6ValueIncrement) {
        this.multiGenGoal6ValueIncrement = multiGenGoal6ValueIncrement;
    }

    public Integer getMultiGenGoal7Increment () {
        return multiGenGoal7Increment;
    }

    public void setMultiGenGoal7Increment (Integer multiGenGoal7Increment) {
        this.multiGenGoal7Increment = multiGenGoal7Increment;
    }

    public Integer getMultiGenGoal7ValueIncrement () {
        return multiGenGoal7ValueIncrement;
    }

    public void setMultiGenGoal7ValueIncrement (Integer multiGenGoal7ValueIncrement) {
        this.multiGenGoal7ValueIncrement = multiGenGoal7ValueIncrement;
    }

    public Integer getMultiGenGoal8Increment () {
        return multiGenGoal8Increment;
    }

    public void setMultiGenGoal8Increment (Integer multiGenGoal8Increment) {
        this.multiGenGoal8Increment = multiGenGoal8Increment;
    }

    public Integer getMultiGenGoal8ValueIncrement () {
        return multiGenGoal8ValueIncrement;
    }

    public void setMultiGenGoal8ValueIncrement (Integer multiGenGoal8ValueIncrement) {
        this.multiGenGoal8ValueIncrement = multiGenGoal8ValueIncrement;
    }

    public Integer getMultiGenGoal9Increment () {
        return multiGenGoal9Increment;
    }

    public void setMultiGenGoal9Increment (Integer multiGenGoal9Increment) {
        this.multiGenGoal9Increment = multiGenGoal9Increment;
    }

    public Integer getMultiGenGoal9ValueIncrement () {
        return multiGenGoal9ValueIncrement;
    }

    public void setMultiGenGoal9ValueIncrement (Integer multiGenGoal9ValueIncrement) {
        this.multiGenGoal9ValueIncrement = multiGenGoal9ValueIncrement;
    }

    public Integer getMultiGenGoal10Increment () {
        return multiGenGoal10Increment;
    }

    public void setMultiGenGoal10Increment (Integer multiGenGoal10Increment) {
        this.multiGenGoal10Increment = multiGenGoal10Increment;
    }

    public Integer getMultiGenGoal10ValueIncrement () {
        return multiGenGoal10ValueIncrement;
    }

    public void setMultiGenGoal10ValueIncrement (Integer multiGenGoal10ValueIncrement) {
        this.multiGenGoal10ValueIncrement = multiGenGoal10ValueIncrement;
    }

    public Integer getMultiGenPageviewsIncrement () {
        return multiGenPageviewsIncrement;
    }

    public void setMultiGenPageviewsIncrement (Integer multiGenPageviewsIncrement) {
        this.multiGenPageviewsIncrement = multiGenPageviewsIncrement;
    }

    public Integer getFirstGenConversions () {
        return firstGenConversions;
    }

    public void setFirstGenConversions (Integer firstGenConversions) {
        this.firstGenConversions = firstGenConversions;
    }

    public Integer getMultiGenConversions () {
        return multiGenConversions;
    }

    public void setMultiGenConversions (Integer multiGenConversions) {
        this.multiGenConversions = multiGenConversions;
    }

    public String getRedirectUrl () {
        return redirectUrl;
    }

    public void setRedirectUrl (String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getChannel () {
        return channel;
    }

    public void setChannel (String channel) {
        this.channel = channel;
    }

    public String getCampaign () {
        return campaign;
    }

    public void setCampaign (String campaign) {
        this.campaign = campaign;
    }

    public Integer getReactions2 () {
        return reactions2;
    }

    public void setReactions2 (Integer reactions2) {
        this.reactions2 = reactions2;
    }

    public void calculateMultiGenIncrements (LinkStats child) {
        if ( child == null ) {
            throw new IllegalArgumentException("child must be NOT NULL");
        }

        // first to multi-gen
        this.setMultiGenClicksIncrement(child.getClicksIncrement());
        this.setMultiGenGoal1Increment(child.getGoal1Increment());
        this.setMultiGenGoal1ValueIncrement(child.getGoal1ValueIncrement());

        this.setMultiGenGoal2Increment(child.getGoal2Increment());
        this.setMultiGenGoal2ValueIncrement(child.getGoal2ValueIncrement());

        this.setMultiGenGoal3Increment(child.getGoal3Increment());
        this.setMultiGenGoal3ValueIncrement(child.getGoal3ValueIncrement());

        this.setMultiGenGoal4Increment(child.getGoal4Increment());
        this.setMultiGenGoal4ValueIncrement(child.getGoal4ValueIncrement());

        this.setMultiGenGoal5Increment(child.getGoal5Increment());
        this.setMultiGenGoal5ValueIncrement(child.getGoal5ValueIncrement());

        this.setMultiGenGoal6Increment(child.getGoal6Increment());
        this.setMultiGenGoal6ValueIncrement(child.getGoal6ValueIncrement());

        this.setMultiGenGoal7Increment(child.getGoal7Increment());
        this.setMultiGenGoal7ValueIncrement(child.getGoal7ValueIncrement());

        this.setMultiGenGoal8Increment(child.getGoal8Increment());
        this.setMultiGenGoal8ValueIncrement(child.getGoal8ValueIncrement());

        this.setMultiGenGoal9Increment(child.getGoal9Increment());
        this.setMultiGenGoal9ValueIncrement(child.getGoal9ValueIncrement());

        this.setMultiGenGoal10Increment(child.getGoal10Increment());
        this.setMultiGenGoal10ValueIncrement(child.getGoal10ValueIncrement());

        this.setMultiGenPageviewsIncrement(child.getPageViewsIncrement());
    }

    @Override
    public String toString () {
        StringBuilder sb = new StringBuilder();
        sb.append("{accountId: " + accountId + ", ")
            .append("redirectionId: " + redirectionId + ", ")
            .append("hour: " + hour + ", ")
            .append("domainId: " + domainId + ", ")
            .append("campaignId: " + campaignId + ", ")
            .append("originalUrlId: " + originalUrlId + ", ")
            .append("accountUserId: " + accountUserId + ", ")
            .append("parentId: " + parentId + ", ")
            .append("postId: " + postId + ", ")
            .append("mentions: " + mentions + ", ")
            .append("reach: " + reach + ", ")
            .append("pageViewsIncrement: " + pageViewsIncrement + ", ")
            .append("clicksIncrement: " + clicksIncrement + ", ")
            .append("goal1Increment: " + goal1Increment + ", ")
            .append("goal1ValueIncrement: " + goal1ValueIncrement + ", ")
            .append("goal2Increment: " + goal2Increment + ", ")
            .append("goal2ValueIncrement: " + goal2ValueIncrement + ", ")
            .append("goal3Increment: " + goal3Increment + ", ")
            .append("goal3ValueIncrement: " + goal3ValueIncrement + ", ")
            .append("goal4Increment: " + goal4Increment + ", ")
            .append("goal4ValueIncrement: " + goal4ValueIncrement + ", ")
            .append("goal5Increment: " + goal5Increment + ", ")
            .append("goal5ValueIncrement: " + goal5ValueIncrement + ", ")
            .append("goal6Increment: " + goal6Increment + ", ")
            .append("goal6ValueIncrement: " + goal6ValueIncrement + ", ")
            .append("goal7Increment: " + goal7Increment + ", ")
            .append("goal7ValueIncrement: " + goal7ValueIncrement + ", ")
            .append("goal8Increment: " + goal8Increment + ", ")
            .append("goal8ValueIncrement: " + goal8ValueIncrement + ", ")
            .append("goal9Increment: " + goal9Increment + ", ")
            .append("goal9ValueIncrement: " + goal9ValueIncrement + ", ")
            .append("goal10Increment: " + goal10Increment + ", ")
            .append("goal10ValueIncrement: " + goal10ValueIncrement + ", ")
            .append("shareType: " + shareType + ", ")
            .append("createType: " + createType + ", ")
            .append("sharerId: " + sharerId + ", ")
            .append("userId: " + userId + ", ")
            .append("notes: " + notes + ", ")
            .append("reactions1: " + reactions1 + ", ")
            .append("reactions2: " + reactions2 + ", ")
            .append("sharedAt: " + sharedAt + ", ")
            .append("updatedAt: " + updatedAt + ", ")
            .append("createdAt: " + createdAt + ", ")
            .append("tag2: " + tag2 + ", ")
            .append("tag3: " + tag3 + ", ")
            .append("tag4: " + tag4 + ", ")
            .append("tag5: " + tag5 + ", ")
            .append("lastClickedAt: " + lastClickedAt + ", ")
            .append("hour: " + hour + "}");

        return sb.toString();
    }
}
