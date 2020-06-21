package com.example.todoslist.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

//@Data
//@NoArgsConstructor
@Embeddable
public class TodoTask {

    @NotBlank(message = "Task name cannot be null or empty")
    public String taskName;

    public boolean isCompleted;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TodoTask todoTask = (TodoTask) o;
        return isCompleted == todoTask.isCompleted &&
                taskName.equals(todoTask.taskName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskName, isCompleted);
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    @Override
    public String toString() {
        return "TodoTask{" +
                "taskName='" + taskName + '\'' +
                ", isCompleted=" + isCompleted +
                '}';
    }

    public TodoTask(@NotBlank(message = "Task name cannot be null or empty") String taskName, boolean isCompleted) {
        this.taskName = taskName;
        this.isCompleted = isCompleted;
    }

    public TodoTask() {}
}
