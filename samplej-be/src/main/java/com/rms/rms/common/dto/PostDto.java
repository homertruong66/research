package com.rms.rms.common.dto;

import com.rms.rms.common.BaseDto;

import java.io.Serializable;

/**
 * homertruong
 */

public class PostDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 2281716278559456765L;

    private CategoryDto category;
    private String categoryId;
    private String content;
    private String description;
    private String subsAdminId;
    private String thumbnail;
    private String title;
    private String url;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubsAdminId() {
        return subsAdminId;
    }

    public void setSubsAdminId(String subsAdminId) {
        this.subsAdminId = subsAdminId;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "PostDto{" +
                "categoryId='" + categoryId + '\'' +
                ", content='" + content + '\'' +
                ", description='" + description + '\'' +
                ", subsAdminId='" + subsAdminId + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}