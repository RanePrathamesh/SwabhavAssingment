package com.aurionpro.model;

import java.sql.Timestamp;

public class Beneficiary {
    private int beneficiaryId;
    private int userId;
    private String beneficiaryName;
    private String accountNumber;
    private String bankName;
    private String ifscCode;
    private String nickname;
    private Timestamp createdAt;

    // Constructors
    public Beneficiary() {}

    public Beneficiary(int beneficiaryId, int userId, String beneficiaryName, String accountNumber,
                       String bankName, String ifscCode, String nickname, Timestamp createdAt) {
        this.beneficiaryId = beneficiaryId;
        this.userId = userId;
        this.beneficiaryName = beneficiaryName;
        this.accountNumber = accountNumber;
        this.bankName = bankName;
        this.ifscCode = ifscCode;
        this.nickname = nickname;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getBeneficiaryId() {
        return beneficiaryId;
    }

    public void setBeneficiaryId(int beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
