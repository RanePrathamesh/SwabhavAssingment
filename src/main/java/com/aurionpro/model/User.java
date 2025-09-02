package com.aurionpro.model;

import java.sql.Timestamp;

public class User {
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
    private int age;
    private String gender;
    private String address;
    private boolean isActive;
    private boolean isFirstLogin;
    private String role;
    private Timestamp createdAt;

  
    public User() {
    }

    
    public User(String firstName, String lastName, String email, String mobileNumber,
                int age, String gender, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.isActive = true;        // default 1
        this.isFirstLogin = true;    // default 1
        this.role = "CUSTOMER";      // default CUSTOMER
    }

    
    public User(int userId, String firstName, String lastName, String email, String mobileNumber,
                int age, String gender, String address, boolean isActive, boolean isFirstLogin,
                String role, Timestamp createdAt) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.isActive = isActive;
        this.isFirstLogin = isFirstLogin;
        this.role = role;
        this.createdAt = createdAt;
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

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isFirstLogin() {
        return isFirstLogin;
    }

    public void setFirstLogin(boolean firstLogin) {
        isFirstLogin = firstLogin;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

   
    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", address='" + address + '\'' +
                ", isActive=" + isActive +
                ", isFirstLogin=" + isFirstLogin +
                ", role='" + role + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
    private String deactivationReason;

    public String getDeactivationReason() {
        return deactivationReason;
    }

    public void setDeactivationReason(String deactivationReason) {
        this.deactivationReason = deactivationReason;
    }


	public User(String firstName, String lastName, String email, String mobileNumber, int age,
			String gender, String address,
			String deactivationReason) {
		super();
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.mobileNumber = mobileNumber;
		this.age = age;
		this.gender = gender;
		this.address = address;
		this.deactivationReason = deactivationReason;
	}
	private String aadharNumber;
	private String aadharFilePath;

	public String getAadharNumber() { return aadharNumber; }
	public void setAadharNumber(String aadharNumber) { this.aadharNumber = aadharNumber; }

	public String getAadharFilePath() { return aadharFilePath; }
	public void setAadharFilePath(String aadharFilePath) { this.aadharFilePath = aadharFilePath; }

}
