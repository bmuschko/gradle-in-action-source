package com.manning.gia.todo.model

import groovy.transform.Canonical

@Canonical
class ToDoItem implements Comparable<ToDoItem> {
    Long id
    String name
    boolean completed

    @Override
    int compareTo(ToDoItem toDoItem) {
        this.id.compareTo(toDoItem.id)
    }
}