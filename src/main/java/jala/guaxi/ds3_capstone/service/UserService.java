package jala.guaxi.ds3_capstone.service;

import jala.guaxi.ds3_capstone.domain.dto.UserDto;
import jala.guaxi.ds3_capstone.domain.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();
    User getUserById(String id);
    User createUser(UserDto user);
    User UpdateUser(String id, UserDto user);
    void deleteUser(String id);

}
