package com.miniproject.bookMyShow.Exceptions.CancelBooking;

import com.miniproject.bookMyShow.Exceptions.BookMyShowException;

public class ShowStartedException extends BookMyShowException {

    public ShowStartedException(String message) {
        super(message);
    }
}
