package jala.guaxi.ds3_capstone.service.exception;
// error 400 - bad request
public class UserExistsException extends RuntimeException {

    public UserExistsException (){
        super("Existing User!");
    }
    public UserExistsException(String message) {
        super(message);
    }
}