package com.application.customerapp.utils;


public class hotelObj {

    public String fullname ,email, address;

    public hotelObj()
    { }

    public hotelObj(String fullname,String email,String address)
    {
        this.fullname=fullname;
        this.email=email;
        this.address=address;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}