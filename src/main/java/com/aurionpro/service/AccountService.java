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

  
    public List<PendingAccountDto> getAllPendingAccounts() {
        return accountDao.getPendingAccounts();
    }

   
    public boolean approveAccount(int accountId) {
        return accountDao.approveAccountAndAssignNumber(accountId);
    }

   
    public boolean rejectAccount(int accountId, String rejectionReason) {
        return accountDao.rejectAccount(accountId, rejectionReason);
    }

   
    public Account getAccountByUserId(int userId) {
        return accountDao.getAccountByUserId(userId);
    }

   
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
