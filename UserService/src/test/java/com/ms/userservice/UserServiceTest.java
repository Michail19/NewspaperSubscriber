package com.ms.userservice;

import com.ms.userservice.dto.UserRequestDTO;
import com.ms.userservice.dto.UserResponseDTO;
import com.ms.userservice.model.Users;
import com.ms.userservice.repository.UserRepository;
import com.ms.userservice.service.MessagePublisher;
import com.ms.userservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private MessagePublisher messagePublisher;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private UserRequestDTO sampleDTO() {
        UserRequestDTO dto = new UserRequestDTO();
        dto.setFirstName("Иван");
        dto.setSecondName("Иванов");
        dto.setThirdName("Иванович");
        dto.setAge(30);
        return dto;
    }

    @Test
    void addUser_shouldSaveAndPublishMessage() {
        UserRequestDTO dto = sampleDTO();
        Users savedUser = new Users();
        savedUser.setId(1L);
        savedUser.setFirstName(dto.getFirstName());

        when(userRepository.save(any(Users.class))).thenReturn(savedUser);

        Users result = userService.addUser(dto);

        assertNotNull(result);
        verify(userRepository).save(any(Users.class));
        verify(messagePublisher).sendSubscriptionCreatedMessage(contains("User created"));
    }

    @Test
    void updateUser_shouldUpdateExistingUser() {
        UserRequestDTO dto = sampleDTO();
        Users existing = new Users();
        existing.setId(5L);
        existing.setFirstName("Old");

        when(userRepository.findById(5L)).thenReturn(Optional.of(existing));

        Users result = userService.updateUser(5L, dto);

        assertEquals("Иван", result.getFirstName());
        verify(userRepository).save(existing);
        verify(messagePublisher).sendSubscriptionCreatedMessage(contains("User updated"));
    }

    @Test
    void updateUser_shouldThrowIfNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> userService.updateUser(1L, sampleDTO()));
    }

    @Test
    void removeUser_shouldDeleteAndPublishMessage() {
        Users existing = new Users();
        existing.setId(7L);
        when(userRepository.findById(7L)).thenReturn(Optional.of(existing));

        userService.removeUser(7L);

        verify(userRepository).delete(existing);
        verify(messagePublisher).sendSubscriptionCreatedMessage(contains("User deleted"));
    }

    @Test
    void removeUser_shouldThrowIfNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> userService.removeUser(99L));
    }

    @Test
    void getUser_shouldReturnResponseDTO() {
        Users user = new Users();
        user.setFirstName("Анна");
        user.setSecondName("Петрова");
        user.setThirdName("Сергеевна");
        user.setAge(25);
        user.setRegistrationDate(Timestamp.valueOf(LocalDateTime.now()));

        when(userRepository.findById(3L)).thenReturn(Optional.of(user));

        UserResponseDTO dto = userService.getUser(3L);

        assertEquals("Анна", dto.getFirstName());
        assertEquals(25, dto.getAge());
    }

    @Test
    void getUser_shouldThrowIfNotFound() {
        when(userRepository.findById(42L)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> userService.getUser(42L));
    }
}
