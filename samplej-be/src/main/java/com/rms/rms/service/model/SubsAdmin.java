package com.rms.rms.service.model;

import java.util.HashSet;
import java.util.Set;

/**
 * homertruong
 */

public class SubsAdmin extends Person {

    private Set<Category> categories = new HashSet<>();
    private Boolean isRootSubsAdmin;
    private Set<Post> posts = new HashSet<>();
    private Subscriber subscriber;
    private String subscriberId;

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Boolean getIsRootSubsAdmin() {
        return isRootSubsAdmin;
    }

    public void setIsRootSubsAdmin(Boolean isRootSubsAdmin) {
        this.isRootSubsAdmin = isRootSubsAdmin;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    @Override
    public String toString() {
        return "SubsAdmin{" +
                "isRootSubsAdmin=" + isRootSubsAdmin +
                ", subscriberId='" + subscriberId + '\'' +
                '}';
    }
}
