package com.example.appbanlinhkien30.model;

public class User {
    private String Phone;
    private String Email;
    private String Password;
    private int isAdmin;

    public User() {
    }
    public User(String phone, String email, String password, int isAdmin) {
        Phone = phone;
        Email = email;
        Password = password;
        this.isAdmin = isAdmin;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

}