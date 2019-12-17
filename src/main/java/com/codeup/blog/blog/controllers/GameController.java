package com.codeup.blog.blog.controllers;


import com.codeup.blog.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class GameController {




    @Autowired
    private UserService userService;

}
