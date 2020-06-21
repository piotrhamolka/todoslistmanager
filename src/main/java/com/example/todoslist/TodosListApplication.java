package com.example.todoslist;

import com.example.todoslist.repository.TodosListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.example.todoslist"})
@EnableJpaRepositories(basePackages = "com.example.todoslist.repository")
@EntityScan(basePackages = "com.example.todoslist.model")
public class TodosListApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodosListApplication.class, args);
    }


    @Autowired
    TodosListRepository repository;

}
