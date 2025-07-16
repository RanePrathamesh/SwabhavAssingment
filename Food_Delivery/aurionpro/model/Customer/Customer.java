package com.aurionpro.model.Customer;

public class Customer {
    private String name;
    private String mobileNumber;

    public Customer(String name, String mobileNumber) {
        this.name = name;
        this.mobileNumber = mobileNumber;
    }

    public String getName() {
        return name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    @Override
    public String toString() {
        return "Customer: " + name + " | Mobile: " + mobileNumber;
    }
}
