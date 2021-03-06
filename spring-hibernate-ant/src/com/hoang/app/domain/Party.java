package com.hoang.app.domain;
// Generated Jun 22, 2009 6:25:59 PM by Hibernate Tools 3.2.0.beta8
/**
 * Party generated by hbm2java
 */
public class Party extends AbstractEntity { 

     private static final long serialVersionUID = 1L;

    // Fields    

     private int version;
     private String email;
     // Constructors

    /** default constructor */
    public Party() {
    }

    /** full constructor */
    public Party(String email) {
       this.email = email;
    }

    // Property accessors
    public int getVersion() {
        return this.version;
    }
    public void setVersion(int version) {
        this.version = version;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
