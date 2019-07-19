package com.rms.rms.service.model;

import java.util.HashSet;
import java.util.Set;

/**
 * homertruong
 */

public class Affiliate extends Person {

    private Set<Agent> agents = new HashSet<>();
    private Bank bank;
    private Boolean isGetResponseSuccess;
    private Boolean isPhoneVerified;
    private String metadata;
    private String nickname;

    public Set<Agent> getAgents() {
        return agents;
    }

    public void setAgents(Set<Agent> agents) {
        this.agents = agents;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public Boolean getIsGetResponseSuccess() {
        return isGetResponseSuccess;
    }

    public void setIsGetResponseSuccess(Boolean getResponseSuccess) {
        isGetResponseSuccess = getResponseSuccess;
    }

    public Boolean getIsPhoneVerified() {
        return isPhoneVerified;
    }

    public void setIsPhoneVerified(Boolean phoneVerified) {
        isPhoneVerified = phoneVerified;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString () {
        return "Affiliate{" +
                "agents=" + agents +
                ", bank=" + bank +
                ", isGetResponseSuccess=" + isGetResponseSuccess +
                ", isPhoneVerified=" + isPhoneVerified +
                ", metadata=" + metadata +
                ", nickname=" + nickname +
                "} - " + super.toString();
    }
}
