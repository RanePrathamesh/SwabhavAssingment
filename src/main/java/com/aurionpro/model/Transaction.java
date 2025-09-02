package com.aurionpro.model;

import java.sql.Timestamp;

public class Transaction {

    private int transactionId;
    private int accountId;          
    private double amount;
    private String description;
    private String transactionType;
    private int relatedAccountId;  
    private Timestamp transactionDate;

    public Transaction() {
    }

   

    public Transaction(int transactionId, int accountId, double amount, String description, String transactionType,
			int relatedAccountId, Timestamp transactionDate) {
		super();
		this.transactionId = transactionId;
		this.accountId = accountId;
		this.amount = amount;
		this.description = description;
		this.transactionType = transactionType;
		this.relatedAccountId = relatedAccountId;
		this.transactionDate = transactionDate;
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

	public Timestamp getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Timestamp transactionDate) {
		this.transactionDate = transactionDate;
	}

	
    public int getAccountId() {
        return accountId;
    }
    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
    
    
    public int getRelatedAccountId() {
        return relatedAccountId;
    }
    public void setRelatedAccountId(int relatedAccountId) {
        this.relatedAccountId = relatedAccountId;
    }
}