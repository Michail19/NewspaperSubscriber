package com.ms.userservice.controller;

import com.ms.userservice.service.MessagePublisher;
import com.ms.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    private final UserService userService;
    private final MessagePublisher messagePublisher;

    @Autowired
    public UserController(UserService userService, MessagePublisher messagePublisher) {
        this.userService = userService;
        this.messagePublisher = messagePublisher;
    }

}
