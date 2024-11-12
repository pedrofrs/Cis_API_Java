package jala.guaxi.ds3_capstone.controller.exception;

import jala.guaxi.ds3_capstone.service.exception.MissingRequeriedFieldsException;
import jala.guaxi.ds3_capstone.service.exception.UserExistsException;
import jala.guaxi.ds3_capstone.service.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<String> userNotFound(UserNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
    }

    @ExceptionHandler(UserExistsException.class)
    private ResponseEntity<String> userExists(UserExistsException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists.");
    }

    @ExceptionHandler(MissingRequeriedFieldsException.class)
    private ResponseEntity<String> missingRequestFieldsException(MissingRequeriedFieldsException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }
}
