package com.app.todo.todoapp.service;

import com.app.todo.todoapp.common.MessageKeys;
import com.app.todo.todoapp.common.SecurityUtil;
import com.app.todo.todoapp.common.exception.ApplicationException;
import com.app.todo.todoapp.repository.TodoRepository;
import com.app.todo.todoapp.common.bean.TodoBean;
import com.app.todo.todoapp.entity.Todo;
import com.app.todo.todoapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public TodoBean findOne(UUID id) throws ApplicationException {

        Optional<Todo> todo = todoRepository.findById(id);
        if (!todo.isPresent()) {
            throw new ApplicationException(MessageKeys.ENTITY_NOT_FOUND);
        }

        return getTodoBeanFromTodo(todo.get());
    }

    @Override
    public void save(TodoBean todoBean) {

        Todo todo = getTodoFromTodoBean(todoBean);
        todoRepository.save(todo);

    }

    @Override
    public void update(TodoBean todoBean) throws ApplicationException {

        Optional<Todo> savedTodo = todoRepository.findById(todoBean.getId());
        if (!savedTodo.isPresent()) {
            throw new ApplicationException(MessageKeys.ENTITY_NOT_FOUND);
        } else if (!todoBean.getUserId().equals(savedTodo.get().getUser().getId().toString())) {
            throw new ApplicationException(MessageKeys.UNAUTHORIZED);
        }

        Todo todo = getTodoFromTodoBean(todoBean);
        todo.setUpdatedDate(LocalDateTime.now());
        todoRepository.save(todo);

    }



    @Override
    public List<TodoBean> findAll() {

        List<Todo> todos = todoRepository.findAll();

        List<TodoBean> todoBeans = todos.stream().map(todo -> getTodoBeanFromTodo(todo)).collect(Collectors.toList());

        return todoBeans;
    }

    @Override
    public List<TodoBean> findAllByUserId(UUID userId) {

        List<Todo> todos = todoRepository.findByUserId(userId);

        List<TodoBean> todoBeans = todos.stream().map(todo -> getTodoBeanFromTodo(todo)).collect(Collectors.toList());

        return todoBeans;
    }

    @Override
    public void delete(UUID id) throws ApplicationException {

        Todo todo = todoRepository.getOne(id);
        if(!todo.getUser().getId().equals(SecurityUtil.getUserIdUUID())) {
            throw new ApplicationException(MessageKeys.UNAUTHORIZED);
        }

        todoRepository.delete(todo);
    }

    private TodoBean getTodoBeanFromTodo(Todo todo) {
        TodoBean todoBean = new TodoBean();
        todoBean.setId(todo.getId());
        todoBean.setCompleted(todo.isCompleted());
        todoBean.setTitle(todo.getTitle());
        todoBean.setUserId(todo.getUser().getId().toString());
        todoBean.setUsername(todo.getUser().getUserName());
        return todoBean;
    }

    private Todo getTodoFromTodoBean(TodoBean todoBean) {
        Todo todo = new Todo();
        todo.setId(todoBean.getId());
        todo.setCompleted(todoBean.isCompleted());
        todo.setTitle(todoBean.getTitle());
        todo.setUser(userRepository.getOne(UUID.fromString(todoBean.getUserId())));
        return todo;
    }

}
