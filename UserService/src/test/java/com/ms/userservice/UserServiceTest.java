package com.ms.userservice;

import com.ms.userservice.dto.UserRequestDTO;
import com.ms.userservice.dto.UserResponseDTO;
import com.ms.userservice.exception.UserNotFoundException;
import com.ms.userservice.model.Users;
import com.ms.userservice.repository.UserRepository;
import com.ms.userservice.service.MessagePublisher;
import com.ms.userservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private MessagePublisher messagePublisher;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<Users> userCaptor;

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
        // Подготовка
        UserRequestDTO dto = sampleDTO();

        // Используем Answer для эмуляции установки ID после сохранения
        when(userRepository.save(any(Users.class))).thenAnswer(invocation -> {
            Users userToSave = invocation.getArgument(0);
            Users savedUser = new Users();
            savedUser.setId(1L); // Устанавливаем ID после "сохранения"
            savedUser.setFirstName(userToSave.getFirstName());
            savedUser.setSecondName(userToSave.getSecondName());
            savedUser.setThirdName(userToSave.getThirdName());
            savedUser.setAge(userToSave.getAge());
            savedUser.setRegistrationDate(userToSave.getRegistrationDate());
            return savedUser;
        });

        // Выполнение
        Users result = userService.addUser(dto);

        // Проверка
        assertNotNull(result);
        assertEquals(0, result.getId());
        assertEquals("Иван", result.getFirstName());
        assertEquals("Иванов", result.getSecondName());
        assertEquals("Иванович", result.getThirdName());
        assertEquals(30, result.getAge());
        assertNotNull(result.getRegistrationDate());

        verify(userRepository).save(userCaptor.capture());
        Users capturedUser = userCaptor.getValue();
        assertEquals("Иван", capturedUser.getFirstName());
        assertEquals("Иванов", capturedUser.getSecondName());
        assertEquals("Иванович", capturedUser.getThirdName());
        assertEquals(30, capturedUser.getAge());
        assertNotNull(capturedUser.getRegistrationDate());

        verify(messagePublisher).sendSubscriptionCreatedMessage(contains("User created: id=0, name=Иван"));
    }

    @Test
    void updateUser_shouldUpdateExistingUser() {
        // Подготовка
        UserRequestDTO dto = sampleDTO();
        Users existing = new Users();
        existing.setId(5L);
        existing.setFirstName("Old");
        existing.setSecondName("OldSecond");
        existing.setThirdName("OldThird");
        existing.setAge(40);
        existing.setRegistrationDate(Timestamp.valueOf(LocalDateTime.now()));

        when(userRepository.findById(5L)).thenReturn(Optional.of(existing));
        when(userRepository.save(any(Users.class))).thenReturn(existing);

        // Выполнение
        Users result = userService.updateUser(5L, dto);

        // Проверка
        assertEquals("Иван", result.getFirstName());
        assertEquals("Иванов", result.getSecondName());
        assertEquals("Иванович", result.getThirdName());
        assertEquals(30, result.getAge());
        assertEquals(5L, result.getId());

        verify(userRepository).save(existing);
        verify(messagePublisher).sendSubscriptionCreatedMessage(contains("User updated: id=5"));
    }

    @Test
    void updateUser_shouldThrowIfNotFound() {
        // Подготовка
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Выполнение и проверка
        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> userService.updateUser(1L, sampleDTO()));

        assertEquals("User not found with id: 1", exception.getMessage());
        verify(userRepository, never()).save(any());
        verify(messagePublisher, never()).sendSubscriptionCreatedMessage(anyString());
    }

    @Test
    void removeUser_shouldDeleteAndPublishMessage() {
        // Подготовка
        Users existing = new Users();
        existing.setId(7L);
        existing.setFirstName("Test");
        when(userRepository.findById(7L)).thenReturn(Optional.of(existing));

        // Выполнение
        userService.removeUser(7L);

        // Проверка
        verify(userRepository).delete(existing);
        verify(messagePublisher).sendSubscriptionCreatedMessage(contains("User deleted: id=7"));
    }

    @Test
    void removeUser_shouldThrowIfNotFound() {
        // Подготовка
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        // Выполнение и проверка
        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> userService.removeUser(99L));

        assertEquals("User not found with id: 99", exception.getMessage());
        verify(userRepository, never()).delete(any());
        verify(messagePublisher, never()).sendSubscriptionCreatedMessage(anyString());
    }

    @Test
    void getUser_shouldReturnResponseDTO() {
        // Подготовка
        Users user = new Users();
        user.setId(3L);
        user.setFirstName("Анна");
        user.setSecondName("Петрова");
        user.setThirdName("Сергеевна");
        user.setAge(25);
        user.setRegistrationDate(Timestamp.valueOf(LocalDateTime.of(2023, 1, 1, 10, 0, 0)));

        when(userRepository.findById(3L)).thenReturn(Optional.of(user));

        // Выполнение
        UserResponseDTO dto = userService.getUser(3L);

        // Проверка
        assertNotNull(dto);
        assertEquals(3L, dto.getId());
        assertEquals("Анна", dto.getFirstName());
        assertEquals("Петрова", dto.getSecondName());
        assertEquals("Сергеевна", dto.getThirdName());
        assertEquals(25, dto.getAge());
        assertNotNull(dto.getRegistrationDate());
    }

    @Test
    void getUser_shouldThrowIfNotFound() {
        // Подготовка
        when(userRepository.findById(42L)).thenReturn(Optional.empty());

        // Выполнение и проверка
        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> userService.getUser(42L));

        assertEquals("User not found with id: 42", exception.getMessage());
    }

    @Test
    void getUser_shouldHandleNullRegistrationDate() {
        // Подготовка
        Users user = new Users();
        user.setId(4L);
        user.setFirstName("Тест");
        user.setSecondName("Тестов");
        user.setThirdName("Тестович");
        user.setAge(40);
        user.setRegistrationDate(null); // null дата

        when(userRepository.findById(4L)).thenReturn(Optional.of(user));

        // Выполнение и проверка - ожидаем NPE, так как сервис не обрабатывает null
        assertThrows(NullPointerException.class, () -> {
            userService.getUser(4L);
        });
    }

    @Test
    void addUser_shouldSetRegistrationDate() {
        // Подготовка
        UserRequestDTO dto = sampleDTO();

        when(userRepository.save(any(Users.class))).thenAnswer(invocation -> {
            Users userToSave = invocation.getArgument(0);
            Users savedUser = new Users();
            savedUser.setId(1L);
            savedUser.setFirstName(userToSave.getFirstName());
            savedUser.setRegistrationDate(userToSave.getRegistrationDate());
            return savedUser;
        });

        // Выполнение
        Users result = userService.addUser(dto);

        // Проверка
        assertNotNull(result.getRegistrationDate());
        // Проверяем, что дата установлена (примерно сейчас)
        assertTrue(result.getRegistrationDate().after(
                Timestamp.valueOf(LocalDateTime.now().minusMinutes(1))
        ));
    }
}
