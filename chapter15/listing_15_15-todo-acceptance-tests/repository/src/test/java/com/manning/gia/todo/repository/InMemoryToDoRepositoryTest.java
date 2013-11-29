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
    public void testInsertToDoItem() {
        ToDoItem toDoItem = new ToDoItem();
        toDoItem.setName("Write unit tests");
        inMemoryToDoRepository.insert(toDoItem);
        List<ToDoItem> toDoItems = inMemoryToDoRepository.findAll();

        assertEquals(1, toDoItems.size());
        assertEquals(toDoItem, toDoItems.get(0));
    }

    @Test
    public void testFindToDoItemById() {
        ToDoItem toDoItem = new ToDoItem();
        toDoItem.setName("Write unit tests");
        inMemoryToDoRepository.insert(toDoItem);
        ToDoItem foundToDoItem = inMemoryToDoRepository.findById(1L);

        assertEquals(toDoItem, foundToDoItem);
    }

    @Test
    public void testFindAllToDoItems() {
        ToDoItem toDoItem1 = new ToDoItem();
        toDoItem1.setName("Write unit tests");
        ToDoItem toDoItem2 = new ToDoItem();
        toDoItem2.setName("Write integration tests");
        ToDoItem toDoItem3 = new ToDoItem();
        toDoItem3.setName("Write functional tests");
        inMemoryToDoRepository.insert(toDoItem1);
        inMemoryToDoRepository.insert(toDoItem2);
        inMemoryToDoRepository.insert(toDoItem3);
        List<ToDoItem> toDoItems = inMemoryToDoRepository.findAll();

        assertEquals(3, toDoItems.size());
    }

    @Test
    public void testFindAllActiveToDoItems() {
        ToDoItem toDoItem1 = new ToDoItem();
        toDoItem1.setName("Write unit tests");
        ToDoItem toDoItem2 = new ToDoItem();
        toDoItem2.setName("Write integration tests");
        ToDoItem toDoItem3 = new ToDoItem();
        toDoItem3.setName("Write functional tests");
        inMemoryToDoRepository.insert(toDoItem1);
        inMemoryToDoRepository.insert(toDoItem2);
        inMemoryToDoRepository.insert(toDoItem3);
        List<ToDoItem> toDoItems = inMemoryToDoRepository.findAllActive();

        assertEquals(3, toDoItems.size());
    }

    @Test
    public void testFindAllCompletedToDoItems() {
        ToDoItem toDoItem1 = new ToDoItem();
        toDoItem1.setName("Write unit tests");
        ToDoItem toDoItem2 = new ToDoItem();
        toDoItem2.setName("Write integration tests");
        toDoItem2.setCompleted(true);
        ToDoItem toDoItem3 = new ToDoItem();
        toDoItem3.setName("Write functional tests");
        inMemoryToDoRepository.insert(toDoItem1);
        inMemoryToDoRepository.insert(toDoItem2);
        inMemoryToDoRepository.insert(toDoItem3);
        List<ToDoItem> toDoItems = inMemoryToDoRepository.findAllCompleted();

        assertEquals(1, toDoItems.size());
    }

    @Test
    public void testDeleteToDoItem() {
        ToDoItem toDoItem = new ToDoItem();
        toDoItem.setName("Write unit tests");
        inMemoryToDoRepository.insert(toDoItem);
        List<ToDoItem> toDoItems = inMemoryToDoRepository.findAll();

        assertEquals(1, toDoItems.size());
        assertEquals(toDoItem, toDoItems.get(0));
        inMemoryToDoRepository.delete(toDoItem);
        toDoItems = inMemoryToDoRepository.findAll();
        assertEquals(0, toDoItems.size());
    }

    @Test
    public void testUpdateToDoItem() {
        ToDoItem toDoItem = new ToDoItem();
        toDoItem.setName("Write unit tests");
        inMemoryToDoRepository.insert(toDoItem);
        List<ToDoItem> toDoItems = inMemoryToDoRepository.findAll();

        assertEquals(1, toDoItems.size());
        assertEquals(toDoItem, toDoItems.get(0));
        toDoItem.setName("Updating an item");
        inMemoryToDoRepository.update(toDoItem);
        toDoItems = inMemoryToDoRepository.findAll();
        assertEquals("Updating an item", toDoItems.get(0).getName());
    }
}