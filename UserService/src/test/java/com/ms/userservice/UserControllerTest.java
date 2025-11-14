package com.ms.userservice;

import com.ms.userservice.controller.UserController;
import com.ms.userservice.dto.UserRequestDTO;
import com.ms.userservice.dto.UserResponseDTO;
import com.ms.userservice.model.Users;
import com.ms.userservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

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
        UserResponseDTO expected = new UserResponseDTO("Иван", "Иванов", "Иванович", 30, null);
        when(userService.getUser(1L)).thenReturn(expected);

        UserResponseDTO result = userController.getUser(1L);

        assertEquals("Иван", result.getFirstName());
        verify(userService).getUser(1L);
    }

    @Test
    void addUser_shouldCallService() {
        UserRequestDTO dto = new UserRequestDTO();
        dto.setFirstName("Анна");
        Users user = new Users();
        user.setFirstName("Анна");

        when(userService.addUser(dto)).thenReturn(user);

        Users result = userController.addUser(dto);

        assertEquals("Анна", result.getFirstName());
        verify(userService).addUser(dto);
    }

    @Test
    void updateUser_shouldCallServiceAndReturnResult() {
        UserRequestDTO dto = new UserRequestDTO();
        dto.setFirstName("Николай");
        Users updated = new Users();
        updated.setFirstName("Николай");

        when(userService.updateUser(2L, dto)).thenReturn(updated);

        Users result = userController.updateUser(2L, dto);

        assertEquals("Николай", result.getFirstName());
        verify(userService).updateUser(2L, dto);
    }

    @Test
    void removeUser_shouldReturnTrue() {
        boolean result = userController.removeUser(3L);
        assertTrue(result);
        verify(userService).removeUser(3L);
    }
}
