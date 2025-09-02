package com.aurionpro.model.payment;

import java.util.regex.Pattern;
import java.util.Scanner;

public class UpiPayment implements PaymentMethod {

    private static final Pattern upiPattern = Pattern.compile("^[\\w.]+@[a-zA-Z]+$");

    @Override
    public void pay(String customerName) {
        Scanner scanner = new Scanner(System.in);
        String upiId;

        while (true) {
            System.out.print("Enter your UPI ID (e.g. user@bank): ");
            upiId = scanner.nextLine().trim();

            if (upiPattern.matcher(upiId).matches()) {
                break;
            } else {
                System.out.println("Invalid UPI ID format. Try again.");
            }
        }

        System.out.println("\nPayment successful from " + customerName + " via UPI: " + upiId);
       
    }
}
