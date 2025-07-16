package com.aurionpro.model.customer_app;

import java.util.List;
import java.util.Scanner;

import com.aurionpro.exceptions.PaymentFailedException;
import com.aurionpro.exceptions.DeliveryAssignmentException;

import com.aurionpro.model.Customer.Customer;
import com.aurionpro.model.delivery.*;
import com.aurionpro.model.food.Food;
import com.aurionpro.model.initializer.AppInitializer;
import com.aurionpro.model.menu.*;
import com.aurionpro.model.order.*;
import com.aurionpro.model.payment.*;
import com.aurionpro.model.utility.InvoiceSaver;

public class CustomerApp {

	public static void start(Scanner scanner) {
		AppInitializer.initializeDefaults();

		int nextOrderId = OrderManager.getNextOrderId();
		Order order = new Order(nextOrderId);

		boolean addMoreCuisines = true;

		while (addMoreCuisines) {
			List<IMenueType> menus = MenuRegistry.getMenuTypes();

			System.out.println("\n========= Choose a Cuisine =========");
			for (int i = 0; i < menus.size(); i++) {
				System.out.println((i + 1) + ". " + menus.get(i).getMenu().getMenuName());
			}
			System.out.print("Enter your choice: ");

			int choice;
			try {
				choice = Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Invalid input! Please enter a number.");
				continue;
			}

			if (choice < 1 || choice > menus.size()) {
				System.out.println("Invalid choice.");
				continue;
			}

			IMenueType menuType = menus.get(choice - 1);
			Menu selectedMenu = menuType.getMenu();
			new MenuPrinter(selectedMenu).showMenu();

			// Food selection loop
			while (true) {
				System.out.print("\nEnter Food ID to add to order (or type 'remove', 'done' to finish this menu): ");
				String input = scanner.nextLine().trim();

				if (input.equalsIgnoreCase("done"))
					break;

				if (input.equalsIgnoreCase("remove")) {
					System.out.print("Enter Food ID to remove: ");
					String removeId = scanner.nextLine().trim();

					System.out.print("Enter quantity to remove: ");
					try {
						int quantityToRemove = Integer.parseInt(scanner.nextLine());
						if (quantityToRemove <= 0) {
							System.out.println("Quantity must be greater than 0.");
							continue;
						}
						order.removeItem(removeId, quantityToRemove);
					} catch (NumberFormatException e) {
						System.out.println("Invalid quantity input.");
					}
					continue;
				}

				Food selectedFood = findFoodById(selectedMenu.getFoods(), input);
				if (selectedFood == null) {
					System.out.println("Food ID not found.");
					continue;
				}

				System.out.print("Enter quantity: ");
				try {
					int quantity = Integer.parseInt(scanner.nextLine());
					if (quantity <= 0) {
						System.out.println("Quantity must be greater than 0.");
						continue;
					}
					order.addItem(selectedFood, quantity);
					System.out.println("Item added to order.");
				} catch (NumberFormatException e) {
					System.out.println("Invalid quantity input.");
				}
			}

			System.out.print("\nDo you want to add items from another cuisine? (yes/no): ");
			if (!scanner.nextLine().trim().equalsIgnoreCase("yes")) {
				addMoreCuisines = false;
			}
		}

		// ===== Finalize Order =====
		order.printOrderSummary();

		System.out.print("\nEnter your name: ");
		String customerName = scanner.nextLine().trim();

		String mobileNumber;
		while (true) {
			System.out.print("Enter your 10-digit mobile number: ");
			mobileNumber = scanner.nextLine().trim();

			if (mobileNumber.matches("\\d{10}")) {
				break;
			} else {
				System.out.println("Invalid number! Please enter exactly 10 digits.");
			}
		}

		Customer customer = new Customer(customerName, mobileNumber);

		System.out.println("Choose payment method:");
		System.out.println("1. UPI");
		System.out.println("2. Cash");
		System.out.print("Enter your choice: ");

		int paymentChoice;
		try {
			paymentChoice = Integer.parseInt(scanner.nextLine());
		} catch (NumberFormatException e) {
			System.out.println("Invalid input! Please enter 1 or 2.");
			return;
		}

		PaymentMethod paymentMethod;
		switch (paymentChoice) {
			case 1 -> paymentMethod = new UpiPayment();
			case 2 -> paymentMethod = new CashPayment();
			default -> {
				System.out.println("Invalid payment option.");
				return;
			}
		}

		try {
			paymentMethod.pay(customer.getName());
		} catch (Exception e) {
			throw new PaymentFailedException("Payment failed for customer: " + customer.getName());
		}

		OrderManager.addOrder(order);

		IDeliveryPartner assignedPartner;
		try {
			assignedPartner = DeliveryAssigner.assignRandomPartner();
		} catch (Exception e) {
			throw new DeliveryAssignmentException("Delivery partner assignment failed.");
		}

		// ===== Final Invoice =====
		System.out.println("\n========== FINAL INVOICE ==========");
		System.out.println("Customer: " + customer.getName());
		System.out.println("Mobile: " + customer.getMobileNumber());
		System.out.println("Date: " + java.time.LocalDateTime.now());
		System.out.println("Delivery Partner: " + assignedPartner.getPartnerName());

		System.out.println("-----------------------------------");
		order.printOrderSummary();

		try {
			InvoiceSaver.saveInvoice(order, customer.getName(), assignedPartner.getPartnerName());
		} catch (Exception e) {
			System.out.println("Error saving invoice: " + e.getMessage());
		}
	}

	// Helper method to search by Food ID
	private static Food findFoodById(List<Food> foods, String id) {
		for (Food food : foods) {
			if (food.getFoodId().equalsIgnoreCase(id)) {
				return food;
			}
		}
		return null;
	}
}
