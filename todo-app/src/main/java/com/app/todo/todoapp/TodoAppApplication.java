package com.app.todo.todoapp;

import com.app.todo.todoapp.common.enums.UserType;
import com.app.todo.todoapp.entity.User;
import com.app.todo.todoapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class TodoAppApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(TodoAppApplication.class, args);
    }

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        //save default users at startup
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userRepository.save(new User("vinitsolanki07", "vinit07@gmail.com", passwordEncoder.encode("vinit@123"), UserType.USER));
        userRepository.save(new User("vinitsolanki369", "vinit369@gmail.com", passwordEncoder.encode("vinit@123"), UserType.USER));
        userRepository.save(new User("admin", "admin@gmail.com", passwordEncoder.encode("admin"), UserType.ADMIN));
    }
}
