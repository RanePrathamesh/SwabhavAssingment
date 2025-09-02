package com.aurionpro.model;

import java.sql.Timestamp;

public class PendingAccountDto {
    private int accountId;
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private Timestamp createdAt;

    public PendingAccountDto(int accountId, int userId, String firstName, String lastName, String email, Timestamp createdAt) {
        this.accountId = accountId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.createdAt = createdAt;
    }

    // getters
    public int getAccountId() { return accountId; }
    public int getUserId() { return userId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public Timestamp getCreatedAt() { return createdAt; }
}
