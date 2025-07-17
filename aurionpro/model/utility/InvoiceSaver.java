package com.aurionpro.model.utility;

import com.aurionpro.model.order.Order;
import com.aurionpro.model.order.OrderItem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class InvoiceSaver {

    public static void saveInvoice(Order order, String customerName, String deliveryPartnerName) {
        String folderPath = "./invoices";
        String fileName = "Invoice_Order_" + order.getOrderId() + ".txt";

        try {
           
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }

           
            File invoiceFile = new File(folder, fileName);
            try (PrintWriter writer = new PrintWriter(new FileWriter(invoiceFile))) {
                writer.println("========== FINAL INVOICE ==========");
                writer.println("Customer: " + customerName);
                writer.println("Date: " + LocalDateTime.now());
                writer.println("Delivery Partner: " + deliveryPartnerName);
                writer.println("-----------------------------------");
                writer.println("Order ID: " + order.getOrderId());

                for (OrderItem item : order.getOrderItems()) {
                    writer.println(item);
                }

                writer.println("-----------------------------------");
                writer.printf("Total Payable Amount: ₹%.2f\n", order.getTotalAmount());
                writer.println("===================================");
            }

            System.out.println(" Invoice saved as: " + invoiceFile.getAbsolutePath());

        } catch (IOException e) {
            System.out.println(" Failed to save invoice: " + e.getMessage());
        }
    }
}
