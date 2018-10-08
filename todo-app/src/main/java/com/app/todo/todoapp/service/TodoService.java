package com.app.todo.todoapp.service;


import com.app.todo.todoapp.common.bean.TodoBean;
import com.app.todo.todoapp.common.exception.ApplicationException;

import java.util.List;
import java.util.UUID;

public interface TodoService {
    TodoBean findOne(UUID id) throws ApplicationException;

    void save(TodoBean todoBean);

    void update(TodoBean todoBean) throws ApplicationException;

    List<TodoBean> findAll();

    List<TodoBean> findAllByUserId(UUID userId);

    void delete(UUID id) throws ApplicationException;
}
