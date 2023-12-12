package com.miniproject.bookMyShow.Exceptions.Payment;

public class PaymentProviderNotFoundException extends PaymentFailureException {
    public PaymentProviderNotFoundException(String message) {
        super(message);
    }
}
