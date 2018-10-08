package com.app.todo.todoapp.entity;

import com.app.todo.todoapp.common.enums.UserType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @NotBlank
    @Column(nullable = false, unique = true, updatable = false)
    private String userName;

    @NotBlank
    @Column(nullable = false, unique = true, updatable = false)
    private String email;

    @NotBlank
    @Column(nullable = false)
    private String password;

    @Column
    @NotNull
    private UserType userType;

    public User() {
    }

    public User(String userName, String email, String password, UserType userType) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.userType = userType;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
