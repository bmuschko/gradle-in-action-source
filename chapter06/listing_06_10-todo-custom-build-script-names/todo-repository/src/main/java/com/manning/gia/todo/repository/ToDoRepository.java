package com.manning.gia.todo.repository;

import com.manning.gia.todo.model.ToDoItem;

import java.util.List;

public interface ToDoRepository {
    List<ToDoItem> findAll();

    List<ToDoItem> findAllActive();

    List<ToDoItem> findAllCompleted();

    ToDoItem findById(Long id);

    Long insert(ToDoItem toDoItem);

    void update(ToDoItem toDoItem);

    void delete(ToDoItem toDoItem);
}