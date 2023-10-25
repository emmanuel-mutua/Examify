package com.emmutua.examify.authentication;

public class User {
    String uid;
    String email;
    String fullName;
    String regNo;
    String role;
    String phoneNumber;

    public User(String uid,String email, String fullName, String regNo, String role, String phoneNumber) {
        this.uid = uid;
        this.email = email;
        this.fullName = fullName;
        this.regNo = regNo;
        this.role = role;
        this.phoneNumber = phoneNumber;
    }
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
