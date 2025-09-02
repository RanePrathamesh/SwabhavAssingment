package com.aurionpro.model.discount;

public class FlatDiscountOver500 implements IDiscount {

	private static final double DISCOUNT_THRESHOLD = 500.0;
	private static final double DISCOUNT_AMOUNT = 50.0;

	@Override
	public double applyDiscount(double totalAmount) {
		if (totalAmount > DISCOUNT_THRESHOLD) {
			return totalAmount - DISCOUNT_AMOUNT;
		}
		return totalAmount;
	}

	@Override
	public double getDiscountAmount(double totalAmount) {
		if (totalAmount > DISCOUNT_THRESHOLD) {
			return DISCOUNT_AMOUNT;
		}
		return 0.0;
	}
}
