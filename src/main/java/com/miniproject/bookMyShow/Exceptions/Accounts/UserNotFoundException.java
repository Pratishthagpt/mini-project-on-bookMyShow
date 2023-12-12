package com.miniproject.bookMyShow.Exceptions.Accounts;

public class UserNotFoundException extends AccountsException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
