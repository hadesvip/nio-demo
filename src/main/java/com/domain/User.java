package com.domain;

import java.io.Serializable;

/**
 * 用户信息
 * Created by wangyong on 2017/6/20.
 */
public class User implements Serializable {

    private String userId;

    private String userName;

    private String password;

    private String email;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
