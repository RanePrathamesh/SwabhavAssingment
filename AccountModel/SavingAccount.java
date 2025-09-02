package com.aurionpro.AccountModel;

import java.util.Scanner;



public class SavingAccount extends Account {
    private int MINI_BALANCE = 500;  

    
    public SavingAccount(int MINI_BALANCE) {
        super();
        this.MINI_BALANCE = MINI_BALANCE;
    }

    public SavingAccount() {
        super();
    }

    
    public SavingAccount(String account_number, String name, float balance, AccountType accountType) {
        super(account_number, name, balance, accountType);
    }

    
    public int getMINI_BALANCE() {
        return MINI_BALANCE;
    }

    
    public void setMINI_BALANCE(int MINI_BALANCE) {
        this.MINI_BALANCE = MINI_BALANCE;
    }

    
    @Override
    public String toString() {
        return "SavingAccount [MINI_BALANCE=" + MINI_BALANCE + "]";
    }

    
    public void deposite(int amount) {
    	
        setBalance(getBalance() + amount); 
        System.out.println("Amount deposited: " + amount + ". New Balance: " + getBalance());
    }

    
    public void withdrwal(int amount) {
        float balance = getBalance();

            setBalance(balance - amount);  
            System.out.println("Withdrawal successful! New Balance: " + getBalance());
        
    }

  
    public void showSavingAccountMenu(Scanner scanner) throws MinimumBalanceExeption {
        while (true) {
            System.out.println("\nYour saving account is ready. Please choose an option:");
            System.out.println("1 - Deposit");
            System.out.println("2 - Withdraw");
            System.out.println("3 - View Balance");
            System.out.println("4 - Exit");

            int userChoice = scanner.nextInt();
            scanner.nextLine();  
            switch (userChoice) {
                case 1:try {
                	System.out.print("Enter amount to deposit: ");
                    int depositAmount = scanner.nextInt();
                    scanner.nextLine();
                    if(depositAmount <0) throw new NegativeAmount(depositAmount);
                    deposite(depositAmount);
                    System.out.println("Current balance: " + getBalance());
                   
                }catch(NegativeAmount negativeAmount) {
                	System.out.println(negativeAmount.getMessage());
                } break;
                    
                case 2:
                	try {
                		System.out.print("Enter amount to withdraw: ");
                        int withdrawAmount = scanner.nextInt();
                        if (balance - withdrawAmount < MINI_BALANCE) throw new MinimumBalanceExeption(withdrawAmount);
                        scanner.nextLine();
                        withdrwal(withdrawAmount);
                       
                	}catch(MinimumBalanceExeption out) {
                		System.out.println(out.getMessage());
                	} break;
                    
                case 3:
                   
                    System.out.println("Your current balance is: " + getBalance());
                    break;
                case 4:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
                    break;
            }
        }
    }
}
