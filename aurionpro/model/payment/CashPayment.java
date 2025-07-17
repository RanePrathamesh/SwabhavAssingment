package com.aurionpro.model.payment;

public class CashPayment implements PaymentMethod {

    @Override
    public void pay(String customerName) {
        System.out.println("\nPayment received in Cash from " + customerName + ".");
    }
}
