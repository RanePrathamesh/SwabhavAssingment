package com.aurionpro.utils;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatChecker {

	public static boolean isValidString(String name) {
		if (name == null || name.isEmpty()) {
			return false;
		}

		Pattern pattern = Pattern.compile("^[a-zA-z ]+$");
		Matcher matcher = pattern.matcher(name);

		return matcher.matches();
	}

	public static boolean isValidEmail(String email) {
		if (email == null || email.isEmpty())
			return false;

		Pattern pattern = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	public static boolean isMobileNumberValid(String mobile) {
	    if (mobile == null) return false;

	    String regex = "^[6-9]\\d{9}$";
	    return mobile.matches(regex);
	}


	public static boolean isValidPassword(String password) {
		if (password == null || password.isEmpty() || password.length() < 8)
			return false;
		Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,20}$");
		// At least 1 digit, 1 lowercase, 1 uppercase, 1 special char, no whitespace,
		// length 8-20
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();
	}

	public static int checkFormatInt(Scanner scanner, String message) {
		while (true) {
			System.out.println(message);
			String input = scanner.nextLine();

			try {
				int number = Integer.parseInt(input);

				if (number < 0) {
					System.out.println("Only non-negative integers are allowed.");
				} else {
					return number;
				}

			} catch (NumberFormatException exception) {
				System.out.println("Only integers are allowed.");
			}
		}
	}

	public static double checkFormatDouble(Scanner scanner, String message) {
		while (true) {
			System.out.println(message);
			String input = scanner.nextLine();

			try {
				double number = Double.parseDouble(input);

				if (number >= 0 && number <= 100) {
					return number;
				} else {
					System.out.println("Please enter a number between 0 and 100.");
				}

			} catch (NumberFormatException exception) {
				System.out.println("Only decimal (double) numbers are allowed.");
			}
		}
	}

	public static double checkFormatFee(Scanner scanner, String message) {
		while (true) {
			System.out.println(message);
			String input = scanner.nextLine();

			try {
				double number = Double.parseDouble(input);

				if (number > 0) {
					return number;
				} else {
					System.out.println("Please enter a valid fee.");
				}

			} catch (NumberFormatException exception) {
				System.out.println("Only decimal (double) numbers are allowed.");
			}
		}
	}
	
	public static boolean isIFSCCodeValid(String ifscCode) {
	    if (ifscCode == null || ifscCode.isEmpty()) {
	        return false;
	    }

	    // IFSC Code Format: 4 letters + 0 + 6 alphanumeric characters
	    String regex = "^[A-Z]{4}0[A-Z0-9]{6}$";
	    Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(ifscCode);
	    return matcher.matches();
	}
	
	public static boolean isInternationalAccountNumberValid(String accountNumber) {
	    if (accountNumber == null || accountNumber.isEmpty()) {
	        return false;
	    }

	    // IBAN: Starts with 2 letters (country code), followed by 2 digits (check digits),
	    // and then up to 30 alphanumeric characters
	   // String regex = "^[A-Z]{2}\\d{2}[A-Z0-9]{10,30}$";
	    String regex = "^[A-Z0-9]{9,15}$";

	    Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(accountNumber);
	    return matcher.matches();
	}




}
