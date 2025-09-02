package com.aurionpro.AccountModel;

import java.util.Scanner;

public class CuurentAccount extends Account {
    private float OVER_DRAFT_LIMIT = 50000;

    public CuurentAccount() {
        super();
    }

    public CuurentAccount(String account_number, String name, float balance, AccountType accountType) {
        super(account_number, name, balance, accountType);
    }

    public float getOVER_DRAFT_LIMIT() {
        return OVER_DRAFT_LIMIT;
    }

    public void setOVER_DRAFT_LIMIT(float OVER_DRAFT_LIMIT) {
        this.OVER_DRAFT_LIMIT = OVER_DRAFT_LIMIT;
    }

    @Override
    public String toString() {
        return "CuurentAccount [OVER_DRAFT_LIMIT=" + OVER_DRAFT_LIMIT + ", getAccount_number()=" + getAccount_number()
                + ", getName()=" + getName() + ", getBalance()=" + getBalance() + ", getAccountType()=" + getAccountType() + "]";
    }

    @Override
    public void deposite(int amount) {
        float balance = getBalance();
        float overdraftUsed = Math.abs(balance);
        
        if (balance < 0) {
              
            if (amount >= overdraftUsed) {
               
                amount -= overdraftUsed;
                setBalance(0);
                OVER_DRAFT_LIMIT=50000;
                
               
            } else {
                overdraftUsed -=amount;
                OVER_DRAFT_LIMIT+=amount; 
                setBalance(0);
                System.out.println("Your new balance: " + getBalance() );
                setBalance(balance+amount);
                return;
            }
        }
        setBalance(getBalance() + amount);
        System.out.println("Your new balance: " + getBalance() );
        

       
        
    }
    @Override
    public void withdrwal(int amount){
    	if(amount <0) {
    		System.out.println("invalid amount");
    		return;
    	}
    	float withdrawAbleAmount = balance + OVER_DRAFT_LIMIT;
    	if(amount > withdrawAbleAmount) {
    		
    		throw new OverDraftLimitReached(withdrawAbleAmount);
    	}
    	balance -=amount;
    	displayBalance();
    }
    
    public void displayBalance() {
    	float withdrawAbleAmount = balance + OVER_DRAFT_LIMIT;
    	if(balance <0) {
    		System.out.println("cuurent balance is 0" );
    		System.out.println("over draft"+ withdrawAbleAmount);
    		return;
    	}
    	System.out.println("balance"+balance);
    	System.out.println("over draft remaining"+ withdrawAbleAmount);
    }

   
//    public void withdrawMoney(int amount) {
//        float balance = getBalance();
//        float totalAvailableAmount = balance + OVER_DRAFT_LIMIT;
//
//        if (totalAvailableAmount < amount) {
//            System.out.println("Insufficient funds. Limit exceeded");
//        } else {
//            
//            if (balance >= amount) {
//                setBalance(balance - amount);
//                System.out.println("Withdrawal successful and Remaining balance: " + getBalance());
//            } else {
//                
//                float remainingAmount = amount - balance;
//                setBalance(0);  
//                OVER_DRAFT_LIMIT -= remainingAmount; 
//                System.out.println("Withdrawal successful using overdraft! Remaining overdraft limit: " + OVER_DRAFT_LIMIT);
//                System.out.println("your balance: " + getBalance());
//                setBalance(balance-amount);
//            }
//        }
//    }

    
    public void showCurrentAccountMenu(Scanner scanner) {
        while (true) {
            System.out.println("\nYour current account is ready. Please choose an option:");
            System.out.println("1 - Deposit");
            System.out.println("2 - Withdraw");
            System.out.println("3 - Exit");

            int userChoice = scanner.nextInt();
            scanner.nextLine();

            switch (userChoice) {
                case 1:
                    System.out.print("Enter amount to deposit: ");
                    int depositAmount = scanner.nextInt();
                    scanner.nextLine();
                    deposite(depositAmount);
                    System.out.println( "Overdraft limit: " + getOVER_DRAFT_LIMIT());
                    break;
                case 2:
                    System.out.print("Enter amount to withdraw: ");
                    int withdrawAmount = scanner.nextInt();
                    scanner.nextLine();
                    withdrwal(withdrawAmount);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
                    break;
            }
        }
    }

//	@Override
//	public void deposite(int amount) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void withdrwal(int amount) {
//		// TODO Auto-generated method stub
//		
//	}
}
