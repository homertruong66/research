package com.rms.rms.common.dto;

import java.io.Serializable;
import java.util.List;

public class AffiliateTransactionDto implements Serializable {

    private static final long serialVersionUID = 1078518661499411341L;

    private AffiliateDto affiliate;
    private AgentDto agent;
    private List<CommissionDto> commissions;
    private List<PaymentDto> payments;

    public AffiliateDto getAffiliate() {
        return affiliate;
    }

    public void setAffiliate(AffiliateDto affiliate) {
        this.affiliate = affiliate;
    }

    public AgentDto getAgent() {
        return agent;
    }

    public void setAgent(AgentDto agent) {
        this.agent = agent;
    }

    public List<CommissionDto> getCommissions() {
        return commissions;
    }

    public void setCommissions(List<CommissionDto> commissions) {
        this.commissions = commissions;
    }

    public List<PaymentDto> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentDto> payments) {
        this.payments = payments;
    }

    @Override
    public String toString() {
        return "AffiliateTransactionDto{" +
                '}';
    }
}