package com.rms.rms.common.dto;

import com.rms.rms.common.BaseDto;

import java.io.Serializable;

public class SubsEmailTemplateDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 6784171572026724852L;

    private String content;
    private Boolean isEnabled;
    private String subsEmailConfigId;
    private String title;
    private String type;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public String getSubsEmailConfigId() {
        return subsEmailConfigId;
    }

    public void setSubsEmailConfigId(String subsEmailConfigId) {
        this.subsEmailConfigId = subsEmailConfigId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "SubsEmailTemplateDto{" +
                "content='" + content + '\'' +
                ", isEnabled=" + isEnabled +
                ", subsEmailConfigId='" + subsEmailConfigId + '\'' +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
