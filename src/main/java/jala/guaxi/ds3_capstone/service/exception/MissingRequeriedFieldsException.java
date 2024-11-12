package jala.guaxi.ds3_capstone.service.exception;
// error 400 - bad request
public class MissingRequeriedFieldsException extends RuntimeException {

    public MissingRequeriedFieldsException(String message) {
        super(message);
    }
}
