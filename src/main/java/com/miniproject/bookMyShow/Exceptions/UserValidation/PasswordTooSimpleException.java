package com.miniproject.bookMyShow.Exceptions.UserValidation;

public class PasswordTooSimpleException extends InvalidUserDetailsException {
    public PasswordTooSimpleException(String message) {
        super(message);
    }
}
