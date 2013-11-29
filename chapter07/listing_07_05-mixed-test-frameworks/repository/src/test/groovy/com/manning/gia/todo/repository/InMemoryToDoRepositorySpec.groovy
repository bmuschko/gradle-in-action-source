package com.manning.gia.todo.repository

import com.manning.gia.todo.model.ToDoItem
import spock.lang.Specification

import java.util.List

class InMemoryToDoRepositorySpec extends Specification {
    def "Insert To Do item"() {
        setup:
        ToDoRepository inMemoryToDoRepository = new InMemoryToDoRepository()
        when:
        ToDoItem newToDoItem = new ToDoItem();
        newToDoItem.name = "Write unit tests"
        Long newId = inMemoryToDoRepository.insert(newToDoItem)
        newToDoItem.id = newId
        then:
        ToDoItem persistedToDoItem = inMemoryToDoRepository.findById(newId)
        newId != null
        persistedToDoItem != null
        newToDoItem == persistedToDoItem
    }
}