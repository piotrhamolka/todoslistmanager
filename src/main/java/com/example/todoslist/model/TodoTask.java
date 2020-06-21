package com.example.todoslist.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@Embeddable
public class TodoTask {

    @NotBlank(message = "Task name cannot be null or empty")
    public String taskName;

    public boolean isCompleted;

}
