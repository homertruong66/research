package com.hoang.app.domain;
// Generated Jul 3, 2009 4:00:55 PM by Hibernate Tools 3.2.0.beta8
import com.hoang.app.enu.EthnicGroup;
import com.hoang.app.enu.SexEnum;
/**
 * Person generated by hbm2java
 */
public class Person extends Party implements java.io.Serializable {

     private static final long serialVersionUID = 1L;

    // Fields
     private String firstName;
     private String lastName;
     private String fullName;
     private SexEnum sex;
     private EthnicGroup ethnicGroup;
     private Address homeAddress;
     private Address workAddress;
     // Constructors

    /** default constructor */
    public Person() {
    }

	/** minimal constructor */
    public Person(String firstName, String lastName, String fullName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
    }

    /** full constructor */
    public Person(String email, String firstName, String lastName, String fullName, SexEnum sex, EthnicGroup ethnicGroup, Address homeAddress, Address workAddress) {
        super(email);
       this.firstName = firstName;
       this.lastName = lastName;
       this.fullName = fullName;
       this.sex = sex;
       this.ethnicGroup = ethnicGroup;
       this.homeAddress = homeAddress;
       this.workAddress = workAddress;
    }

    // Property accessors
    public String getFirstName() {
        return this.firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return this.lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getFullName() {
        return this.fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public SexEnum getSex() {
        return this.sex;
    }
    public void setSex(SexEnum sex) {
        this.sex = sex;
    }
    public EthnicGroup getEthnicGroup() {
        return this.ethnicGroup;
    }
    public void setEthnicGroup(EthnicGroup ethnicGroup) {
        this.ethnicGroup = ethnicGroup;
    }
    public Address getHomeAddress() {
        return this.homeAddress;
    }
    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }
    public Address getWorkAddress() {
        return this.workAddress;
    }
    public void setWorkAddress(Address workAddress) {
        this.workAddress = workAddress;
    }
}
