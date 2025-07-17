package com.aurionpro.model.discount;

public interface IDiscount {
	double applyDiscount(double totalAmount);
	double getDiscountAmount(double totalAmount);
}
