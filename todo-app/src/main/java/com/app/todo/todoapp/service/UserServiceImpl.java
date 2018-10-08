package com.app.todo.todoapp.service;

import com.app.todo.todoapp.common.bean.UserBean;
import com.app.todo.todoapp.entity.User;
import com.app.todo.todoapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserBean> findAll() {
        List<User> users = userRepository.findAll();
        List<UserBean> userBeans = users.stream().map(user -> new UserBean(user.getId(), user.getUserName(), user.getEmail(), user.getUserType().name())).collect(Collectors.toList());
        return userBeans;
    }

    @Override
    public UserBean findById(UUID userId) {
        User user = userRepository.getOne(userId);
        return new UserBean(user.getId(), user.getUserName(), user.getEmail(), user.getUserType().name());
    }
}
