package com.manning.gia.todo.repository;

import com.manning.gia.todo.model.ToDoItem;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class InMemoryToDoRepositoryTest {
    private ToDoRepository inMemoryToDoRepository;

    @Before
    public void setUp() {
        inMemoryToDoRepository = new InMemoryToDoRepository();
    }

    @Test
    public void testInsertToDoItems() {
        int items = System.getProperty("items") != null ? Integer.parseInt(System.getProperty("items")) : 1;
        createAndInsertToDoItems(items);
        List<ToDoItem> toDoItems = inMemoryToDoRepository.findAll();

        assertEquals(items, toDoItems.size());
    }

    private void createAndInsertToDoItems(int items) {
        System.out.println("Creating " + items + " To Do items.");

        for (int i = 1; i <= items; i++) {
            ToDoItem toDoItem = new ToDoItem();
            toDoItem.setName("To Do task " + i);
            inMemoryToDoRepository.insert(toDoItem);
        }
    }
}