package com.manning.gia.todo.repository

import com.manning.gia.todo.model.ToDoItem

trait ToDoRepository {
    def findAll(): java.util.List[ToDoItem]
    def findAllActive(): java.util.List[ToDoItem]
    def findAllCompleted(): java.util.List[ToDoItem]
    def findById(id: java.lang.Long): ToDoItem
    def insert(toDoItem: ToDoItem): java.lang.Long
    def update(toDoItem: ToDoItem): Unit
    def delete(toDoItem: ToDoItem): Unit
}