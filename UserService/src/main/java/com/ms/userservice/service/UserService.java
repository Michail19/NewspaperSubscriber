package com.ms.userservice.service;

import com.ms.userservice.dto.UserRequestDTO;
import com.ms.userservice.dto.UserResponseDTO;
import com.ms.userservice.exception.UserNotFoundException;
import com.ms.userservice.model.Users;
import com.ms.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final MessagePublisher messagePublisher;

    @Autowired
    public UserService(UserRepository userRepository, MessagePublisher messagePublisher) {
        this.userRepository = userRepository;
        this.messagePublisher = messagePublisher;
    }

    public Users addUser(UserRequestDTO dto) {
        Users user = new Users();

        user.setFirstName(dto.getFirstName());
        user.setSecondName(dto.getSecondName());
        user.setThirdName(dto.getThirdName());
        user.setAge(dto.getAge());
        user.setRegistrationDate(Timestamp.valueOf(LocalDateTime.now()));

        userRepository.save(user);

        messagePublisher.sendSubscriptionCreatedMessage(
                "User created: id=" + user.getId() + ", name=" + user.getFirstName()
        );

        return user;
    }

    public Users updateUser(long id, UserRequestDTO dto) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден"));

        user.setFirstName(dto.getFirstName());
        user.setSecondName(dto.getSecondName());
        user.setThirdName(dto.getThirdName());
        user.setAge(dto.getAge());

        userRepository.save(user);

        messagePublisher.sendSubscriptionCreatedMessage(
                "User updated: id=" + user.getId()
        );

        return user;
    }

    public void removeUser(long id) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден"));

        userRepository.delete(user);

        messagePublisher.sendSubscriptionCreatedMessage(
                "User deleted: id=" + id
        );
    }

    public UserResponseDTO getUser(long id) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        return new UserResponseDTO(
                user.getId(),
                user.getFirstName(),
                user.getSecondName(),
                user.getThirdName(),
                user.getAge(),
                user.getRegistrationDate().toString()
        );
    }
}
