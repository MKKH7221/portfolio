package com.example.spring_project.domain.model.user;

import org.springframework.util.ObjectUtils;


public class SearchUser{

    private Integer id;
    private String name;
    private String address;
    private String tel;
    private String countryCode;

    public SearchUser(Integer id, String name, String address, String tel, String countryCode) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.tel = tel;
        this.countryCode = countryCode;
    }

    public Integer getId() {
        if (this.id != null && this.id != 0) {
            return this.id;
        }
        return null;
    }

    public String getName() {
       
        return isEmpty(this.name);
    }

    public String getAddress() {
        return isEmpty(this.address);
    }

    public String getTel() {
        return isEmpty(tel);
    }

    public String getCountryCode() {
        return isEmpty(countryCode);
    }
    
    private static String isEmpty (String strCheck) {
        if (ObjectUtils.isEmpty(strCheck)) {
            return null;
        }
        return strCheck;
    }

    @Override
    public String toString() {
        return "SearchUser [id=" + id + ", name=" + name + ", address=" + address + ", tel=" + tel + ", countryCode="
                + countryCode + "]";
    }
 
    
}

