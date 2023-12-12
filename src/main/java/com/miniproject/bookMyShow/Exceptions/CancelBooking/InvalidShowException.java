package com.miniproject.bookMyShow.Exceptions.CancelBooking;

import com.miniproject.bookMyShow.Exceptions.BookMyShowException;

public class InvalidShowException extends BookMyShowException {
    public InvalidShowException(String message) {
        super(message);
    }
}
