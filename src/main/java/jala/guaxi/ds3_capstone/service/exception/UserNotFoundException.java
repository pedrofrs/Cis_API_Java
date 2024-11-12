package jala.guaxi.ds3_capstone.service.exception;
// error 404 - not found
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("User Not Found!");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
