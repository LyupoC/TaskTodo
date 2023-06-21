package com.example.tasktodoapp.controller;

import com.example.tasktodoapp.dao.CommentsRepository;
import com.example.tasktodoapp.dao.UserRepository;
import com.example.tasktodoapp.entity.Comment;
import com.example.tasktodoapp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private UserRepository userRepository;

    @DeleteMapping("/{commentId}")
    public String deleteComment() {
        return null;
    }

    @PostMapping("/")
    public String createComment(Model theModel, @ModelAttribute Comment comment, Authentication authentication) {

        User user = userRepository.findByUsername(authentication.getName());
        comment.setUser(user);
        comment.setTitle("empty");

        commentsRepository.save(comment);

        return "redirect:/tasks/" + comment.getTask().getId();
    }

}

