package com.aurionpro.AccountModel;

public class NegativeAmount extends RuntimeException{
	private float amount ;

	public NegativeAmount(float amount) {
		super();
		this.amount = amount;
	}
	public String getMessage() {
		return "you have entered negative amount "+amount;
	}
}
