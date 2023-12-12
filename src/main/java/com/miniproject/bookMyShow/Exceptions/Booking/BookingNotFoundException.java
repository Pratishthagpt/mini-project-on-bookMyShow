package com.miniproject.bookMyShow.Exceptions.Booking;

import com.miniproject.bookMyShow.Exceptions.BookMyShowException;

public class BookingNotFoundException extends BookMyShowException {
    public BookingNotFoundException(String message) {
        super(message);
    }
}
