package com.hvn.spring.model;
// Generated Jan 28, 2015 4:27:18 PM by Hibernate Tools 3.2.2.GA
/**
 * Student generated by hbm2java
 */
public class Student extends com.hvn.spring.model.Person implements java.io.Serializable {
     private String level;
    public Student() {
    }
    public Student(String name, String email) {
        super(name, email);        
    }
    public Student(String name, String email, String phone, String level) {
        super(name, email, phone);        
       this.level = level;
    }
    public String getLevel() {
        return this.level;
    }
    public void setLevel(String level) {
        this.level = level;
    }
}
