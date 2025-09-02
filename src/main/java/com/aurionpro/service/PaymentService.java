package com.aurionpro.service;

import java.util.List;

import com.aurionpro.dao.ITransactionDao;
import com.aurionpro.dao.TransactionDaoImpl;
import com.aurionpro.model.Account;
import com.aurionpro.model.AllTransactionDto;
import com.aurionpro.model.Transaction;

public class PaymentService {

    private ITransactionDao paymentDao;

    public PaymentService() {
        this.paymentDao = new TransactionDaoImpl();
    }

    /**
     * Transfer money from one account to another
     * @param senderAccountId account id of sender
     * @param receiverAccountId account id of beneficiary
     * @param amount amount to transfer
     * @param description a brief description of the transaction
     * @return true if success, false otherwise
     */
    public boolean transfer(int senderAccountId, int receiverAccountId, double amount, String description) {
        // Basic validation before hitting DB
        if (amount <= 0) {
            System.out.println("Invalid amount: " + amount);
            return false;
        }

        // Call DAO method for money transfer
        return paymentDao.transferMoney(senderAccountId, receiverAccountId, amount, description);
    }
    
    /**
     * Fetch the passbook history for a given account.
     * @param accountId account ID to fetch transactions for
     * @return List of transactions for the specified account
     */
    public List<Transaction> getPassbookHistory(int accountId) {
        return paymentDao.getPassbookHistory(accountId);
    }
    
    public Account getAccountById(int accountId) {
        return paymentDao.getAccountById(accountId);
    }
    
    public Account getAccountByAccountNumber(String accountNumber) {
        return paymentDao.getAccountByAccountNumber(accountNumber);
    }

    public boolean transferToExternal(int fromAccountId, double amount, String description) {
        return paymentDao.transferMoney(fromAccountId, amount, description);
    }
    public List<Transaction> getPassbookHistory(int accountId, String fromDate, String toDate) {
        return paymentDao.getPassbookHistory(accountId, fromDate, toDate);
    }

	public List<AllTransactionDto> getAllTransactions() {
		// TODO Auto-generated method stub
		return paymentDao.getAllTransactions();
	}


}