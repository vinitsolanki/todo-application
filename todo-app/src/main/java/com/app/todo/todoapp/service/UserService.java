package com.app.todo.todoapp.service;

import com.app.todo.todoapp.common.bean.UserBean;

import java.util.List;
import java.util.UUID;

public interface UserService {

    List<UserBean> findAll();

    UserBean findById(UUID userId);

}
