package com.aurionpro.model.order;

import com.aurionpro.model.food.Food;

public class OrderItem {
	private Food food;
	private int quantity;

	public OrderItem(Food food, int quantity) {
		super();
		this.food = food;
		this.quantity = quantity;

	}

	public Food getFood() {
		return food;
	}

	public void setFood(Food food) {
		this.food = food;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getSubtotal() {
		return food.getPrice() * quantity;

	}

	@Override
	public String toString() {
		return "ID: " + food.getFoodId() + " | Name: " + food.getFoodname() + " | Qty: " + quantity + " | Unit Price: ₹"
				+ food.getPrice() + " | Subtotal: ₹" + getSubtotal();
	}

}
