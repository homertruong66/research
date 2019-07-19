package com.hoang.uma.service.model;

import javax.persistence.*;

@Entity
@Table(
    name="users_roles",
    uniqueConstraints={
        @UniqueConstraint(columnNames={"user_id", "role_id"})
    }
)
public class UserRole {

    @Id
    private String id;

    // Associations
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    // Get/Set
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return role.getName();
    }
}
