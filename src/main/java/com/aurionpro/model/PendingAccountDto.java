package com.aurionpro.model;

import java.sql.Timestamp;

public class PendingAccountDto {
    private int accountId;
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
  
    private Timestamp createdAt;
    private String aadharFilePath;  
    private String aadharNumber;
    private boolean isApproved; 
    private String rejectionReason; 

    public PendingAccountDto(int accountId, int userId, String firstName, String lastName, String email, 
                             Timestamp createdAt, String aadharFilePath, String aadharNumber, 
                             boolean isApproved, String rejectionReason) {
        this.accountId = accountId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.createdAt = createdAt;
        this.aadharFilePath = aadharFilePath;
        this.aadharNumber = aadharNumber;
        this.isApproved = isApproved;
        this.rejectionReason = rejectionReason; 
    }
   
    
    
    public String getRejectionReason() {
		return rejectionReason;
	}



	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}



	public PendingAccountDto(int accountId, int userId, String firstName, String lastName, String email,
            Timestamp createdAt, String aadharFilePath, String aadharNumber, boolean isApproved) {
        this.accountId = accountId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.createdAt = createdAt;
        this.aadharFilePath = aadharFilePath;
        this.aadharNumber = aadharNumber;
        this.isApproved = isApproved;
    }
    

    // Getter and Setter for isApproved
    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean isApproved) {
        this.isApproved = isApproved;
    }

    // Other Getters and Setters
    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getAadharFilePath() {
        return aadharFilePath;
    }

    public void setAadharFilePath(String aadharFilePath) {
        this.aadharFilePath = aadharFilePath;
    }

    public String getAadharNumber() {
        return aadharNumber;
    }

    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }
}
