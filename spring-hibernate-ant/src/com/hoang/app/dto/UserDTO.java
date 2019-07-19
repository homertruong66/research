package com.hoang.app.dto;

import java.util.HashSet;
import java.util.Set;

import com.hoang.app.domain.Organization;
import com.hoang.app.domain.Person;
import com.hoang.app.domain.User;
import com.hoang.app.domain.UserRole;

/**
 *
 * @author Hoang Truong
 * 
 */

public class UserDTO {
	private Long id;
    private String username;
    private String currentPassword;
    private String password;    
    private String confirmedPassword;
    private boolean enabled;
    private String fullname;
    private String email;
    private boolean hasPerson = true;
    private boolean hasOrganization = false;
    private Person person;
    private Organization organization;
    private Set<String> roles = new HashSet<String>(0);
    private Integer version;

    public void copyFromUser(User user) {
    	 setId(user.getId());
    	 setUsername(user.getUsername());
         setPassword(user.getPassword());
         setEnabled(user.isEnabled());
         setFullname(user.getFullname());
         setEmail(user.getEmail());
         if (user.getParty() instanceof Person) {
        	 setHasPerson(true);
             setHasOrganization(false);
             setPerson((Person) user.getParty());
         }
         else if (user.getParty() instanceof Organization) {
             setHasPerson(false);
             setHasOrganization(true);
             setOrganization((Organization) user.getParty()); 
         }
         for (UserRole userRole : user.getUserRoles()) {
             roles.add(userRole.getRole().getName());
         }
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmedPassword() {
		return confirmedPassword;
	}

	public void setConfirmedPassword(String confirmedPassword) {
		this.confirmedPassword = confirmedPassword;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isHasPerson() {
		return hasPerson;
	}

	public void setHasPerson(boolean hasPerson) {
		this.hasPerson = hasPerson;
	}

	public boolean isHasOrganization() {
		return hasOrganization;
	}

	public void setHasOrganization(boolean hasOrganization) {
		this.hasOrganization = hasOrganization;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

    
}
