package jala.guaxi.ds3_capstone.service.imlp;

import jala.guaxi.ds3_capstone.domain.dto.UserDto;
import jala.guaxi.ds3_capstone.domain.model.User;
import jala.guaxi.ds3_capstone.domain.repository.UserRepository;
import jala.guaxi.ds3_capstone.service.exception.MissingRequeriedFieldsException;
import jala.guaxi.ds3_capstone.service.exception.UserExistsException;
import jala.guaxi.ds3_capstone.service.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return a User List")
    void getAllUsers() {
        List<User>users = List.of(new User("Rebecca","Becca","senha123"));
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userServiceImpl.getAllUsers();
        assertEquals(users, result);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return User when it exists")
    void getUserByIdCase1() {
        User user = new User("Rebecca","Becca","senha123");
        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        User result = userServiceImpl.getUserById("1");
        assertEquals(user, result);
        verify(userRepository, times(1)).findById("1");
    }

    @Test
    @DisplayName("Should throw User not found exception")
    void getUserByIdCase2() {
        when(userRepository.findById("1")).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userServiceImpl.getUserById("1"));
    }


    @Test
    @DisplayName("Should save User")
    void createUserCase1() {
        UserDto userDto = new UserDto();
        userDto.setName("Rebecca");
        userDto.setPassword("senha123");
        userDto.setLogin("Becca");

        User user = new User("Rebecca","Becca","senha123");

        when(userRepository.findByLogin(userDto.getLogin())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userServiceImpl.createUser(userDto);

        assertEquals(user, result);
        verify(userRepository, times(1)).findByLogin(userDto.getLogin());
    }

    @Test
    @DisplayName("Should throw user exists exception")
    void createUserCase2() {
        UserDto userDto = new UserDto();
        userDto.setName("Rebecca");
        userDto.setPassword("senha123");
        userDto.setLogin("Becca");

        when(userRepository.findByLogin(userDto.getLogin())).thenReturn(Optional.of(new User("Rebecca","Becca","senha123")));
        assertThrows(UserExistsException.class, () -> userServiceImpl.createUser(userDto));
    }

    @Test
    @DisplayName("Should throw Missing requeried field exception")
    void createUserCase3(){
        UserDto userDto = new UserDto();
        userDto.setName("Rebecca");
        userDto.setPassword("senha123");

        assertThrows(MissingRequeriedFieldsException.class, () -> userServiceImpl.createUser(userDto));
    }
    @Test
    @DisplayName("Should throw Missing requeried field exception")
    void createUserCase4(){
        UserDto userDto = new UserDto();
        userDto.setLogin("Rebecca");
        userDto.setPassword("senha123");

        assertThrows(MissingRequeriedFieldsException.class, () -> userServiceImpl.createUser(userDto));
    }
    @Test
    @DisplayName("Should throw Missing requeried field exception")
    void createUserCase5(){
        UserDto userDto = new UserDto();
        userDto.setName("Rebecca");
        userDto.setLogin("senha123");

        assertThrows(MissingRequeriedFieldsException.class, () -> userServiceImpl.createUser(userDto));
    }
    @Test
    @DisplayName("Should update user when data is valid")
    void updateUserCase1() {
        UserDto userDto = new UserDto();
        userDto.setName("RebeccaHardman");
        userDto.setPassword("senha123");
        userDto.setLogin("BexHard");

        User user = new User("Rebecca","Becca","senha123");

        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userServiceImpl.UpdateUser("1",userDto);
        verify(userRepository, times(1)).save(any(User.class));

    }

    @Test
    @DisplayName("Should throw user not found exception")
    void updateUserCase2() {
        UserDto userDto = new UserDto();
        userDto.setName("RebeccaHardman");
        userDto.setPassword("senha123");
        userDto.setLogin("BexHard");
        when(userRepository.findById("1")).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userServiceImpl.UpdateUser("1",userDto));
    }


    @Test
    @DisplayName("should delete user when it exists")
    void deleteUserCase1() {
        User user = new User("Rebecca","Becca","senha123");
        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        userServiceImpl.deleteUser("1");

        verify(userRepository, times(1)).deleteById("1");
    }

    @Test
    @DisplayName("should throw user not found exception")
    void deleteUserCase2() {
        when(userRepository.findById("1")).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userServiceImpl.deleteUser("1"));
    }

}