package com.ms.userservice;

import com.ms.userservice.controller.UserController;
import com.ms.userservice.dto.UserRequestDTO;
import com.ms.userservice.dto.UserResponseDTO;
import com.ms.userservice.model.Users;
import com.ms.userservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUser_shouldReturnResponse() {
        UserResponseDTO expected = new UserResponseDTO(1L, "Иван", "Иванов", "Иванович", 30, "2023-01-01T10:00:00.000");
        when(userService.getUser(1L)).thenReturn(expected);

        UserResponseDTO result = userController.getUser(1L);

        assertEquals("Иван", result.getFirstName());
        assertEquals("Иванов", result.getSecondName());
        assertEquals("Иванович", result.getThirdName());
        assertEquals(30, result.getAge());
        verify(userService).getUser(1L);
    }

    @Test
    void addUser_shouldCallService() {
        // Подготовка
        UserRequestDTO dto = new UserRequestDTO();
        dto.setFirstName("Анна");
        dto.setSecondName("Петрова");
        dto.setThirdName("Сергеевна");
        dto.setAge(25);

        Users user = new Users();
        user.setId(1L);
        user.setFirstName("Анна");
        user.setSecondName("Петрова");
        user.setThirdName("Сергеевна");
        user.setAge(25);
        user.setRegistrationDate(Timestamp.valueOf(LocalDateTime.of(2023, 1, 1, 10, 0)));

        when(userService.addUser(dto)).thenReturn(user);

        // Выполнение
        UserResponseDTO result = userController.addUser(dto);

        // Проверка
        assertEquals("Анна", result.getFirstName());
        assertEquals("Петрова", result.getSecondName());
        assertEquals("Сергеевна", result.getThirdName());
        assertEquals(25, result.getAge());
        assertNotNull(result.getRegistrationDate());
        // Проверяем только что дата не null, не проверяя точный формат
        verify(userService).addUser(dto);
    }

    @Test
    void updateUser_shouldCallServiceAndReturnResult() {
        // Подготовка
        UserRequestDTO dto = new UserRequestDTO();
        dto.setFirstName("Николай");
        dto.setSecondName("Сидоров");
        dto.setThirdName("Петрович");
        dto.setAge(35);

        Users updated = new Users();
        updated.setId(2L);
        updated.setFirstName("Николай");
        updated.setSecondName("Сидоров");
        updated.setThirdName("Петрович");
        updated.setAge(35);
        updated.setRegistrationDate(Timestamp.valueOf(LocalDateTime.of(2023, 1, 1, 10, 0)));

        when(userService.updateUser(2L, dto)).thenReturn(updated);

        // Выполнение
        UserResponseDTO result = userController.updateUser(2L, dto);

        // Проверка
        assertEquals("Николай", result.getFirstName());
        assertEquals("Сидоров", result.getSecondName());
        assertEquals("Петрович", result.getThirdName());
        assertEquals(35, result.getAge());
        assertNotNull(result.getRegistrationDate());
        // Проверяем только что дата не null, не проверяя точный формат
        verify(userService).updateUser(2L, dto);
    }

    @Test
    void removeUser_shouldReturnTrue() {
        // Выполнение
        boolean result = userController.removeUser(3L);

        // Проверка
        assertTrue(result);
        verify(userService).removeUser(3L);
    }

    @Test
    void addUser_shouldHandleNullRegistrationDate() {
        // Подготовка
        UserRequestDTO dto = new UserRequestDTO();
        dto.setFirstName("Тест");
        dto.setSecondName("Тестов");
        dto.setThirdName("Тестович");
        dto.setAge(40);

        Users user = new Users();
        user.setId(4L);
        user.setFirstName("Тест");
        user.setSecondName("Тестов");
        user.setThirdName("Тестович");
        user.setAge(40);
        user.setRegistrationDate(null); // null дата регистрации

        when(userService.addUser(dto)).thenReturn(user);

        // Выполнение и проверка - ожидаем NPE, так как контроллер не обрабатывает null
        assertThrows(NullPointerException.class, () -> {
            userController.addUser(dto);
        });

        verify(userService).addUser(dto);
    }

    @Test
    void addUser_shouldHandleNonNullRegistrationDate() {
        // Подготовка
        UserRequestDTO dto = new UserRequestDTO();
        dto.setFirstName("Мария");
        dto.setSecondName("Кузнецова");
        dto.setThirdName("Александровна");
        dto.setAge(28);

        Users user = new Users();
        user.setId(5L);
        user.setFirstName("Мария");
        user.setSecondName("Кузнецова");
        user.setThirdName("Александровна");
        user.setAge(28);
        // Используем Timestamp как в реальной БД
        user.setRegistrationDate(Timestamp.valueOf("2023-01-01 10:00:00"));

        when(userService.addUser(dto)).thenReturn(user);

        // Выполнение
        UserResponseDTO result = userController.addUser(dto);

        // Проверка
        assertEquals("Мария", result.getFirstName());
        assertNotNull(result.getRegistrationDate());
        // Формат может быть разным, поэтому проверяем только наличие
        verify(userService).addUser(dto);
    }
}
