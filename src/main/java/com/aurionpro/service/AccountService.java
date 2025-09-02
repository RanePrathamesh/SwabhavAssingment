package com.aurionpro.service;

import com.aurionpro.dao.AccountDaoImpl;
import com.aurionpro.model.Account;
import com.aurionpro.model.PendingAccountDto;

import java.util.List;

public class AccountService {

    private final AccountDaoImpl accountDao;

    public AccountService() {
        this.accountDao = new AccountDaoImpl();
    }

    /**
     * Fetch all accounts which are not yet approved (pending).
     */
    public List<PendingAccountDto> getAllPendingAccounts() {
        return accountDao.getPendingAccounts();
    }

    /**
     * Approve account and assign unique account number.
     * Returns true if approval success, false otherwise.
     */
    public boolean approveAccount(int accountId) {
        return accountDao.approveAccountAndAssignNumber(accountId);
    }

    /**
     * Reject the account and save rejection reason.
     * Returns true if rejection success, false otherwise.
     */
    public boolean rejectAccount(int accountId, String rejectionReason) {
        return accountDao.rejectAccount(accountId, rejectionReason);
    }

    /**
     * Fetch a single account by ID.
     */
    public Account getAccountByUserId(int userId) {
        return accountDao.getAccountByUserId(userId);
    }

    /**
     * Fetch only the balance for a given userId.
     */
    public double getBalanceByUserId(int userId) {
        return accountDao.getBalanceByUserId(userId);
    }

    public double getTotalBankBalance() {
        return accountDao.getTotalBankBalance();
    }
    public List<PendingAccountDto> getRejectedAccounts() {
    	return accountDao.getRejectedAccounts();
    }

    public String getRejectionReason(int userId) {
        Account account = accountDao.getAccountByUserId(userId);
        if (account != null && account.getUserId() == userId) {
            return accountDao.getRejectionReason(account.getAccountId()); 
        }
        return null;
    }
}
