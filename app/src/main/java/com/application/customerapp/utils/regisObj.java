package com.application.customerapp.utils;

public class regisObj {

    public String fname,lname,email,address,gender,phone,adults,children,checkin,checkout,idurl,sigurl,passport,token,time;

    public regisObj(){}

    public regisObj(String fname, String lname, String email, String address, String gender, String phone,String adults, String children, String checkin, String checkout,String idurl,String sigurl,String passport,String token,String time) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.address = address;
        this.gender = gender;
        this.adults = adults;
        this.phone = phone;
        this.children = children;
        this.checkin = checkin;
        this.checkout = checkout;
        this.idurl = idurl;
        this.sigurl = sigurl;
        this.passport = passport;
        this.token = token;
        this.time = time;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdults() {
        return adults;
    }

    public void setAdults(String adults) {
        this.adults = adults;
    }

    public String getChildren() {
        return children;
    }

    public void setChildren(String children) {
        this.children = children;
    }

    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    public String getCheckout() {
        return checkout;
    }

    public void setCheckout(String checkout) {
        this.checkout = checkout;
    }

    public String getIdurl() {
        return idurl;
    }

    public void setIdurl(String idurl) {
        this.idurl = idurl;
    }

    public String getSigurl() {
        return sigurl;
    }

    public void setSigurl(String sigurl) {
        this.sigurl = sigurl;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
