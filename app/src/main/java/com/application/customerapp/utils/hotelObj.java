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
}