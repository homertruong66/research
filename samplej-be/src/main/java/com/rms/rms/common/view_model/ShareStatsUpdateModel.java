package com.rms.rms.common.view_model;

import org.apache.commons.lang3.StringUtils;

/**
 * homertruong
 */

public class ShareStatsUpdateModel {

    private String nickname;
    private String url;
    private ClickInfoUpdateModel clickInfo;

    public String validate() {
        String errors = "";

        if (StringUtils.isBlank(nickname)) {
            errors += "'nickname' can't be empty! && ";
        }

        if (StringUtils.isBlank(url)) {
            errors += "'url' can't be empty! && ";
        }

        if (clickInfo == null) {
            errors += "'click_info' can't be null! &&";
        }
        else {
            if (StringUtils.isBlank(clickInfo.getCountry())) {
                errors += "'country' can't be empty! &&";
            }

            if (StringUtils.isBlank(clickInfo.getDeviceType())) {
                errors += "'device_type' can't be empty! &&";
            }
        }

        return errors.equals("") ? null : errors;
    }

    //
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ClickInfoUpdateModel getClickInfo() {
        return clickInfo;
    }

    public void setClickInfo(ClickInfoUpdateModel clickInfo) {
        this.clickInfo = clickInfo;
    }

    @Override
    public String toString() {
        return "ShareStatsUpdateModel{" +
                "nickname='" + nickname + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
