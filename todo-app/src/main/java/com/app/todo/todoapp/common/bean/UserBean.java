package com.app.todo.todoapp.common.bean;

import java.util.UUID;

public class UserBean {

    private UUID id;

    private String userName;

    private String email;

    private String userType;

    public UserBean() {
    }

    public UserBean(UUID id, String userName, String email, String userType) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.userType = userType;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
