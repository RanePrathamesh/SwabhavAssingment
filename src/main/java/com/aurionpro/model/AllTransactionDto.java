package com.aurionpro.model;

import java.sql.Timestamp;

public class AllTransactionDto {

	private int transactionId;	
	private double amount;
	private String description;
	private String transactionType;
	private int relatedAccountId;
	private Timestamp transactionDate;
	private String accountNumber;
	public AllTransactionDto(int transactionId, double amount, String description, String transactionType,
			int relatedAccountId, Timestamp transactionDate, String accountNumber) {
		super();
		this.transactionId = transactionId;
		this.amount = amount;
		this.description = description;
		this.transactionType = transactionType;
		this.relatedAccountId = relatedAccountId;
		this.transactionDate = transactionDate;
		this.accountNumber = accountNumber;
	}
	public int getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public int getRelatedAccountId() {
		return relatedAccountId;
	}
	public void setRelatedAccountId(int relatedAccountId) {
		this.relatedAccountId = relatedAccountId;
	}
	public Timestamp getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Timestamp transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	public AllTransactionDto() {
		
	}
	

}
