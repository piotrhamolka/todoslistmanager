package com.example.todoslist;

import com.example.todoslist.model.TodoTask;
import com.example.todoslist.model.TodosList;
import com.example.todoslist.repository.TodosListRepository;
import com.example.todoslist.service.TodosListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Arrays;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.example.todoslist"})
@EnableJpaRepositories(basePackages = "com.example.todoslist.repository")
@EntityScan(basePackages = "com.example.todoslist.model")
public class TodosListApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(TodosListApplication.class, args);
    }


    @Autowired
    TodosListRepository repository;

    @Override
    public void run(String... args) throws Exception {

        repository.deleteAll();

        TodoTask task1 = new TodoTask();
        task1.setTaskName("task 1");
        task1.setCompleted(false);

        TodoTask task2 = new TodoTask();
        task2.setTaskName("task 2");
        task2.setCompleted(false);

        TodoTask task3 = new TodoTask();
        task3.setTaskName("task 3");
        task3.setCompleted(true);

        TodosList list = new TodosList();
        list.setListName("List 1");
        list.setTasks(Arrays.asList(task1, task2));

        repository.save(list);

        list.setTasks(Arrays.asList(task1, task2, task3));

        repository.save(list);
    }
}
