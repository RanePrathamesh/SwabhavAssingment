package com.aurionpro.model.utility;

import com.aurionpro.model.Customer.Customer;
import com.aurionpro.model.delivery.IDeliveryPartner;
import com.aurionpro.model.discount.IDiscount;
import com.aurionpro.model.order.Order;

public class InvoicePrinter {

    public static void printFinalInvoice(Order order, Customer customer, IDeliveryPartner partner, IDiscount discount) {
        System.out.println("\n========== FINAL INVOICE ==========");
        System.out.println("Customer: " + customer.getName());
        System.out.println("Mobile: " + customer.getMobileNumber());
        System.out.println("Date: " + java.time.LocalDateTime.now());
        System.out.println("Delivery Partner: " + partner.getPartnerName());
        System.out.println("-----------------------------------");

        double originalTotal = order.calculateTotalAmount();
        double discountAmount = discount.getDiscountAmount(originalTotal);
        double finalTotal = discount.applyDiscount(originalTotal);

        order.printOrderSummary();
        System.out.printf("Discount Applied: ₹%.2f\n", discountAmount);
        System.out.printf("Total Payable after Discount: ₹%.2f\n", finalTotal);
        System.out.println("===================================");
    }
}
