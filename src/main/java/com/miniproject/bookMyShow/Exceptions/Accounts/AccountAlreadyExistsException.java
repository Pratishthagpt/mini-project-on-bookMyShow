package com.miniproject.bookMyShow.Exceptions.Accounts;

public class AccountAlreadyExistsException extends AccountsException{
    public AccountAlreadyExistsException(String message) {
        super(message);
    }
}
