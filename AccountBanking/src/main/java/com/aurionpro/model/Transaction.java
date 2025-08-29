package com.aurionpro.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Transaction {
    private int txnId;
    private int fromAccountId;
    private Integer toAccountId; // Nullable for external transfers
    private String toAccountNumber;
    private double amount;
    private String txnType;
    private Timestamp txnDate;
    private String status;

    // Constructor
    
    public Transaction() {}
    public Transaction(int txnId, int fromAccountId, Integer toAccountId, String toAccountNumber,
    		double amount, String txnType, Timestamp txnDate, String status) {
        this.txnId = txnId;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.toAccountNumber = toAccountNumber;
        this.amount = amount;
        this.txnType = txnType;
        this.txnDate = txnDate;
        this.status = status;
    }

    // Getters and setters
    public int getTxnId() { return txnId; }
    public void setTxnId(int txnId) { this.txnId = txnId; }

    public int getFromAccountId() { return fromAccountId; }
    public void setFromAccountId(int fromAccountId) { this.fromAccountId = fromAccountId; }

    public Integer getToAccountId() { return toAccountId; }
    public void setToAccountId(Integer toAccountId) { this.toAccountId = toAccountId; }

    public String getToAccountNumber() { return toAccountNumber; }
    public void setToAccountNumber(String toAccountNumber) { this.toAccountNumber = toAccountNumber; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getTxnType() { return txnType; }
    public void setTxnType(String txnType) { this.txnType = txnType; }

    public Timestamp getTxnDate() { return txnDate; }
    public void setTxnDate(Timestamp txnDate) { this.txnDate = txnDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
