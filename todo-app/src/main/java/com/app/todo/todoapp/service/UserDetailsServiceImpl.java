package com.app.todo.todoapp.service;

import com.app.todo.todoapp.common.MessageKeys;
import com.app.todo.todoapp.common.enums.UserType;
import com.app.todo.todoapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.app.todo.todoapp.entity.User user = userRepository.findByUserName(username);
        if(user == null) {
            throw new UsernameNotFoundException(MessageKeys.BAD_CREDENTIALS);
        }

        return new User(user.getId().toString(), user.getPassword(), Collections.singleton(new SimpleGrantedAuthority(user.getUserType().name())));
    }
}
