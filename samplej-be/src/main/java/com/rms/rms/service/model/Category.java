package com.rms.rms.service.model;

import com.rms.rms.persistence.BaseEntityExtensible;

import java.util.HashSet;
import java.util.Set;

public class Category extends BaseEntityExtensible {

    private String name;
    private Set<Post> posts = new HashSet<>();
    private String subsAdminId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    public String getSubsAdminId() {
        return subsAdminId;
    }

    public void setSubsAdminId(String subsAdminId) {
        this.subsAdminId = subsAdminId;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", subsAdminId='" + subsAdminId + '\'' +
                '}';
    }
}
