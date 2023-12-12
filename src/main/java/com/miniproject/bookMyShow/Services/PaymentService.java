package com.miniproject.bookMyShow.Services;

import com.miniproject.bookMyShow.Exceptions.Payment.PaymentProviderNotFoundException;
import com.miniproject.bookMyShow.Models.*;
import com.miniproject.bookMyShow.Repositories.PaymentRepositoy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class PaymentService {
    private PaymentRepositoy paymentRepositoy;

    @Autowired
    public PaymentService(PaymentRepositoy paymentRepositoy) {
        this.paymentRepositoy = paymentRepositoy;
    }

    public Booking initializePayment (Booking booking, User user, int amount) {
        Scanner sc = new Scanner(System.in);
        List<Payment> payments = new ArrayList<>();
        while (amount > 0) {
            Payment payment = new Payment();
            System.out.println("Enter the amount to be paid by this payment - ");
            int subAmount = sc.nextInt();
            payment = requestPaymentFromProvider(subAmount, user);
            amount -= subAmount;
        }
        booking.setPayments(payments);
        return booking;
    }

    public Payment requestPaymentFromProvider (int amount, User user) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the payment gateway to process the payment : " +
                "1. Paytm \n" + "2. GooglePay \n" + "3. PhonePe \n" + "4. Net Banking \n");

        int response = sc.nextInt();
        Payment payment = new Payment();
        if (response == 1) {
            payment.setPaymentProvider(PaymentProvider.PAYTM);
            payment.setPaymentStatus(PaymentStatus.SUCCESSFUL);
        } else if (response == 2) {
            payment.setPaymentProvider(PaymentProvider.GOOGLE_PAY);
            payment.setPaymentStatus(PaymentStatus.SUCCESSFUL);
        } else if (response == 3) {
            payment.setPaymentProvider(PaymentProvider.PHONE_PE);
            payment.setPaymentStatus(PaymentStatus.SUCCESSFUL);
        } else if (response == 4) {
            payment.setPaymentProvider(PaymentProvider.NET_BANKING);
            payment.setPaymentStatus(PaymentStatus.SUCCESSFUL);
        }
        else {
            payment.setPaymentStatus(PaymentStatus.FAILURE);
            throw new PaymentProviderNotFoundException("Payment Failed!! Payment provider not found.");
        }
        System.out.println("Congratulations!! Payment has been done successfully.");
        Payment savedpayment = paymentRepositoy.save(payment);
        return payment;
    }
}
