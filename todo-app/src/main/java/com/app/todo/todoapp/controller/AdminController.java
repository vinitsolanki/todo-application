package com.app.todo.todoapp.controller;

import com.app.todo.todoapp.common.bean.GenericResponseBean;
import com.app.todo.todoapp.common.bean.TodoBean;
import com.app.todo.todoapp.common.bean.UserBean;
import com.app.todo.todoapp.service.TodoService;
import com.app.todo.todoapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private TodoService todoService;

    @GetMapping("users")
    public GenericResponseBean getUsers() {
        List<UserBean> userBeans = userService.findAll();
        return GenericResponseBean.build(userBeans);
    }

    @GetMapping("users/{userId}")
    public GenericResponseBean getUsers(@PathVariable String userId) {

        UserBean userBean = userService.findById(UUID.fromString(userId));
        return GenericResponseBean.build(userBean);
    }


    @GetMapping("todos")
    public GenericResponseBean getTodos() {

        List<TodoBean> todoBeans = todoService.findAll();

        return GenericResponseBean.build(todoBeans);
    }

    @GetMapping("users/{userId}/todos")
    public GenericResponseBean getTodosByUserId(@PathVariable String userId) {

        List<TodoBean> todoBeans = todoService.findAllByUserId(UUID.fromString(userId));

        return GenericResponseBean.build(todoBeans);
    }
}
