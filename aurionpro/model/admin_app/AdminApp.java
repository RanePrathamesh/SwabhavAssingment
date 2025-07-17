package com.aurionpro.model.admin_app;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.aurionpro.model.admin.Admin;
import com.aurionpro.model.delivery.DeliveryRegistry;
import com.aurionpro.model.delivery.IDeliveryPartner;
import com.aurionpro.model.food.*;
import com.aurionpro.model.initializer.AppInitializer;
import com.aurionpro.model.menu.*;

public class AdminApp {

	public static void start(Scanner scanner) {
		AppInitializer.initializeDefaults();

		final String validAdminName = "om";
		final String validPassword = "admin123";

		System.out.print("Enter admin name: ");
		String enteredName = scanner.nextLine().trim();

		System.out.print("Enter admin password: ");
		String enteredPassword = scanner.nextLine().trim();

		if (!enteredName.equalsIgnoreCase(validAdminName) || !enteredPassword.equals(validPassword)) {
			System.out.println("Invalid admin credentials. Access denied!!!!!!");
			return;
		}

		Admin admin = new Admin(enteredName);
		System.out.println(" Welcome, " + admin.getName() + "!");

		while (true) {
			System.out.println("\n========== Admin Panel ==========");
			System.out.println("1. Modify Cuisine (Add/Remove Dishes)");

			System.out.println("2. Add New Delivery Partner");
			System.out.println("3. View Registered Cuisines");
			System.out.println("4. View Registered Delivery Partners");
			System.out.println("5. View All Invoices");
			System.out.println("6. Exit");

			System.out.print("Choose an option: ");
			int choice = 0;
			try {
				choice = Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("only number allowed");
			}

			switch (choice) {
			case 1 -> modifyExistingCuisine(scanner);
			case 2 -> addNewDeliveryPartner(scanner);
			case 3 -> viewCuisines();
			case 4 -> viewPartners();
			case 5 -> viewAllInvoices(scanner);
			case 6 -> {
				System.out.println(" Exiting admin panel. Goodbye, " + admin.getName() + "!");
				return;
			}
			default -> System.out.println(" Invalid choice.");
			}
		}
	}

	private static void modifyExistingCuisine(Scanner scanner) {
	    List<IMenueType> menus = MenuRegistry.getMenuTypes();
	    if (menus.isEmpty()) {
	        System.out.println("⚠ No cuisines available.");
	        return;
	    }

	    System.out.println("Choose cuisine to modify:");
	    for (int i = 0; i < menus.size(); i++) {
	        System.out.println((i + 1) + ". " + menus.get(i).getMenu().getMenuName());
	    }
	    int choice =0;
	    try {
	    	choice = Integer.parseInt(scanner.nextLine().trim());
	    }catch(NumberFormatException e) {
	    	System.out.println("only number allowed");
	    	
	    }
	    
	    if (choice < 1 || choice > menus.size()) {
	        System.out.println("❌ Invalid choice.");
	        return;
	    }

	    Menu selectedMenu = menus.get(choice - 1).getMenu();

	    while (true) {
	        System.out.println("\nModifying: " + selectedMenu.getMenuName());
	        System.out.println("1. Add Dish");
	        System.out.println("2. Remove Dish");
	        System.out.println("3. Back");
	        System.out.print("Choose: ");
	        String option = scanner.nextLine().trim();

	        switch (option) {
	            case "1" -> {
	                System.out.print("Enter food ID: ");
	                String id = scanner.nextLine().trim();

	                if (selectedMenu.getFoods().stream().anyMatch(f -> f.getFoodId().equalsIgnoreCase(id))) {
	                    System.out.println("❌ ID already exists in this cuisine.");
	                    break;
	                }

	                System.out.print("Enter food name: ");
	                String name = scanner.nextLine().trim();
	                System.out.print("Enter description: ");
	                String desc = scanner.nextLine().trim();
	                System.out.print("Enter price: ");
	                double price = Double.parseDouble(scanner.nextLine());
	                System.out.print("Enter type (Starter/MainCourse/Dessert): ");
	                String type = scanner.nextLine().trim();

	                IFoodType foodType = switch (type.toLowerCase()) {
	                    case "starter" -> new Starter();
	                    case "maincourse" -> new MainCourse();
	                    case "dessert" -> new Dessert();
	                    default -> null;
	                };

	                if (foodType == null) {
	                    System.out.println("❌ Invalid type.");
	                    break;
	                }

	                selectedMenu.addFood(new Food(id, name, desc, price, foodType));
	                System.out.println("✅ Dish added.");
	            }

	            case "2" -> {
	                System.out.print("Enter food ID to remove: ");
	                String id = scanner.nextLine().trim();

	                boolean removed = selectedMenu.getFoods().removeIf(f -> f.getFoodId().equalsIgnoreCase(id));
	                if (removed) {
	                    System.out.println("✅ Dish removed.");
	                } else {
	                    System.out.println("❌ Dish not found.");
	                }
	            }

	            case "3" -> { return; }
	            default -> System.out.println("❌ Invalid option.");
	        }
	    }
	}

	private static void addNewDeliveryPartner(Scanner scanner) {
		AppInitializer.DEBUG = true; // Turn on debug logging for this operation

		System.out.print("Enter delivery partner name: ");
		String name = scanner.nextLine().trim();

		IDeliveryPartner newPartner = () -> name;
		DeliveryRegistry.addPartner(newPartner);

		AppInitializer.DEBUG = false; // Optional: turn off debug after use
	}

	private static void viewCuisines() {
	    System.out.println("\n========== Registered Cuisines ==========\n");

	    for (IMenueType menuType : MenuRegistry.getMenuTypes()) {
	        Menu menu = menuType.getMenu();
	        System.out.println("🍽 " + menu.getMenuName());
	        System.out.println("--------------------------------------------------------------");
	        System.out.printf("%-10s %-20s %-12s %-10s\n", "Food ID", "Food Name", "Type", "Price (₹)");
	        System.out.println("--------------------------------------------------------------");

	        for (Food food : menu.getFoods()) {
	            System.out.printf("%-10s %-20s %-12s %-10.2f\n",
	                    food.getFoodId(),
	                    food.getFoodname(),
	                    food.getFoodType().getFoodType(),
	                    food.getPrice());
	        }

	        System.out.println("--------------------------------------------------------------\n");
	    }
	}


	private static void viewPartners() {
		System.out.println("\nRegistered Delivery Partners:");
		for (IDeliveryPartner partner : DeliveryRegistry.getPartners()) {
			System.out.println("- " + partner.getPartnerName());
		}
	}

	private static void viewAllInvoices(Scanner scanner) {
		File invoiceDir = new File("./invoices");

		File[] invoiceFiles = invoiceDir.listFiles((dir, name) -> name.endsWith(".txt"));

		if (invoiceFiles == null || invoiceFiles.length == 0) {
			System.out.println(" No invoices found.");
			return;
		}

		System.out.println("\n========== All Invoices ==========");
		List<Integer> invoiceIds = new ArrayList<>();

		for (File file : invoiceFiles) {
			String name = file.getName();
			try {
				String idPart = name.replace("Invoice_Order_", "").replace(".txt", "");
				int id = Integer.parseInt(idPart);
				invoiceIds.add(id);
				System.out.println(" Invoice ID: " + id);
			} catch (NumberFormatException e) {

			}
		}

		System.out.print("\nEnter the invoice ID to view (or type 'exit' to go back): ");
		String input = scanner.nextLine().trim();
		if (input.equalsIgnoreCase("exit"))
			return;

		try {
			int selectedId = Integer.parseInt(input);
			if (!invoiceIds.contains(selectedId)) {
				System.out.println(" Invalid ID.");
				return;
			}

			File selectedFile = new File("./invoices/Invoice_Order_" + selectedId + ".txt");
			System.out.println("\n========== Viewing Invoice ==========");
			Scanner fileReader = new Scanner(selectedFile);
			while (fileReader.hasNextLine()) {
				System.out.println(fileReader.nextLine());
			}
			fileReader.close();

		} catch (Exception e) {
			System.out.println(" Error reading invoice: " + e.getMessage());
		}
	}

}
