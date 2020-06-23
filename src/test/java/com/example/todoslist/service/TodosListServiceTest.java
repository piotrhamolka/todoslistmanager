package com.example.todoslist.service;

import com.example.todoslist.model.TodoTask;
import com.example.todoslist.model.TodosList;
import com.example.todoslist.repository.TodosListRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TodosListServiceTest {

    @InjectMocks
    private TodosListService service;

    @Mock
    private TodosListRepository repository;

    private TodosList list1, list2;
    private TodoTask task1, task2, task3;

    @Before
    public void setUp() {

        task1 = new TodoTask();
        task1.setTaskName("task 1");
        task1.setCompleted(false);

        task2 = new TodoTask();
        task2.setTaskName("task 2");
        task2.setCompleted(false);

        task3 = new TodoTask();
        task3.setTaskName("task 3");
        task3.setCompleted(true);

        List<TodoTask> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);

        list1 = new TodosList();
        list1.setListName("List 1");
        list1.setTasks(tasks);
        list1.setActive(true);

        list2 = new TodosList();
        list2.setListName("List 2");
        list2.setActive(false);
        list2.setTasks(tasks);

        when(repository.findAll())
                .thenReturn(Arrays.asList(list1, list2));

    }

    @Test
    public void whenValidLists_thenListsShouldBeFound() {

        List<TodosList> found = service.getAllLists();

        assertThat(found.size())
                .isEqualTo(2);
        assertThat(found.get(0).getListName())
                .isEqualTo("List 1");
        assertThat(found.get(1).getListName())
                .isEqualTo("List 2");

        assertThat(found.get(0).getTasks().size())
                .isEqualTo(3);
        assertThat(found.get(0).getTasks().get(0).getTaskName())
                .isEqualTo("task 1");
        assertThat(found.get(0).getTasks().get(1).getTaskName())
                .isEqualTo("task 2");
        assertThat(found.get(0).getTasks().get(2).getTaskName())
                .isEqualTo("task 3");
    }

    @Test
    public void whenValidId_thenListShouldBeFound() {

        long listId = 1;
        Mockito.when(repository.findById(listId))
                .thenReturn(Optional.of(list1));

        TodosList found = service.getList(listId);

        assertThat(found)
                .isNotNull();
        assertThat(found.getListName())
                .isEqualTo("List 1");

        assertThat(found.getTasks().size())
                .isEqualTo(3);
        assertThat(found.getTasks().get(0).getTaskName())
                .isEqualTo("task 1");
        assertThat(found.getTasks().get(1).getTaskName())
                .isEqualTo("task 2");
        assertThat(found.getTasks().get(2).getTaskName())
                .isEqualTo("task 3");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenInvalidId_thenException() {

        long listId = 11111;
        Mockito.when(repository.findById(listId))
                .thenThrow(new IllegalArgumentException());

        service.getList(listId);
    }

    @Test
    public void whenSaveValidLists_thenListsShouldBeFound() {

        Mockito.when(repository.save(list1))
                .thenReturn(list1);
        List<TodosList> found = service.saveList(list1);

        assertThat(found.size())
                .isEqualTo(2);
        assertThat(found.get(0).getListName())
                .isEqualTo("List 1");
        assertThat(found.get(1).getListName())
                .isEqualTo("List 2");

        assertThat(found.get(0).getTasks().size())
                .isEqualTo(3);
        assertThat(found.get(0).getTasks().get(0).getTaskName())
                .isEqualTo("task 1");
        assertThat(found.get(0).getTasks().get(1).getTaskName())
                .isEqualTo("task 2");
        assertThat(found.get(0).getTasks().get(2).getTaskName())
                .isEqualTo("task 3");
    }

    @Test
    public void whenSaveValidTask_thenListShouldBeFound() {

        TodoTask task4 = new TodoTask();
        task4.setTaskName("task 4");
        task4.setCompleted(true);

        List<TodoTask> taskList = new ArrayList<>();
        taskList.add(task1);
        taskList.add(task2);
        taskList.add(task3);

        TodosList list3 = new TodosList();
        list3.setListName("List 3");
        list3.setTasks(taskList);
        list3.setActive(true);

        Mockito.when(repository.save(list3))
                .thenReturn(list3);
        Mockito.when(repository.findById(3L))
                .thenReturn(Optional.of(list3));

        TodosList found = service.saveTask(3, task4);

        assertThat(found)
                .isNotNull();
        assertThat(found.getListName())
                .isEqualTo("List 3");

        assertThat(found.getTasks().size())
                .isEqualTo(4);
        assertThat(found.getTasks().get(0).getTaskName())
                .isEqualTo("task 1");
        assertThat(found.getTasks().get(1).getTaskName())
                .isEqualTo("task 2");
        assertThat(found.getTasks().get(2).getTaskName())
                .isEqualTo("task 3");
        assertThat(found.getTasks().get(3).getTaskName())
                .isEqualTo("task 4");
    }

    @Test
    public void whenDeleteValidList_thenOthersShouldBeFound() {

        Mockito.when(repository.existsById(list1.getId()))
                .thenReturn(true);
        Mockito.when(repository.findAll())
                .thenReturn(Collections.singletonList(list2));

        List<TodosList> found = service.deleteList(list1.getId());

        Mockito.verify(repository).deleteById(list1.getId());

        assertThat(found.size())
                .isEqualTo(1);
        assertThat(found.get(0).getListName())
                .isEqualTo("List 2");

        assertThat(found.get(0).getTasks().size())
                .isEqualTo(3);
        assertThat(found.get(0).getTasks().get(0).getTaskName())
                .isEqualTo("task 1");
        assertThat(found.get(0).getTasks().get(1).getTaskName())
                .isEqualTo("task 2");
        assertThat(found.get(0).getTasks().get(2).getTaskName())
                .isEqualTo("task 3");
    }

    @Test
    public void whenDeleteValidTask_thenListShouldBeFound() {

        TodoTask task5 = new TodoTask();
        task5.setTaskName("task 5");
        task5.setCompleted(false);

        TodoTask task6 = new TodoTask();
        task6.setTaskName("task 6");
        task6.setCompleted(false);

        TodoTask task7 = new TodoTask();
        task7.setTaskName("task 7");
        task7.setCompleted(true);

        List<TodoTask> tasks = new ArrayList<>();
        tasks.add(task5);
        tasks.add(task6);
        tasks.add(task7);

        TodosList list5 = new TodosList();
        list5.setListName("List 5");
        list5.setTasks(tasks);
        list5.setActive(true);

        Mockito.when(repository.findById(list5.getId()))
                .thenReturn(Optional.of(list5));
        list5.getTasks().remove(0);

        TodosList found = service.deleteTask(list5.getId(), task5.getTaskName());

        assertThat(found)
                .isNotNull();
        assertThat(found.getListName())
                .isEqualTo("List 5");

        assertThat(found.getTasks().size())
                .isEqualTo(2);
        assertThat(found.getTasks().get(0).getTaskName())
                .isEqualTo("task 6");
        assertThat(found.getTasks().get(1).getTaskName())
                .isEqualTo("task 7");
    }

    @Test
    public void whenToggleList_thenToggledListShouldBeFound() {

        Mockito.when(repository.findById(list1.getId()))
                .thenReturn(Optional.of(list1));
        Mockito.when(repository.save(list1))
                .thenReturn(list1);
        Mockito.when(repository.findAll())
                .thenReturn(Arrays.asList(list1, list2));

        // list 1 to be toggled to false, list 2 keeps storing its initial false value
        List<TodosList> found = service.toggleList(list1.getId());

        assertThat(found.size())
                .isEqualTo(2);
        assertThat(found.get(0).getListName())
                .isEqualTo("List 1");
        assertThat(found.get(1).getListName())
                .isEqualTo("List 2");

        assertThat(found.get(0).isActive())
                .isFalse();
        assertThat(found.get(1).isActive())
                .isFalse();
    }

    @Test
    public void whenToggleTask_thenToggledTaskShouldBeFound() {

        Mockito.when(repository.findById(list1.getId()))
                .thenReturn(Optional.of(list1));
        Mockito.when(repository.save(list1))
                .thenReturn(list1);

        // Task 2 of the list 1 to be toggled to true (completed), others should keep storing their initial values
        TodosList found = service.toggleTask(list1.getId(), task2.getTaskName());

        assertThat(found)
                .isNotNull();
        assertThat(found.getListName())
                .isEqualTo("List 1");

        assertThat(found.getTasks().get(0).isCompleted())
                .isFalse();
        assertThat(found.getTasks().get(1).isCompleted())
                .isTrue();
        assertThat(found.getTasks().get(2).isCompleted())
                .isTrue();
    }

}
