package com.manning.gia.todo.repository;

import com.manning.gia.todo.model.ToDoItem;

import java.util.List;

import org.testng.annotations.*;

import static org.testng.Assert.*;

public class InMemoryToDoRepositoryTest {
    private ToDoRepository inMemoryToDoRepository;

    @BeforeClass
    public void setUp() {
        inMemoryToDoRepository = new InMemoryToDoRepository();
    }

    @Test
    public void testToDoItem() {
        ToDoItem newToDoItem = new ToDoItem();
        newToDoItem.setName("Write unit tests");
        Long newId = inMemoryToDoRepository.insert(newToDoItem);
        assertNotNull(newId);

        ToDoItem persistedToDoItem = inMemoryToDoRepository.findById(newId);
        assertNotNull(persistedToDoItem);
        assertEquals(newToDoItem, persistedToDoItem);
    }
}