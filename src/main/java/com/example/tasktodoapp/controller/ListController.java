package com.example.tasktodoapp.controller;

import com.example.tasktodoapp.dao.ListsRepository;
import com.example.tasktodoapp.dao.TaskRepository;
import com.example.tasktodoapp.dao.UserRepository;
import com.example.tasktodoapp.entity.List;
import com.example.tasktodoapp.entity.Task;
import com.example.tasktodoapp.entity.User;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.server.ResponseStatusException;

import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

@Controller
@RequestMapping("/lists")
public class ListController {

    @Autowired
    private ListsRepository listsRepository;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private TaskRepository taskRepository;


    @GetMapping("/delete/{listId}")
    public String deleteList(Model theModel, @PathVariable int listId) {


        listsRepository.deleteById(listId);

        return "redirect:/lists/list";
    }

    @GetMapping("/list")
    public String listLists(Model theModel, Authentication authentication) {

        User user = userRepository.findByUsername(authentication.getName());

        theModel.addAttribute("lists", listsRepository.findByUser(user));
        theModel.addAttribute("username", user.getUsername());

        return "list-lists";
    }


    @GetMapping("/showAddListForm")
    public String showAddForm(Model theModel, Authentication authentication) {
        List list = new List();

        theModel.addAttribute("list", list);
        theModel.addAttribute("username", authentication.getName());

        return "createList";
    }

    @GetMapping("/showListUpdateForm/{listId}")
    public String listTasks(Model theModel, @PathVariable int listId, Authentication authentication) throws AccessDeniedException {

        isUserAuthorized(authentication.getName(), listId);

        List list = listsRepository.findById(listId)
                .orElseThrow(() -> new IllegalArgumentException("TodoItem id: " + listId + " not found"));

        theModel.addAttribute("list", list);
        theModel.addAttribute("username", authentication.getName());

        return "updateList";
    }


    @PostMapping("/updateList")
    public String updateForm(@Valid @ModelAttribute List list, BindingResult bindingResult,
                             @RequestParam(value = "toDelete", required = false) java.util.List<Integer> taskIdsToDelete,
                             Authentication authentication,
                             Model theModel) throws AccessDeniedException {


        isUserAuthorized(authentication.getName(), list.getId());
        deleteDeletedTasks(list, taskIdsToDelete);

        return updateListAndTasks(list, authentication, bindingResult, theModel);
    }

    @PostMapping("/createList")
    public String createList(@Valid @ModelAttribute List list, BindingResult bindingResult,
                             @RequestParam(value = "toDelete", required = false) java.util.List<Integer> taskIdsToDelete,
                             Authentication authentication,
                             Model theModel) throws AccessDeniedException {


        deleteDeletedTasks(list, taskIdsToDelete);
        return saveListAndTasks(list, authentication, bindingResult, theModel);

    }


    public void isUserAuthorized(String username, int listID) throws AccessDeniedException, IllegalArgumentException {

        List tmpList = listsRepository.findById(listID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task id: " + listID + " not found"));

        if (tmpList == null || !Objects.equals(tmpList.getUser().getUsername(), username))
            throw new AccessDeniedException(username);


    }

    private void deleteDeletedTasks(List list, java.util.List<Integer> taskIdsToDelete) {

        Iterator<Task> iterator = list.getTasks().iterator();
        while (iterator.hasNext()) {

            Task task = iterator.next();

            if (taskIdsToDelete != null && taskIdsToDelete.contains(task.getId())) {
                taskRepository.delete(task);
                iterator.remove();

            } else if (task.getId() == 0 && task.getTitle() == null) {
                iterator.remove();
            }
        }

    }


    private String saveListAndTasks(List list, Authentication authentication,
                                    BindingResult bindingResult,
                                    Model theModel) {

        if (bindingResult.hasErrors()) {
            return "createList";
        }

        saveList(list, authentication);

        if (tasksAreNotValid(bindingResult, list.getTasks())) {
            theModel.addAttribute("list", list);
            return "createList";
        }

        taskRepository.saveAll(list.getTasks());

        return "redirect:/lists/list";

    }

    private String updateListAndTasks(List list, Authentication authentication,
                                      BindingResult bindingResult,
                                      Model theModel) {

        if (bindingResult.hasErrors()) {
            return "updateList";
        }

        saveList(list, authentication);

        if (tasksAreNotValid(bindingResult, list.getTasks())) {
            theModel.addAttribute("list", list);
            return "updateList";
        }

        taskRepository.saveAll(list.getTasks());

        return "redirect:/lists/list";

    }

    private void saveList(List list, Authentication authentication) {

        User user = userRepository.findByUsername(authentication.getName());

        for (Task task : list.getTasks()) {
            task.setList(list);
            task.setUser(user);
        }

        list.setUser(user);

        listsRepository.save(list);
    }

    private boolean tasksAreNotValid(BindingResult bindingResult, java.util.List<Task> tasks) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            Set<ConstraintViolation<Task>> newViolations = validator.validate(task);

            if (!newViolations.isEmpty()) {

                int taskNumber = i;
                newViolations.forEach(violation -> {
                    String field = "tasks[" + taskNumber + "]." + violation.getPropertyPath().toString();
                    bindingResult.rejectValue(field, "error.deadlineDate", violation.getMessage());
                });
            }
        }

        return bindingResult.hasErrors();

    }

}
