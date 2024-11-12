package jala.guaxi.ds3_capstone.service.imlp;

import jala.guaxi.ds3_capstone.domain.dto.UserDto;
import jala.guaxi.ds3_capstone.domain.model.User;
import jala.guaxi.ds3_capstone.domain.repository.UserRepository;
import jala.guaxi.ds3_capstone.service.exception.MissingRequeriedFieldsException;
import jala.guaxi.ds3_capstone.service.exception.UserExistsException;
import jala.guaxi.ds3_capstone.service.exception.UserNotFoundException;
import jala.guaxi.ds3_capstone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
    }

    @Override
    public User createUser(UserDto userDto) {
        validateNullableAttributes(userDto);
        Optional<User> userByLogin = userRepository.findByLogin(userDto.getLogin());

        if (userByLogin.isPresent()){
            throw new UserExistsException();
        }
        return userRepository.save(new User(
                userDto.getName(),
                userDto.getLogin(),
                userDto.getPassword()));
    }

    @Override
    public User UpdateUser(String id, UserDto userDto) {
        validateNullableAttributes(userDto);
        User DbUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());

        User userUpdated = new User();
        userUpdated.setId(DbUser.getId());
        userUpdated.setName(userDto.getName());
        userUpdated.setPassword(userDto.getPassword());
        userUpdated.setLogin(userDto.getLogin());
        return userRepository.save(userUpdated);
    }

    @Override
    public void deleteUser(String id) {
        User userDb = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
        userRepository.deleteById(id);
    }

    private void validateNullableAttributes(UserDto userDto) {
        ofNullable(userDto.getName()).orElseThrow(() -> new MissingRequeriedFieldsException("Missing Name"));
        ofNullable(userDto.getLogin()).orElseThrow(() -> new MissingRequeriedFieldsException("Missing Login"));
        ofNullable(userDto.getPassword()).orElseThrow(() -> new MissingRequeriedFieldsException("Missing Password"));
    }
}