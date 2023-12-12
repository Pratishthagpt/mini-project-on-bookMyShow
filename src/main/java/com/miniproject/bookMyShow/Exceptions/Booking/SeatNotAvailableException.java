package com.miniproject.bookMyShow.Exceptions.Booking;

import com.miniproject.bookMyShow.Exceptions.BookMyShowException;

public class SeatNotAvailableException extends BookMyShowException {
    public SeatNotAvailableException(String message) {
        super(message);
    }
}
