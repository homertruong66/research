package com.rms.rms.common.view_model;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.HtmlUtils;

public class AffiliateConvertModel {

    private String email;
    private String feUrl;
    private String nickname;
    private String orderId;

    public void escapeHtml() {
        this.email = HtmlUtils.htmlEscape(this.email, "UTF-8");
        this.nickname = HtmlUtils.htmlEscape(this.nickname, "UTF-8");
    }

    public String validate() {
        String errors = "";

        if (StringUtils.isEmpty(feUrl)) {
            errors += "'fe_url' can't be empty! && ";
        }
        
        if (StringUtils.isEmpty(orderId)) {
            errors += "'order_id' can't be empty! && ";
        }

        return errors.equals("") ? null : errors;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFeUrl() {
        return feUrl;
    }

    public void setFeUrl(String feUrl) {
        this.feUrl = feUrl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "AffiliateConvertModel{" +
                "email='" + email + '\'' +
                ", feUrl='" + feUrl + '\'' +
                ", nickname='" + nickname + '\'' +
                ", orderId='" + orderId + '\'' +
                '}';
    }
}
