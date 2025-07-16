package com.aurionpro.model.order;

import java.util.ArrayList;
import java.util.List;

import com.aurionpro.model.food.Food;

public class Order {

	private List<OrderItem> orderItems;
	private int orderId;

	public Order(int orderId) {
		this.orderId = orderId;
		this.orderItems = new ArrayList<>();
	}

	public void addItem(Food food, int quantity) {
		for (OrderItem item : orderItems) {
			if (item.getFood().getFoodId().equals(food.getFoodId())) {
				item.setQuantity(item.getQuantity() + quantity);
				return;
			}
		}
		orderItems.add(new OrderItem(food, quantity));
	}

	public void removeItem(String foodId, int quantityToRemove) {
		for (int i = 0; i < orderItems.size(); i++) {
			OrderItem item = orderItems.get(i);
			if (item.getFood().getFoodId().equalsIgnoreCase(foodId)) {
				if (quantityToRemove >= item.getQuantity()) {
					orderItems.remove(i);
					System.out.println("Removed all of item: " + foodId);
				} else {
					item.setQuantity(item.getQuantity() - quantityToRemove);
					System.out.println("Removed " + quantityToRemove + " from item: " + foodId);
					System.out.println("Remaining quantity: " + item.getQuantity());
				}
				return;
			}
		}
		System.out.println("No item found with ID: " + foodId);
	}

	public void printOrderSummary() {
		System.out.println("\n========== ORDER SUMMARY ==========");
		System.out.println("Order ID: " + orderId);
		System.out.println("-----------------------------------");

		double total = 0;
		for (OrderItem item : orderItems) {
			System.out.println(item);
			total += item.getSubtotal();
		}

		System.out.println("-----------------------------------");
		System.out.printf("Total Payable Amount: ₹%.2f\n", total);
		System.out.println("===================================");
	}

	public double getTotalAmount() {
	    double total = 0;
	    for (OrderItem item : orderItems) {
	        total += item.getSubtotal();
	    }
	    return total;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public int getOrderId() {
		return orderId;
	}

}
