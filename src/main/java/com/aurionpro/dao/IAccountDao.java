package com.aurionpro.dao;

import java.util.List;

import com.aurionpro.model.Account;
import com.aurionpro.model.PendingAccountDto;

public interface IAccountDao {
	 List<PendingAccountDto> getPendingAccounts();                 
	    boolean approveAccountAndAssignNumber(int accountId);      
	    public Account getAccountByUserId(int userId) ;
	    boolean isAccountNumberExists(String accountNumber);
	    public double getTotalBankBalance();
	    public boolean rejectAccount(int accountId, String rejectionReason);
	    public List<PendingAccountDto> getRejectedAccounts() ;
}
