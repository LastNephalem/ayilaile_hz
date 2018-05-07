package com.example.lucifer.ayilaile_hz.bean;

import java.io.Serializable;

/**
 * Created by Lucifer on 2018/4/25.
 */

public class User implements Serializable {
    private int id;
    private String phoneNumber;
    private String password;
    private String userName;
    public static String PHONENUMBER = "phoneNumber";
    public static String PASSWORD = "passWord";
    public static String USERNAME = "userName";

    public User(String phoneNumber, String password, String userName){
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.userName = userName;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

}
