package com.aurionpro.AccountTest;

import java.util.Scanner;

import com.aurionpro.AccountModel.AccountType;
import com.aurionpro.AccountModel.CuurentAccount;
import com.aurionpro.AccountModel.MinimumBalanceExeption;
import com.aurionpro.AccountModel.NegativeAmount;
import com.aurionpro.AccountModel.OverDraftLimitReached;
import com.aurionpro.AccountModel.SavingAccount;

public class AccountTest {
	public static void main(String[] args) throws MinimumBalanceExeption {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Select your account type: \n1 - Current Account \n2 - Saving Account");
		int userChoice = scanner.nextInt();
		scanner.nextLine();
		try {
			if (userChoice == 1) {
				CuurentAccount currentAccount = takeInputForCurrentAccount(scanner);
				currentAccount.showCurrentAccountMenu(scanner);
			} else if (userChoice == 2) {
				SavingAccount savingAccount = takeInputForSavingAccount(scanner);
				if (savingAccount != null) {
					savingAccount.showSavingAccountMenu(scanner);
				}
			} else {
				System.out.println("Invalid choice.");
			}

		} catch (MinimumBalanceExeption balanceExeption) {
			System.out.println(balanceExeption.getMessage());
		}finally {
			scanner.close();
		}

		
	}

	public static CuurentAccount takeInputForCurrentAccount(Scanner scanner) {
		CuurentAccount currentAccount = new CuurentAccount();
		currentAccount.setAccountType(AccountType.current);

		System.out.print("Enter your name: ");
		String name = scanner.nextLine();

		System.out.print("Enter your initial balance: ");
		float balance = scanner.nextFloat();
		scanner.nextLine();
		
		String accountNumber = currentAccount.accountNumberGenerate();

		return new CuurentAccount(accountNumber, name, balance, currentAccount.getAccountType());
	}

	public static SavingAccount takeInputForSavingAccount(Scanner scanner) throws MinimumBalanceExeption {
		System.out.print("Enter your name: ");
		String name = scanner.nextLine();

		float balance = 0;
		int attempts = 0;

		while (attempts < 2) {
			System.out.print("Enter your initial balance: ");
			balance = scanner.nextFloat();
			scanner.nextLine();
			

			if (balance >= 500) {
				SavingAccount savingAccount = new SavingAccount();
				savingAccount.setAccountType(AccountType.savings);

				String accountNumber = savingAccount.accountNumberGenerate();

				return new SavingAccount(accountNumber, name, balance, savingAccount.getAccountType());
			} else {
				attempts++;
				System.out.println(" mini deposite 500");
			}
		}
		throw new MinimumBalanceExeption(balance);
	}

}
