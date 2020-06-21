package com.example.todoslist.service;

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

}
