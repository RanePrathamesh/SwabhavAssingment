package com.aurionpro.dao;

import com.aurionpro.model.Account;
import com.aurionpro.model.AllTransactionDto;
import com.aurionpro.model.Transaction;
import java.util.List;

public interface ITransactionDao {
    boolean transferMoney(int senderAccountId, int receiverAccountId, double amount, String description);
    List<Transaction> getPassbookHistory(int accountId);
	Account getAccountById(int accountId);
	 public Account getAccountByAccountNumber(String accountNumber);
	 boolean transferMoney(int fromAccountId, double amount, String description);
	 public List<Transaction> getPassbookHistory(int accountId, String fromDate, String toDate);
	List<AllTransactionDto> getAllTransactions();


}