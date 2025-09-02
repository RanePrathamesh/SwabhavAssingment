package com.aurionpro.AccountModel;

public abstract class Account {
	private String account_number;
	private String name;
	protected float balance;
	private AccountType accountType;
	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Account(String account_number, String name, float balance, AccountType accountType) {
		super();
		this.account_number = account_number;
		this.name = name;
		this.balance = balance;
		this.accountType = accountType;
	}
	public String getAccount_number() {
		return account_number;
	}
	public void setAccount_number(String account_number) {
		this.account_number = account_number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getBalance() {
		return balance;
	}
	public void setBalance(float balance) {
		this.balance = balance;
	}
	public AccountType getAccountType() {
		return accountType;
	}
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}
	@Override
	public String toString() {
		return "Account [account_number=" + account_number + ", name=" + name + ", balance=" + balance
				+ ", accountType=" + accountType + "]";
	}
	
	public static String accountNumberGenerate() {
		String accountNumber = "IDBI1000" + (int) (Math.random() * 10000000);

//	    for (Account acc : records) {
//	        if (acc != null && acc.getAccountNumber().equals(accountNumber)) {
//	            
//	            return accountNumberGenerate(records);
//	        }
//	    }

		System.out.println("Account number generated: " + accountNumber);
		return accountNumber;

	}
	
	public abstract void deposite(int amount);
	public abstract void withdrwal(int amount);
	
}
