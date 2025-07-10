package com.aurionpro.AccountModel;

public class MinimumBalanceExeption extends Exception {
	private float amount;

	public MinimumBalanceExeption(float amount) {
		super();
		this.amount = amount;
	}
	
	public String getMessage() {
		return "you cannot withdraw. minimum balance must me 500 your bakance is :" + amount;
	}
}
