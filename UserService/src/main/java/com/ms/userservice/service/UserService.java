package com.ms.userservice.service;

import com.ms.userservice.dto.UserRequestDTO;
import com.ms.userservice.model.Users;
import com.ms.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Users addUser(UserRequestDTO dto) {
        Users user = new Users();

        user.setFirstName(dto.getFirstName());
        user.setSecondName(dto.getSecondName());
        user.setThirdName(dto.getThirdName());
        user.setAge(dto.getAge());
        user.setRegistrationDate(Timestamp.valueOf(LocalDateTime.now()));

        return user;
    }

}
