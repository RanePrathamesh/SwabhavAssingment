package com.aurionpro.dao;

import com.aurionpro.model.Transaction;
import java.util.List;

public interface ITransactionDao {

    // Transfer money from user to beneficiary
	 public boolean transferMoney(int fromAccountId, int toAccountId, String toAccountNumber, double amount);

    // Get all transactions of a user (for passbook)
    List<Transaction> getTransactionsByAccount(int accountId);
}