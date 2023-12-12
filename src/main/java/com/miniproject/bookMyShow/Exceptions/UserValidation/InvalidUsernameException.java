package com.miniproject.bookMyShow.Exceptions.UserValidation;

public class InvalidUsernameException extends InvalidUserDetailsException {
    public InvalidUsernameException(String message) {
        super(message);
    }
}
