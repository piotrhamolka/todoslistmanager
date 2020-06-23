package com.example.todoslist.controller;

import com.example.todoslist.model.TodoTask;
import com.example.todoslist.model.TodosList;
import com.example.todoslist.service.TodosListService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class TodosListControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean(name = "todosListService")
    private TodosListService service;

    @Test
    public void givenLists_whenGetAllLists_thenReturnAllLists()
            throws Exception {

        TodosList list1 = new TodosList();
        TodosList list2 = new TodosList();
        List<TodosList> lists = Arrays.asList(list1, list2);

        given(service.getAllLists()).willReturn(lists);

        MvcResult result = mvc.perform(get("/lists")
                .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(view().name("task-lists"))
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isNotEmpty();
    }

    @Test
    public void givenList_whenAddNewList_thenReturnAllLists()
            throws Exception {

        TodosList list1 = new TodosList();
        list1.setListName("ABCD");
        TodosList list2 = new TodosList();
        List<TodosList> lists = Arrays.asList(list1, list2);

        given(service.saveList(list1)).willReturn(lists);
        given(service.getAllLists()).willReturn(lists);

        mvc.perform(post("/addlist")
                .content("listName=" + list1.getListName() + "&id=" + list1.getId())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(redirectedUrl("/lists"))
                .andExpect(view().name("redirect:/lists"));
    }

    @Test
    public void givenTask_whenAddNewTask_thenReturnCurrentList()
            throws Exception {

        TodosList list1 = new TodosList();
        TodoTask task = new TodoTask();
        list1.setListName("ABCD");
        task.setTaskName("abcd");
        list1.setTasks(Collections.singletonList(task));

        given(service.saveTask(list1.getId(), task)).willReturn(list1);

        MvcResult result = mvc.perform(post("/addTask")
                .content("taskName=" + task.getTaskName())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(view().name("edit-list"))
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isNotEmpty();
    }

    @Test
    public void whenCreateNewListPage_thenReturnViewWithSingleList()
            throws Exception {

        TodosList list1 = new TodosList();
        List<TodosList> lists = Collections.singletonList(list1);

        given(service.getAllLists()).willReturn(lists);

        MvcResult result = mvc.perform(get("/add-new-list")
                .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(view().name("add-new-list"))
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isNotEmpty();
    }

    @Test
    public void whenCreateNewTaskPage_thenReturnViewWithSingleTask()
            throws Exception {

        TodosList list1 = new TodosList();
        List<TodosList> lists = Collections.singletonList(list1);

        given(service.getAllLists()).willReturn(lists);

        MvcResult result = mvc.perform(get("/add-new-task/" + list1.getId())
                .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(view().name("add-new-task"))
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isNotEmpty();
    }

    @Test
    public void whenDeleteExistingList_thenReturnViewWithOthers()
            throws Exception {

        TodosList list1 = new TodosList();
        TodosList list2 = new TodosList();
        List<TodosList> lists = Collections.singletonList(list2);

        given(service.deleteList(list1.getId())).willReturn(lists);

        mvc.perform(get("/delete/" + list1.getId())
                .contentType(MediaType.TEXT_PLAIN))
                .andExpect(redirectedUrl("/lists"))
                .andExpect(view().name("redirect:/lists"));
    }
}
