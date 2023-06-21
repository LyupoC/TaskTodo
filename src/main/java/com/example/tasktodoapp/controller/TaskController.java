package com.example.tasktodoapp.controller;


import com.example.tasktodoapp.dao.TaskRepository;
import com.example.tasktodoapp.dao.UserRepository;
import com.example.tasktodoapp.entity.Comment;
import com.example.tasktodoapp.entity.List;
import com.example.tasktodoapp.entity.Task;
import com.example.tasktodoapp.entity.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
@RequestMapping("/tasks")
public class TaskController {


    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;


    public void isUserAuthorized(String username, Task task) throws AccessDeniedException {

        if (task != null && !Objects.equals(task.getUser().getUsername(), username))
            throw new AccessDeniedException(username);

    }

    @GetMapping("/markCompleted/{taskId}/{focusElement}")
    public String deleteTask(Model theModel, @PathVariable int taskId, @PathVariable int focusElement, Authentication authentication) throws AccessDeniedException {


        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task id: " + taskId + " not found"));

        isUserAuthorized(authentication.getName(), task);

        task.setStatus(1);

        taskRepository.save(task);

        return "redirect:/lists/list" + "?scrollToList=" + focusElement;
    }


    @GetMapping("/delete/{taskId}")

    public String deleteTask(Model theModel, @PathVariable int taskId, Authentication authentication) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task id: " + taskId + " not found"));

        isUserAuthorized(authentication.getName(), task);

        int listId = task.getList().getId();

        taskRepository.delete(task);


        return "redirect:/lists/showListUpdateForm/" + listId;
    }

    @GetMapping("/{taskId}")

    public String showUpdateTaskForm(Model theModel, @PathVariable int taskId, Authentication authentication) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new IllegalArgumentException("Task id: " + taskId + " not found"));
        List list = task.getList();

        isUserAuthorized(authentication.getName(), task);


        Comment comment = new Comment();

        comment.setTask(task);

        theModel.addAttribute("comment", comment);
        theModel.addAttribute("task", task);
        theModel.addAttribute("list", list);
        theModel.addAttribute("username", authentication.getName());

        return "updateTask";
    }

    @PostMapping("/update")
    public String updateTask(
            @Valid
            @ModelAttribute
            Task task, BindingResult bindingResult,
            Authentication authentication,
            Model theModel) throws AccessDeniedException {

        if (bindingResult.hasErrors()) {

            List list = task.getList();

            Comment comment = new Comment();

            comment.setTask(task);

            theModel.addAttribute("comment", comment);
            theModel.addAttribute("list", list);
            bindingResult.getAllErrors().forEach(System.out::println);
            return "updateTask";
        }

        User user = userRepository.findByUsername(authentication.getName());

        task.setUser(user);

        taskRepository.save(task);

        return "redirect:/tasks/" + task.getId();


    }


}
