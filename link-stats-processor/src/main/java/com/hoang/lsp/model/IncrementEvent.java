package com.hoang.lsp.model;

import java.math.BigInteger;

import com.google.common.base.Function;

public class IncrementEvent {

    // Primary key
    // Hourly: accountId-hour-redirectionId
    // Total: accountId-redirectionId
    private Long accountId;
    private BigInteger redirectionId;
    private String clickedDate;

    // Stats metrics
    private Integer clicksIncrement;
    private Integer pageViewsIncrement;
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

    private Integer multiGenClicksIncrement;
    private Integer multiGenPageviewsIncrement;
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

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public BigInteger getRedirectionId() {
        return redirectionId;
    }

    public void setRedirectionId(BigInteger redirectionId) {
        this.redirectionId = redirectionId;
    }

    public Integer getPageViewsIncrement() {
        return pageViewsIncrement;
    }

    public void setPageViewsIncrement(Integer pageViewsIncrement) {
        this.pageViewsIncrement = pageViewsIncrement;
    }

    public Integer getClicksIncrement() {
        return clicksIncrement;
    }

    public void setClicksIncrement(Integer clicksIncrement) {
        this.clicksIncrement = clicksIncrement;
    }

    public Integer getGoal1Increment() {
        return goal1Increment;
    }

    public void setGoal1Increment(Integer goal1Increment) {
        this.goal1Increment = goal1Increment;
    }

    public Integer getGoal1ValueIncrement() {
        return goal1ValueIncrement;
    }

    public void setGoal1ValueIncrement(Integer goal1ValueIncrement) {
        this.goal1ValueIncrement = goal1ValueIncrement;
    }

    public Integer getGoal2Increment() {
        return goal2Increment;
    }

    public void setGoal2Increment(Integer goal2Increment) {
        this.goal2Increment = goal2Increment;
    }

    public Integer getGoal2ValueIncrement() {
        return goal2ValueIncrement;
    }

    public void setGoal2ValueIncrement(Integer goal2ValueIncrement) {
        this.goal2ValueIncrement = goal2ValueIncrement;
    }

    public Integer getGoal3Increment() {
        return goal3Increment;
    }

    public void setGoal3Increment(Integer goal3Increment) {
        this.goal3Increment = goal3Increment;
    }

    public Integer getGoal3ValueIncrement() {
        return goal3ValueIncrement;
    }

    public void setGoal3ValueIncrement(Integer goal3ValueIncrement) {
        this.goal3ValueIncrement = goal3ValueIncrement;
    }

    public Integer getGoal4Increment() {
        return goal4Increment;
    }

    public void setGoal4Increment(Integer goal4Increment) {
        this.goal4Increment = goal4Increment;
    }

    public Integer getGoal4ValueIncrement() {
        return goal4ValueIncrement;
    }

    public void setGoal4ValueIncrement(Integer goal4ValueIncrement) {
        this.goal4ValueIncrement = goal4ValueIncrement;
    }

    public Integer getGoal5Increment() {
        return goal5Increment;
    }

    public void setGoal5Increment(Integer goal5Increment) {
        this.goal5Increment = goal5Increment;
    }

    public Integer getGoal5ValueIncrement() {
        return goal5ValueIncrement;
    }

    public void setGoal5ValueIncrement(Integer goal5ValueIncrement) {
        this.goal5ValueIncrement = goal5ValueIncrement;
    }

    public Integer getGoal6Increment() {
        return goal6Increment;
    }

    public void setGoal6Increment(Integer goal6Increment) {
        this.goal6Increment = goal6Increment;
    }

    public Integer getGoal6ValueIncrement() {
        return goal6ValueIncrement;
    }

    public void setGoal6ValueIncrement(Integer goal6ValueIncrement) {
        this.goal6ValueIncrement = goal6ValueIncrement;
    }

    public Integer getGoal7Increment() {
        return goal7Increment;
    }

    public void setGoal7Increment(Integer goal7Increment) {
        this.goal7Increment = goal7Increment;
    }

    public Integer getGoal7ValueIncrement() {
        return goal7ValueIncrement;
    }

    public void setGoal7ValueIncrement(Integer goal7ValueIncrement) {
        this.goal7ValueIncrement = goal7ValueIncrement;
    }

    public Integer getGoal8Increment() {
        return goal8Increment;
    }

    public void setGoal8Increment(Integer goal8Increment) {
        this.goal8Increment = goal8Increment;
    }

    public Integer getGoal8ValueIncrement() {
        return goal8ValueIncrement;
    }

    public void setGoal8ValueIncrement(Integer goal8ValueIncrement) {
        this.goal8ValueIncrement = goal8ValueIncrement;
    }

    public Integer getGoal9Increment() {
        return goal9Increment;
    }

    public void setGoal9Increment(Integer goal9Increment) {
        this.goal9Increment = goal9Increment;
    }

    public Integer getGoal9ValueIncrement() {
        return goal9ValueIncrement;
    }

    public void setGoal9ValueIncrement(Integer goal9ValueIncrement) {
        this.goal9ValueIncrement = goal9ValueIncrement;
    }

    public Integer getGoal10Increment() {
        return goal10Increment;
    }

    public void setGoal10Increment(Integer goal10Increment) {
        this.goal10Increment = goal10Increment;
    }

    public Integer getGoal10ValueIncrement() {
        return goal10ValueIncrement;
    }

    public void setGoal10ValueIncrement(Integer goal10ValueIncrement) {
        this.goal10ValueIncrement = goal10ValueIncrement;
    }

    public String getClickedDate() {
        return clickedDate;
    }

    public void setClickedDate(String clickedDate) {
        this.clickedDate = clickedDate;
    }

    public Integer getMultiGenClicksIncrement() {
        return multiGenClicksIncrement;
    }

    public void setMultiGenClicksIncrement(Integer multiGenClicksIncrement) {
        this.multiGenClicksIncrement = multiGenClicksIncrement;
    }

    public Integer getMultiGenGoal1Increment() {
        return multiGenGoal1Increment;
    }

    public void setMultiGenGoal1Increment(Integer multiGenGoal1Increment) {
        this.multiGenGoal1Increment = multiGenGoal1Increment;
    }

    public Integer getMultiGenGoal1ValueIncrement() {
        return multiGenGoal1ValueIncrement;
    }

    public void setMultiGenGoal1ValueIncrement(Integer multiGenGoal1ValueIncrement) {
        this.multiGenGoal1ValueIncrement = multiGenGoal1ValueIncrement;
    }

    public Integer getMultiGenGoal2Increment() {
        return multiGenGoal2Increment;
    }

    public void setMultiGenGoal2Increment(Integer multiGenGoal2Increment) {
        this.multiGenGoal2Increment = multiGenGoal2Increment;
    }

    public Integer getMultiGenGoal2ValueIncrement() {
        return multiGenGoal2ValueIncrement;
    }

    public void setMultiGenGoal2ValueIncrement(Integer multiGenGoal2ValueIncrement) {
        this.multiGenGoal2ValueIncrement = multiGenGoal2ValueIncrement;
    }

    public Integer getMultiGenGoal3Increment() {
        return multiGenGoal3Increment;
    }

    public void setMultiGenGoal3Increment(Integer multiGenGoal3Increment) {
        this.multiGenGoal3Increment = multiGenGoal3Increment;
    }

    public Integer getMultiGenGoal3ValueIncrement() {
        return multiGenGoal3ValueIncrement;
    }

    public void setMultiGenGoal3ValueIncrement(Integer multiGenGoal3ValueIncrement) {
        this.multiGenGoal3ValueIncrement = multiGenGoal3ValueIncrement;
    }

    public Integer getMultiGenGoal4Increment() {
        return multiGenGoal4Increment;
    }

    public void setMultiGenGoal4Increment(Integer multiGenGoal4Increment) {
        this.multiGenGoal4Increment = multiGenGoal4Increment;
    }

    public Integer getMultiGenGoal4ValueIncrement() {
        return multiGenGoal4ValueIncrement;
    }

    public void setMultiGenGoal4ValueIncrement(Integer multiGenGoal4ValueIncrement) {
        this.multiGenGoal4ValueIncrement = multiGenGoal4ValueIncrement;
    }

    public Integer getMultiGenGoal5Increment() {
        return multiGenGoal5Increment;
    }

    public void setMultiGenGoal5Increment(Integer multiGenGoal5Increment) {
        this.multiGenGoal5Increment = multiGenGoal5Increment;
    }

    public Integer getMultiGenGoal5ValueIncrement() {
        return multiGenGoal5ValueIncrement;
    }

    public void setMultiGenGoal5ValueIncrement(Integer multiGenGoal5ValueIncrement) {
        this.multiGenGoal5ValueIncrement = multiGenGoal5ValueIncrement;
    }

    public Integer getMultiGenGoal6Increment() {
        return multiGenGoal6Increment;
    }

    public void setMultiGenGoal6Increment(Integer multiGenGoal6Increment) {
        this.multiGenGoal6Increment = multiGenGoal6Increment;
    }

    public Integer getMultiGenGoal6ValueIncrement() {
        return multiGenGoal6ValueIncrement;
    }

    public void setMultiGenGoal6ValueIncrement(Integer multiGenGoal6ValueIncrement) {
        this.multiGenGoal6ValueIncrement = multiGenGoal6ValueIncrement;
    }

    public Integer getMultiGenGoal7Increment() {
        return multiGenGoal7Increment;
    }

    public void setMultiGenGoal7Increment(Integer multiGenGoal7Increment) {
        this.multiGenGoal7Increment = multiGenGoal7Increment;
    }

    public Integer getMultiGenGoal7ValueIncrement() {
        return multiGenGoal7ValueIncrement;
    }

    public void setMultiGenGoal7ValueIncrement(Integer multiGenGoal7ValueIncrement) {
        this.multiGenGoal7ValueIncrement = multiGenGoal7ValueIncrement;
    }

    public Integer getMultiGenGoal8Increment() {
        return multiGenGoal8Increment;
    }

    public void setMultiGenGoal8Increment(Integer multiGenGoal8Increment) {
        this.multiGenGoal8Increment = multiGenGoal8Increment;
    }

    public Integer getMultiGenGoal8ValueIncrement() {
        return multiGenGoal8ValueIncrement;
    }

    public void setMultiGenGoal8ValueIncrement(Integer multiGenGoal8ValueIncrement) {
        this.multiGenGoal8ValueIncrement = multiGenGoal8ValueIncrement;
    }

    public Integer getMultiGenGoal9Increment() {
        return multiGenGoal9Increment;
    }

    public void setMultiGenGoal9Increment(Integer multiGenGoal9Increment) {
        this.multiGenGoal9Increment = multiGenGoal9Increment;
    }

    public Integer getMultiGenGoal9ValueIncrement() {
        return multiGenGoal9ValueIncrement;
    }

    public void setMultiGenGoal9ValueIncrement(Integer multiGenGoal9ValueIncrement) {
        this.multiGenGoal9ValueIncrement = multiGenGoal9ValueIncrement;
    }

    public Integer getMultiGenGoal10Increment() {
        return multiGenGoal10Increment;
    }

    public void setMultiGenGoal10Increment(Integer multiGenGoal10Increment) {
        this.multiGenGoal10Increment = multiGenGoal10Increment;
    }

    public Integer getMultiGenGoal10ValueIncrement() {
        return multiGenGoal10ValueIncrement;
    }

    public void setMultiGenGoal10ValueIncrement(Integer multiGenGoal10ValueIncrement) {
        this.multiGenGoal10ValueIncrement = multiGenGoal10ValueIncrement;
    }

    public Integer getMultiGenPageviewsIncrement() {
        return multiGenPageviewsIncrement;
    }

    public void setMultiGenPageviewsIncrement(Integer multiGenPageviewsIncrement) {
        this.multiGenPageviewsIncrement = multiGenPageviewsIncrement;
    }

    public static Function<LinkStats, IncrementEvent> getLinkStatsToIncrementEventFunction () {
        return new Function<LinkStats, IncrementEvent>() {
            @Override
            public IncrementEvent apply(LinkStats linkStats) {
                return IncrementEventBuilder.anIncrementEvent()
                        .accountId(linkStats.getAccountId())
                        .redirectionId(linkStats.getRedirectionId())
                        .clickedDate(linkStats.getClickedDate())
                        .clicksIncrement(linkStats.getClicksIncrement())
                        .pageViewsIncrement(linkStats.getPageViewsIncrement())
                        .goal1Increment(linkStats.getGoal1Increment())
                        .goal2Increment(linkStats.getGoal2Increment())
                        .goal3Increment(linkStats.getGoal3Increment())
                        .goal4Increment(linkStats.getGoal4Increment())
                        .goal5Increment(linkStats.getGoal5Increment())
                        .goal6Increment(linkStats.getGoal6Increment())
                        .goal7Increment(linkStats.getGoal7Increment())
                        .goal8Increment(linkStats.getGoal8Increment())
                        .goal9Increment(linkStats.getGoal9Increment())
                        .goal10Increment(linkStats.getGoal10Increment())
                        .multiGenClicksIncrement(linkStats.getMultiGenClicksIncrement())
                        .multiGenPageviewsIncrement(linkStats.getMultiGenPageviewsIncrement())
                        .multiGenGoal1Increment(linkStats.getMultiGenGoal1Increment())
                        .multiGenGoal1ValueIncrement(linkStats.getMultiGenGoal1ValueIncrement())
                        .multiGenGoal2Increment(linkStats.getMultiGenGoal2Increment())
                        .multiGenGoal2ValueIncrement(linkStats.getMultiGenGoal2ValueIncrement())
                        .multiGenGoal3Increment(linkStats.getMultiGenGoal3Increment())
                        .multiGenGoal3ValueIncrement(linkStats.getMultiGenGoal3ValueIncrement())
                        .multiGenGoal4Increment(linkStats.getMultiGenGoal4Increment())
                        .multiGenGoal4ValueIncrement(linkStats.getMultiGenGoal4ValueIncrement())
                        .multiGenGoal5Increment(linkStats.getMultiGenGoal5Increment())
                        .multiGenGoal5ValueIncrement(linkStats.getMultiGenGoal5ValueIncrement())
                        .multiGenGoal6Increment(linkStats.getMultiGenGoal6Increment())
                        .multiGenGoal6ValueIncrement(linkStats.getMultiGenGoal6ValueIncrement())
                        .multiGenGoal7Increment(linkStats.getMultiGenGoal7Increment())
                        .multiGenGoal7ValueIncrement(linkStats.getMultiGenGoal7ValueIncrement())
                        .multiGenGoal8Increment(linkStats.getMultiGenGoal8Increment())
                        .multiGenGoal8ValueIncrement(linkStats.getMultiGenGoal8ValueIncrement())
                        .multiGenGoal9Increment(linkStats.getMultiGenGoal9Increment())
                        .multiGenGoal9ValueIncrement(linkStats.getMultiGenGoal9ValueIncrement())
                        .multiGenGoal10Increment(linkStats.getMultiGenGoal10Increment())
                        .multiGenGoal10ValueIncrement(linkStats.getMultiGenGoal10ValueIncrement())
                        .build();
            }
        };
    }

    public static final class IncrementEventBuilder {
        // Primary key
        // Hourly: accountId-hour-redirectionId
        // Total: accountId-redirectionId
        private Long accountId;
        private BigInteger redirectionId;
        // Stats metrics
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
        private String clickedDate;
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

        private IncrementEventBuilder() {
        }

        public static IncrementEventBuilder anIncrementEvent() {
            return new IncrementEventBuilder();
        }

        public IncrementEventBuilder accountId(Long accountId) {
            this.accountId = accountId;
            return this;
        }

        public IncrementEvent build() {
            IncrementEvent incrementEvent = new IncrementEvent();
            incrementEvent.setAccountId(accountId);
            incrementEvent.setRedirectionId(redirectionId);
            incrementEvent.setPageViewsIncrement(pageViewsIncrement);
            incrementEvent.setClicksIncrement(clicksIncrement);
            incrementEvent.setGoal1Increment(goal1Increment);
            incrementEvent.setGoal1ValueIncrement(goal1ValueIncrement);
            incrementEvent.setGoal2Increment(goal2Increment);
            incrementEvent.setGoal2ValueIncrement(goal2ValueIncrement);
            incrementEvent.setGoal3Increment(goal3Increment);
            incrementEvent.setGoal3ValueIncrement(goal3ValueIncrement);
            incrementEvent.setGoal4Increment(goal4Increment);
            incrementEvent.setGoal4ValueIncrement(goal4ValueIncrement);
            incrementEvent.setGoal5Increment(goal5Increment);
            incrementEvent.setGoal5ValueIncrement(goal5ValueIncrement);
            incrementEvent.setGoal6Increment(goal6Increment);
            incrementEvent.setGoal6ValueIncrement(goal6ValueIncrement);
            incrementEvent.setGoal7Increment(goal7Increment);
            incrementEvent.setGoal7ValueIncrement(goal7ValueIncrement);
            incrementEvent.setGoal8Increment(goal8Increment);
            incrementEvent.setGoal8ValueIncrement(goal8ValueIncrement);
            incrementEvent.setGoal9Increment(goal9Increment);
            incrementEvent.setGoal9ValueIncrement(goal9ValueIncrement);
            incrementEvent.setGoal10Increment(goal10Increment);
            incrementEvent.setGoal10ValueIncrement(goal10ValueIncrement);
            incrementEvent.setClickedDate(clickedDate);
            incrementEvent.setMultiGenClicksIncrement(multiGenClicksIncrement);
            incrementEvent.setMultiGenGoal1Increment(multiGenGoal1Increment);
            incrementEvent.setMultiGenGoal1ValueIncrement(multiGenGoal1ValueIncrement);
            incrementEvent.setMultiGenGoal2Increment(multiGenGoal2Increment);
            incrementEvent.setMultiGenGoal2ValueIncrement(multiGenGoal2ValueIncrement);
            incrementEvent.setMultiGenGoal3Increment(multiGenGoal3Increment);
            incrementEvent.setMultiGenGoal3ValueIncrement(multiGenGoal3ValueIncrement);
            incrementEvent.setMultiGenGoal4Increment(multiGenGoal4Increment);
            incrementEvent.setMultiGenGoal4ValueIncrement(multiGenGoal4ValueIncrement);
            incrementEvent.setMultiGenGoal5Increment(multiGenGoal5Increment);
            incrementEvent.setMultiGenGoal5ValueIncrement(multiGenGoal5ValueIncrement);
            incrementEvent.setMultiGenGoal6Increment(multiGenGoal6Increment);
            incrementEvent.setMultiGenGoal6ValueIncrement(multiGenGoal6ValueIncrement);
            incrementEvent.setMultiGenGoal7Increment(multiGenGoal7Increment);
            incrementEvent.setMultiGenGoal7ValueIncrement(multiGenGoal7ValueIncrement);
            incrementEvent.setMultiGenGoal8Increment(multiGenGoal8Increment);
            incrementEvent.setMultiGenGoal8ValueIncrement(multiGenGoal8ValueIncrement);
            incrementEvent.setMultiGenGoal9Increment(multiGenGoal9Increment);
            incrementEvent.setMultiGenGoal9ValueIncrement(multiGenGoal9ValueIncrement);
            incrementEvent.setMultiGenGoal10Increment(multiGenGoal10Increment);
            incrementEvent.setMultiGenGoal10ValueIncrement(multiGenGoal10ValueIncrement);
            incrementEvent.setMultiGenPageviewsIncrement(multiGenPageviewsIncrement);
            return incrementEvent;
        }

        public IncrementEventBuilder clickedDate(String clickedDate) {
            this.clickedDate = clickedDate;
            return this;
        }

        public IncrementEventBuilder clicksIncrement(Integer clicksIncrement) {
            this.clicksIncrement = clicksIncrement;
            return this;
        }

        public IncrementEventBuilder goal10Increment(Integer goal10Increment) {
            this.goal10Increment = goal10Increment;
            return this;
        }

        public IncrementEventBuilder goal10ValueIncrement(Integer goal10ValueIncrement) {
            this.goal10ValueIncrement = goal10ValueIncrement;
            return this;
        }

        public IncrementEventBuilder goal1Increment(Integer goal1Increment) {
            this.goal1Increment = goal1Increment;
            return this;
        }

        public IncrementEventBuilder goal1ValueIncrement(Integer goal1ValueIncrement) {
            this.goal1ValueIncrement = goal1ValueIncrement;
            return this;
        }

        public IncrementEventBuilder goal2Increment(Integer goal2Increment) {
            this.goal2Increment = goal2Increment;
            return this;
        }

        public IncrementEventBuilder goal2ValueIncrement(Integer goal2ValueIncrement) {
            this.goal2ValueIncrement = goal2ValueIncrement;
            return this;
        }

        public IncrementEventBuilder goal3Increment(Integer goal3Increment) {
            this.goal3Increment = goal3Increment;
            return this;
        }

        public IncrementEventBuilder goal3ValueIncrement(Integer goal3ValueIncrement) {
            this.goal3ValueIncrement = goal3ValueIncrement;
            return this;
        }

        public IncrementEventBuilder goal4Increment(Integer goal4Increment) {
            this.goal4Increment = goal4Increment;
            return this;
        }

        public IncrementEventBuilder goal4ValueIncrement(Integer goal4ValueIncrement) {
            this.goal4ValueIncrement = goal4ValueIncrement;
            return this;
        }

        public IncrementEventBuilder goal5Increment(Integer goal5Increment) {
            this.goal5Increment = goal5Increment;
            return this;
        }

        public IncrementEventBuilder goal5ValueIncrement(Integer goal5ValueIncrement) {
            this.goal5ValueIncrement = goal5ValueIncrement;
            return this;
        }

        public IncrementEventBuilder goal6Increment(Integer goal6Increment) {
            this.goal6Increment = goal6Increment;
            return this;
        }

        public IncrementEventBuilder goal6ValueIncrement(Integer goal6ValueIncrement) {
            this.goal6ValueIncrement = goal6ValueIncrement;
            return this;
        }

        public IncrementEventBuilder goal7Increment(Integer goal7Increment) {
            this.goal7Increment = goal7Increment;
            return this;
        }

        public IncrementEventBuilder goal7ValueIncrement(Integer goal7ValueIncrement) {
            this.goal7ValueIncrement = goal7ValueIncrement;
            return this;
        }

        public IncrementEventBuilder goal8Increment(Integer goal8Increment) {
            this.goal8Increment = goal8Increment;
            return this;
        }

        public IncrementEventBuilder goal8ValueIncrement(Integer goal8ValueIncrement) {
            this.goal8ValueIncrement = goal8ValueIncrement;
            return this;
        }

        public IncrementEventBuilder goal9Increment(Integer goal9Increment) {
            this.goal9Increment = goal9Increment;
            return this;
        }

        public IncrementEventBuilder goal9ValueIncrement(Integer goal9ValueIncrement) {
            this.goal9ValueIncrement = goal9ValueIncrement;
            return this;
        }

        public IncrementEventBuilder multiGenClicksIncrement(Integer multiGenClicksIncrement) {
            this.multiGenClicksIncrement = multiGenClicksIncrement;
            return this;
        }

        public IncrementEventBuilder multiGenGoal10Increment(Integer multiGenGoal10Increment) {
            this.multiGenGoal10Increment = multiGenGoal10Increment;
            return this;
        }

        public IncrementEventBuilder multiGenGoal10ValueIncrement(Integer multiGenGoal10ValueIncrement) {
            this.multiGenGoal10ValueIncrement = multiGenGoal10ValueIncrement;
            return this;
        }

        public IncrementEventBuilder multiGenGoal1Increment(Integer multiGenGoal1Increment) {
            this.multiGenGoal1Increment = multiGenGoal1Increment;
            return this;
        }

        public IncrementEventBuilder multiGenGoal1ValueIncrement(Integer multiGenGoal1ValueIncrement) {
            this.multiGenGoal1ValueIncrement = multiGenGoal1ValueIncrement;
            return this;
        }

        public IncrementEventBuilder multiGenGoal2Increment(Integer multiGenGoal2Increment) {
            this.multiGenGoal2Increment = multiGenGoal2Increment;
            return this;
        }

        public IncrementEventBuilder multiGenGoal2ValueIncrement(Integer multiGenGoal2ValueIncrement) {
            this.multiGenGoal2ValueIncrement = multiGenGoal2ValueIncrement;
            return this;
        }

        public IncrementEventBuilder multiGenGoal3Increment(Integer multiGenGoal3Increment) {
            this.multiGenGoal3Increment = multiGenGoal3Increment;
            return this;
        }

        public IncrementEventBuilder multiGenGoal3ValueIncrement(Integer multiGenGoal3ValueIncrement) {
            this.multiGenGoal3ValueIncrement = multiGenGoal3ValueIncrement;
            return this;
        }

        public IncrementEventBuilder multiGenGoal4Increment(Integer multiGenGoal4Increment) {
            this.multiGenGoal4Increment = multiGenGoal4Increment;
            return this;
        }

        public IncrementEventBuilder multiGenGoal4ValueIncrement(Integer multiGenGoal4ValueIncrement) {
            this.multiGenGoal4ValueIncrement = multiGenGoal4ValueIncrement;
            return this;
        }

        public IncrementEventBuilder multiGenGoal5Increment(Integer multiGenGoal5Increment) {
            this.multiGenGoal5Increment = multiGenGoal5Increment;
            return this;
        }

        public IncrementEventBuilder multiGenGoal5ValueIncrement(Integer multiGenGoal5ValueIncrement) {
            this.multiGenGoal5ValueIncrement = multiGenGoal5ValueIncrement;
            return this;
        }

        public IncrementEventBuilder multiGenGoal6Increment(Integer multiGenGoal6Increment) {
            this.multiGenGoal6Increment = multiGenGoal6Increment;
            return this;
        }

        public IncrementEventBuilder multiGenGoal6ValueIncrement(Integer multiGenGoal6ValueIncrement) {
            this.multiGenGoal6ValueIncrement = multiGenGoal6ValueIncrement;
            return this;
        }

        public IncrementEventBuilder multiGenGoal7Increment(Integer multiGenGoal7Increment) {
            this.multiGenGoal7Increment = multiGenGoal7Increment;
            return this;
        }

        public IncrementEventBuilder multiGenGoal7ValueIncrement(Integer multiGenGoal7ValueIncrement) {
            this.multiGenGoal7ValueIncrement = multiGenGoal7ValueIncrement;
            return this;
        }

        public IncrementEventBuilder multiGenGoal8Increment(Integer multiGenGoal8Increment) {
            this.multiGenGoal8Increment = multiGenGoal8Increment;
            return this;
        }

        public IncrementEventBuilder multiGenGoal8ValueIncrement(Integer multiGenGoal8ValueIncrement) {
            this.multiGenGoal8ValueIncrement = multiGenGoal8ValueIncrement;
            return this;
        }

        public IncrementEventBuilder multiGenGoal9Increment(Integer multiGenGoal9Increment) {
            this.multiGenGoal9Increment = multiGenGoal9Increment;
            return this;
        }

        public IncrementEventBuilder multiGenGoal9ValueIncrement(Integer multiGenGoal9ValueIncrement) {
            this.multiGenGoal9ValueIncrement = multiGenGoal9ValueIncrement;
            return this;
        }

        public IncrementEventBuilder multiGenPageviewsIncrement(Integer multiGenPageviewsIncrement) {
            this.multiGenPageviewsIncrement = multiGenPageviewsIncrement;
            return this;
        }

        public IncrementEventBuilder pageViewsIncrement(Integer pageViewsIncrement) {
            this.pageViewsIncrement = pageViewsIncrement;
            return this;
        }

        public IncrementEventBuilder redirectionId(BigInteger redirectionId) {
            this.redirectionId = redirectionId;
            return this;
        }
    }
}
