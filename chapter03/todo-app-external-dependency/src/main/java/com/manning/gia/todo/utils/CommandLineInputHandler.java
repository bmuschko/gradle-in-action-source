package com.manning.gia.todo.utils;

import com.manning.gia.todo.model.ToDoItem;
import com.manning.gia.todo.repository.InMemoryToDoRepository;
import com.manning.gia.todo.repository.ToDoRepository;

import java.util.Collection;

public class CommandLineInputHandler {
    private ToDoRepository toDoRepository = new InMemoryToDoRepository();

    public void printOptions() {
        System.out.println("\n--- To Do Application ---");
        System.out.println("Please make a choice:");
        System.out.println("(a)ll items");
        System.out.println("(f)ind a specific item");
        System.out.println("(i)nsert a new item");
        System.out.println("(u)pdate an existing item");
        System.out.println("(d)elete an existing item");
        System.out.println("(e)xit");
    }

    public String readInput() {
        java.io.Console console = System.console();
        return new java.util.Scanner(System.in).useDelimiter(System.getProperty("line.separator")).next();
    }

    public void processInput(CommandLineInput input) {
        if (input == null) {
            handleUnknownInput();
        } else {
            switch (input) {
                case FIND_ALL:
                    printAllToDoItems();
                    break;
                case FIND_BY_ID:
                    printToDoItem();
                    break;
                case INSERT:
                    insertToDoItem();
                    break;
                case UPDATE:
                    updateToDoItem();
                    break;
                case DELETE:
                    deleteToDoItem();
                    break;
                case EXIT:
                    break;
                default:
                    handleUnknownInput();
            }
        }
    }

    private Long askForItemId() {
        System.out.println("Please enter the item ID:");
        String input = readInput();
        return Long.parseLong(input);
    }

    private ToDoItem askForNewToDoAction() {
        ToDoItem toDoItem = new ToDoItem();
        System.out.println("Please enter the name of the item:");
        toDoItem.setName(readInput());
        return toDoItem;
    }

    private void printAllToDoItems() {
        Collection<ToDoItem> toDoItems = toDoRepository.findAll();

        if (toDoItems.isEmpty()) {
            System.out.println("Nothing to do. Go relax!");
        } else {
            for (ToDoItem toDoItem : toDoItems) {
                System.out.println(toDoItem);
            }
        }
    }

    private void printToDoItem() {
        ToDoItem toDoItem = findToDoItem();

        if (toDoItem != null) {
            System.out.println(toDoItem);
        }
    }

    private ToDoItem findToDoItem() {
        Long id = askForItemId();
        ToDoItem toDoItem = toDoRepository.findById(id);

        if (toDoItem == null) {
            System.err.println("To do item with ID " + id + " could not be found.");
        }

        return toDoItem;
    }

    private void insertToDoItem() {
        ToDoItem toDoItem = askForNewToDoAction();
        Long id = toDoRepository.insert(toDoItem);
        System.out.println("Successfully inserted to do item with ID " + id + ".");
    }

    private void updateToDoItem() {
        ToDoItem toDoItem = findToDoItem();

        if (toDoItem != null) {
            System.out.println(toDoItem);
            System.out.println("Please enter the name of the item:");
            toDoItem.setName(readInput());
            System.out.println("Please enter the done status the item:");
            toDoItem.setCompleted(Boolean.parseBoolean(readInput()));
            toDoRepository.update(toDoItem);
            System.out.println("Successfully updated to do item with ID " + toDoItem.getId() + ".");
        }
    }

    private void deleteToDoItem() {
        ToDoItem toDoItem = findToDoItem();

        if (toDoItem != null) {
            toDoRepository.delete(toDoItem);
            System.out.println("Successfully deleted to do item with ID " + toDoItem.getId() + ".");
        }
    }

    private void handleUnknownInput() {
        System.out.println("Please select a valid option!");
    }
}