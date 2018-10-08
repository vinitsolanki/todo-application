package com.app.todo.todoapp.common.bean;

import java.util.List;

public class AuthUserResponseBean extends GenericResponseBean<String> {

    private String token;
    private String userId;
    private String userName;
    private List<String> authorities;
    //more user details can be here

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

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

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }
}
