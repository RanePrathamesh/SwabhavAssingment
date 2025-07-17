package com.aurionpro.AppLauncher;

import java.util.Scanner;

import com.aurionpro.model.admin_app.AdminApp;
import com.aurionpro.model.customer_app.CustomerApp;
import com.aurionpro.model.initializer.AppInitializer;

public class MainAppLauncher {

    public static void main(String[] args) {
        AppInitializer.initializeDefaults();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n========== Welcome to the Food Delivery App ==========");
            System.out.println("Are you a:");
            System.out.println("1. Customer");
            System.out.println("2. Admin");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int userType;
            try {
                userType = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (userType) {
                case 1 -> {
                    do {
                        CustomerApp.start(scanner);
                        System.out.print("\n Do you want to place another order? (yes/no): ");
                    } while (scanner.nextLine().trim().equalsIgnoreCase("yes"));
                }

                case 2 -> AdminApp.start(scanner);

                case 3 -> {
                    System.out.println(" Thank you for using the Food Delivery App. Goodbye!");
                    scanner.close();
                    return;
                }

                default -> System.out.println(" Invalid choice. Try again.");
            }
        }
    }
}
