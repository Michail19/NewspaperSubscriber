package com.ms.userservice.controller;

import com.ms.userservice.dto.UserRequestDTO;
import com.ms.userservice.dto.UserResponseDTO;
import com.ms.userservice.model.Users;
import com.ms.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @QueryMapping
    public UserResponseDTO getUser(@Argument long id) {
        return userService.getUser(id);
    }

    @MutationMapping
    public Users addUser(@Argument UserRequestDTO input) {
        return userService.addUser(input);
    }

    @MutationMapping
    public Users updateUser(@Argument long id, @Argument UserRequestDTO input) {
        return userService.updateUser(id, input);
    }

    @MutationMapping
    public boolean removeUser(@Argument long id) {
        userService.removeUser(id);
        return true;
    }
}
