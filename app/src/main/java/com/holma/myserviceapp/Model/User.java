package com.holma.myserviceapp.Model;

public class User {
    private String fid;
    private String Name;
    private String userPhone;
    private String Address;

    public User() {
    }

    public User(String fid, String name, String userPhone, String address) {
        this.fid = fid;
        Name = name;
        this.userPhone = userPhone;
        Address = address;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}


