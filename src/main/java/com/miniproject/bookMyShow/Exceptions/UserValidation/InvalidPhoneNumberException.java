package com.miniproject.bookMyShow.Exceptions.UserValidation;

public class InvalidPhoneNumberException extends InvalidUserDetailsException {

    public InvalidPhoneNumberException(String message) {
        super(message);
    }
}
