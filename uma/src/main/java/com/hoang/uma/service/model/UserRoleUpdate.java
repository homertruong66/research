package com.hoang.uma.service.model;

import javax.persistence.*;

@Entity
@Table(
    name="users_roles",
    uniqueConstraints={
        @UniqueConstraint(columnNames={"user_id", "role_id"})
    }
)
public class UserRoleUpdate {

    @Id
    private String id;

    // Associations
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserUpdate user;

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

    public UserUpdate getUser() {
        return user;
    }

    public void setUser(UserUpdate user) {
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
