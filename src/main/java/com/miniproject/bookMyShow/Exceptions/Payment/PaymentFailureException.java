package com.miniproject.bookMyShow.Exceptions.Payment;

import com.miniproject.bookMyShow.Exceptions.BookMyShowException;

public class PaymentFailureException extends BookMyShowException {
    public PaymentFailureException(String message) {
        super(message);
    }
}
