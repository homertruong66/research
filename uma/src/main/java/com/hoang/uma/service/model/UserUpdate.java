package com.hoang.uma.service.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "discriminator", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(value = "USER")
public class UserUpdate {

    @Id
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @Column(name = "token")
    private String token;

    @Version
    @Column(name = "VERSION")
    private Integer version;

    @Column(name = "updated_at")
    private Date updatedAt;

    // Associations
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<UserRoleUpdate> userRoleUpdates;

    // Get/Set
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<UserRoleUpdate> getUserRoleUpdates() {
        return userRoleUpdates;
    }

    public void setUserRoleUpdates(List<UserRoleUpdate> userRoleUpdates) {
        this.userRoleUpdates = userRoleUpdates;
    }

    @Override
    public String toString() {
        return "UserUpdate{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", token=" + token +
                ", version=" + version +
                ", updatedAt=" + updatedAt +
                ", userRoleUpdates=" + userRoleUpdates +
                '}';
    }
}
