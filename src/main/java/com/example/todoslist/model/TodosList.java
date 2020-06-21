package com.example.todoslist.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "lists")
@Data
@NoArgsConstructor
public class TodosList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "List name cannot be null or empty")
    private String listName;

    public boolean isActive = false;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "tasks", joinColumns = @JoinColumn(columnDefinition = "list_id"))
    private List<TodoTask> tasks;

}
