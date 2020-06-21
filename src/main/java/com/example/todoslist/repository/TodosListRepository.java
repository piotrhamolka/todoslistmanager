package com.example.todoslist.repository;

import com.example.todoslist.model.TodosList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodosListRepository extends CrudRepository<TodosList, Long> {
}
