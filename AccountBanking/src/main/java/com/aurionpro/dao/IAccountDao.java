package com.aurionpro.dao;

import java.util.List;

import com.aurionpro.model.Account;
import com.aurionpro.model.PendingAccountDto;

public interface IAccountDao {
	 List<PendingAccountDto> getPendingAccounts();                 // admin list view
	    boolean approveAccountAndAssignNumber(int accountId);      // generates unique number + approves
	    public Account getAccountByUserId(int userId) ;
	    boolean isAccountNumberExists(String accountNumber);
}
