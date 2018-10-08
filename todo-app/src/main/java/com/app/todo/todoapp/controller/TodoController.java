package com.app.todo.todoapp.controller;

import com.app.todo.todoapp.common.MessageKeys;
import com.app.todo.todoapp.common.SecurityUtil;
import com.app.todo.todoapp.common.bean.GenericResponseBean;
import com.app.todo.todoapp.common.bean.TodoBean;
import com.app.todo.todoapp.common.exception.ApplicationException;
import com.app.todo.todoapp.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping
    public GenericResponseBean getTodosByUsername() {

        List<TodoBean> todoBeans = todoService.findAllByUserId(SecurityUtil.getUserIdUUID());

        return GenericResponseBean.build(todoBeans);
    }

    @PostMapping
    public GenericResponseBean saveTodo(@RequestBody @Valid TodoBean todoBean) {

        todoBean.setUserId(SecurityUtil.getUserId());
        todoService.save(todoBean);

        return GenericResponseBean.build(MessageKeys.RESULT_OK);
    }

    @PutMapping("{id}")
    public GenericResponseBean updateTodo(@RequestBody @Valid TodoBean todoBean, @PathVariable String id) throws ApplicationException {

        todoBean.setId(UUID.fromString(id));
        todoBean.setUserId(SecurityUtil.getUserId());

        todoService.update(todoBean);

        return GenericResponseBean.build(MessageKeys.RESULT_OK);
    }

    @DeleteMapping("{id}")
    public GenericResponseBean deleteTodo(@PathVariable String id) throws ApplicationException {

        todoService.delete(UUID.fromString(id));

        return GenericResponseBean.build(MessageKeys.RESULT_OK);
    }

}
