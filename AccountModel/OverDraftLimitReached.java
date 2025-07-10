package com.aurionpro.AccountModel;

public class OverDraftLimitReached extends RuntimeException {
	private float amount;

	public OverDraftLimitReached(float amount) {
		super();
		this.amount = amount;
	}
	
	public String getMessage() {
		return "limit excedeed . money that you have is "+amount;
	}
	
	
}
