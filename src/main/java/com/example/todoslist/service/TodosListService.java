package com.example.todoslist.service;

import com.example.todoslist.model.TodoTask;
import com.example.todoslist.model.TodosList;
import com.example.todoslist.repository.TodosListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TodosListService {

    @Autowired
    TodosListRepository repository;

    public List<TodosList> getAllLists() {
        List<TodosList> lists = new ArrayList<>();
        repository.findAll().forEach(lists::add);
        return lists;
    }

    public TodosList getList(long id) {

        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid list Id: " + id));
    }

    public List<TodosList> saveList(TodosList newList) {

        repository.save(newList);
        return getAllLists();
    }

    public TodosList saveTask(long listId, TodoTask task) {

        TodosList list = getList(listId);
        list.getTasks().add(task);
        return repository.save(list);
    }

    public List<TodosList> deleteList(long id) {

        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Invalid list reference: " + id);
        }
        return getAllLists();
    }

    public TodosList deleteTask(long id, String name) {

        TodosList list = getList(id);
        List<TodoTask> tasks = list.getTasks();
        if (tasks.removeIf(task -> name.equals(task.getTaskName()))) {
            saveList(list);
        }
        return getList(id);
    }

    public List<TodosList> toggleList(long id) {

        TodosList list = getList(id);
        list.setActive(!list.isActive);

        return saveList(list);
    }

    public TodosList toggleTask(long id, String name) {

        TodosList list = getList(id);
        List<TodoTask> tasks = list.getTasks();

        TodoTask task = tasks.stream()
                .filter(t -> name.equals(t.getTaskName()))
                .findFirst().orElse(null);

        if (task != null) {
            task.setCompleted(!task.isCompleted);
            saveList(list);
        }

        return getList(id);
    }

}
