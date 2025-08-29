package com.aurionpro.service;

import java.util.List;

import com.aurionpro.dao.*;
import com.aurionpro.model.Transaction;

public class PaymentService {

    private ITransactionDao paymentDao;

    public PaymentService() {
        this.paymentDao = new TransactionDaoImpl();
    }

    /**
     * Transfer money from one account to another
     * @param fromAccountId account id of sender
     * @param toAccountId account id of beneficiary
     * @param toAccountNumber beneficiary account number
     * @param amount amount to transfer
     * @return true if success, false otherwise
     */
    public boolean transfer(int fromAccountId, int toAccountId, String toAccountNumber, double amount) {
        // Basic validation before hitting DB
        if (amount <= 0) {
            System.out.println("Invalid amount: " + amount);
            return false;
        }
        if (toAccountNumber == null || toAccountNumber.isEmpty()) {
            System.out.println("Beneficiary account is invalid.");
            return false;
        }

        // Call DAO method for money transfer
        return paymentDao.transferMoney(fromAccountId, toAccountId, toAccountNumber, amount);
    }
    
    /**
     * Fetch all transactions by account ID (both sent and received)
     * @param accountId account ID to fetch transactions for
     * @return List of transactions for the specified account
     */
    public List<Transaction> getTransactionsByAccountId(int accountId) {
        return paymentDao.getTransactionsByAccount(accountId);
    }
}
