package com.hoang.codegenerator.model;
// Generated Nov 20, 2016 3:20:38 PM by Hibernate Tools 3.2.2.GA
/**
 * Person generated by hbm2java
 */
public class Person extends AbstractEntity { 

     private Integer version;
     private String name;
     private String email;
     private String phone;
    public Person() {
    }
    public Person(String name, String email) {
        this.name = name;
        this.email = email;
    }
    public Person(String name, String email, String phone) {
       this.name = name;
       this.email = email;
       this.phone = phone;
    }
    public Integer getVersion() {
        return this.version;
    }
    public void setVersion(Integer version) {
        this.version = version;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
