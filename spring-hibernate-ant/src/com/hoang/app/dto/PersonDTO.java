package com.hoang.app.dto;

import com.hoang.app.domain.Address;
import com.hoang.app.domain.Person;
import com.hoang.app.enu.EthnicGroup;
import com.hoang.app.enu.SexEnum;

/**
 *
 * @author htruong
 */

public class PersonDTO {
	private Long id;
    private String firstName;
    private String lastName;
    private String fullName;
    private SexEnum sex;
    private EthnicGroup ethnicGroup;
    private Address homeAddress;
    private Address workAddress;
    private String email;
    private Integer version;

    public void copyFromPerson(Person person) {
    	setId(person.getId());
		setFirstName(person.getFirstName());
		setLastName(person.getLastName());
		setFullName(person.getFullName());
		setSex(person.getSex());
		setEthnicGroup(person.getEthnicGroup());
		setHomeAddress(person.getHomeAddress());
		setWorkAddress(person.getWorkAddress());
		setEmail(person.getEmail());
        setVersion(person.getVersion());   
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public SexEnum getSex() {
		return sex;
	}

	public void setSex(SexEnum sex) {
		this.sex = sex;
	}

	public EthnicGroup getEthnicGroup() {
		return ethnicGroup;
	}

	public void setEthnicGroup(EthnicGroup ethnicGroup) {
		this.ethnicGroup = ethnicGroup;
	}

	public Address getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(Address homeAddress) {
		this.homeAddress = homeAddress;
	}

	public Address getWorkAddress() {
		return workAddress;
	}

	public void setWorkAddress(Address workAddress) {
		this.workAddress = workAddress;
	}		

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}


    
}
