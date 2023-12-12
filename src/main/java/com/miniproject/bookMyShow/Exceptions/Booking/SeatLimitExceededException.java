package com.miniproject.bookMyShow.Exceptions.Booking;

import com.miniproject.bookMyShow.Exceptions.BookMyShowException;

public class SeatLimitExceededException extends BookMyShowException {
    public SeatLimitExceededException(String message) {
        super(message);
    }
}
