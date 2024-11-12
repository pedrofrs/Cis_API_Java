package jala.guaxi.ds3_capstone.controller;

import jala.guaxi.ds3_capstone.domain.dto.UserDto;
import jala.guaxi.ds3_capstone.domain.model.User;
import jala.guaxi.ds3_capstone.service.exception.UserNotFoundException;
import jala.guaxi.ds3_capstone.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return a list of User")
    void getUsers() {
        List<User> users = List.of(new User("Rebecca","Becca","Senha123"));
        when(userService.getAllUsers()).thenReturn(users);

        ResponseEntity<List<User>> response = userController.getUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
        verify(userService,times(1)).getAllUsers();
    }

    @Test
    @DisplayName("Should return user when found")
    void getUserByIdCase1() {
        User user = new User("Rebecca","Becca","Senha123");
        when(userService.getUserById("1")).thenReturn(user);

        ResponseEntity<User> response = userController.getUserById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
        verify(userService,times(1)).getUserById("1");
    }
    @Test
    @DisplayName("Should throw user not found")
    void getUserByIdCase2() {
        when(userService.getUserById("1")).thenThrow(new UserNotFoundException());

        try {
            userController.getUserById("1");
        } catch (UserNotFoundException e) {
            assertEquals("User Not Found!", e.getMessage());
        }
    }

    @Test
    @DisplayName("Should return updated user")
    void updateUser() {
        UserDto userDto = new UserDto();
        userDto.setName("Rebecca");
        userDto.setPassword("Senha123");
        userDto.setLogin("Becca");
        User updatedUser = new User("Rebecca Updated", "Becca", "senha1234");
        when(userService.UpdateUser("1", userDto)).thenReturn(updatedUser);

        ResponseEntity<User> response = userController.updateUser("1", userDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedUser, response.getBody());
        verify(userService, times(1)).UpdateUser("1", userDto);
    }

    @Test
    void deleteUser() {
        ResponseEntity<User> response = userController.deleteUser("1");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService, times(1)).deleteUser("1");
    }
}