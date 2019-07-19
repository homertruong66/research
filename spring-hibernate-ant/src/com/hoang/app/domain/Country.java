package com.hoang.app.domain;
// Generated Jun 22, 2009 6:25:59 PM by Hibernate Tools 3.2.0.beta8
import java.util.HashSet;
import java.util.Set;
/**
 * Country generated by hbm2java
 */
public class Country extends AbstractEntity { 

     private static final long serialVersionUID = 1L;

    // Fields    

     private int version;
     private String code;
     private String name;
     private Set<Province> provinces = new HashSet<Province>(0);
     // Constructors

    /** default constructor */
    public Country() {
    }

    /** full constructor */
    public Country(String code, String name, Set<Province> provinces) {
       this.code = code;
       this.name = name;
       this.provinces = provinces;
    }

    // Property accessors
    public int getVersion() {
        return this.version;
    }
    public void setVersion(int version) {
        this.version = version;
    }
    public String getCode() {
        return this.code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Set<Province> getProvinces() {
        return this.provinces;
    }
    public void setProvinces(Set<Province> provinces) {
        this.provinces = provinces;
    }
}
