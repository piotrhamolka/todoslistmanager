package com.example.todoslist.controller;

import com.example.todoslist.model.TodoTask;
import com.example.todoslist.model.TodosList;
import com.example.todoslist.service.TodosListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class TodosListController {

    final Logger logger = LoggerFactory.getLogger(TodosListController.class);
    private long listId;

    @Autowired
    TodosListService service;

    @GetMapping("/lists")
    private String getAllLists(Model model) {
        model.addAttribute("lists", service.getAllLists());
        return "task-lists";
    }

    @GetMapping("/add-new-list")
    private String getCreatePage(Model model) {
        TodosList list = new TodosList();
        model.addAttribute("list", list);
        return "add-new-list";
    }

    @GetMapping("/add-new-task/{id}")
    private String getCreateTaskPage(@PathVariable("id") long id, Model model) {
        listId = id;
        TodoTask task = new TodoTask();
        model.addAttribute("task", task);
        return "add-new-task";
    }


    @RequestMapping(value = "/addlist", method = RequestMethod.POST)
    public String addList(@Valid @ModelAttribute(value = "list") TodosList list, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "add-new-list";
        }
        model.addAttribute("lists", service.saveList(list));
        return "redirect:/lists";
    }

    @RequestMapping(value = "/addTask", method = RequestMethod.POST)
    public String addTask(@Valid @ModelAttribute(value = "task") TodoTask task, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "add-new-task";
        }
        model.addAttribute("list", service.saveTask(listId, task));
        return "edit-list";
    }

    @GetMapping("/delete/{id}")
    public String deleteList(@PathVariable("id") long id, Model model) {

        model.addAttribute("lists", service.deleteList(id));
        return "redirect:/lists";
    }

    @GetMapping("/delete/{id}/{name}")
    public String deleteTask(@PathVariable("id") long id, @PathVariable("name") String name, Model model) {

        model.addAttribute("list", service.deleteTask(id, name));
        return "edit-list";
    }

    @GetMapping("/toggle/{id}")
    public String toggleList(@PathVariable("id") long id, Model model) {

        model.addAttribute("lists", service.toggleList(id));
        return "redirect:/lists";
    }

    @GetMapping("/toggle/{id}/{name}")
    public String toggleTask(@PathVariable("id") long id, @PathVariable("name") String name, Model model) {

        model.addAttribute("list", service.toggleTask(id, name));
        return "edit-list";
    }

    @GetMapping("/edit/{id}")
    public String getEditPage(@PathVariable("id") long id, Model model) {

        model.addAttribute("list", service.getList(id));
        return "edit-list";
    }

}