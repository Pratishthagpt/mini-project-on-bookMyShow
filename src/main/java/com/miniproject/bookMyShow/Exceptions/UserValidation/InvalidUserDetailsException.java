package com.miniproject.bookMyShow.Exceptions.UserValidation;

import com.miniproject.bookMyShow.Exceptions.BookMyShowException;

public class InvalidUserDetailsException extends BookMyShowException {
    public InvalidUserDetailsException(String message) {
        super(message);
    }
}
