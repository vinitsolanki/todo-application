package com.app.todo.todoapp.repository;

import com.app.todo.todoapp.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TodoRepository extends JpaRepository<Todo, UUID>{

    List<Todo> findByUserId(UUID userId);
}