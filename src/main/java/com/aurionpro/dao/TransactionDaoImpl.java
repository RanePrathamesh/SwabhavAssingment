package com.aurionpro.dao;

import com.aurionpro.model.Account;
import com.aurionpro.model.AllTransactionDto;
import com.aurionpro.model.Transaction;
import com.aurionpro.utils.DatabaseSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDaoImpl implements ITransactionDao {

	private Connection conn;
	public TransactionDaoImpl() {
		conn = DatabaseSource.getinstance().getConnection();
	}
    @Override
    public boolean transferMoney(int fromAccountId, int toAccountId, double amount, String description) {
        String debitQuery = "UPDATE accounts SET balance = balance - ? WHERE account_id = ? AND balance >= ?";
        String creditQuery = "UPDATE accounts SET balance = balance + ? WHERE account_id = ?";
        String insertTxnQuery = "INSERT INTO transactions (account_id, amount, transaction_type, description, related_account_id) VALUES (?, ?, ?, ?, ?)";

        try  {
            conn.setAutoCommit(false);

            // 1. Debit sender's account
            try (PreparedStatement ps = conn.prepareStatement(debitQuery)) {
                ps.setDouble(1, amount);
                ps.setInt(2, fromAccountId);
                ps.setDouble(3, amount);
                if (ps.executeUpdate() == 0) {
                    System.out.println("Debit failed: insufficient balance or account not found.");
                    conn.rollback();
                    return false;
                }
            }

            // 2. Credit receiver's account
            try (PreparedStatement ps = conn.prepareStatement(creditQuery)) {
                ps.setDouble(1, amount);
                ps.setInt(2, toAccountId);
                int creditRows = ps.executeUpdate();
                if (creditRows == 0) {
                    System.out.println("Credit failed: beneficiary account not found.");
                    conn.rollback();
                    return false;
                }
            }

            // 3. Insert DEBIT transaction for sender
            try (PreparedStatement ps = conn.prepareStatement(insertTxnQuery)) {
                ps.setInt(1, fromAccountId);
                ps.setDouble(2, amount);
                ps.setString(3, "DEBIT");
                ps.setString(4, description);
                ps.setInt(5, toAccountId);
                ps.executeUpdate();
            }
            
            Account account  = getAccountById(fromAccountId);
           String acc = account.getAccountNumber();

            // 4. Insert CREDIT transaction for receiver
            try (PreparedStatement ps = conn.prepareStatement(insertTxnQuery)) {
                ps.setInt(1, toAccountId);
                ps.setDouble(2, amount);
                ps.setString(3, "CREDIT");
                ps.setString(4, "Received from " + acc);
                ps.setInt(5, fromAccountId);
                ps.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
        	System.out.println("roll back took place "+ e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Transaction> getPassbookHistory(int accountId) {
        List<Transaction> list = new ArrayList<>();
        String query = "SELECT * FROM transactions WHERE account_id = ? ORDER BY transaction_date DESC";

        try (
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, accountId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Transaction t = new Transaction();
                    t.setTransactionId(rs.getInt("transaction_id"));
                    t.setAccountId(rs.getInt("account_id"));
                    t.setRelatedAccountId(rs.getInt("related_account_id"));
                    t.setAmount(rs.getDouble("amount"));
                    t.setTransactionType(rs.getString("transaction_type"));
                    t.setDescription(rs.getString("description"));
                    t.setTransactionDate(rs.getTimestamp("transaction_date"));
                    list.add(t);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ✅ NEW: Fetch account by ID (used for checking balance, etc.)
    public Account getAccountById(int accountId) {
        String query = "SELECT * FROM accounts WHERE account_id = ?";
        try (
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, accountId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Account account = new Account();
                    account.setAccountId(rs.getInt("account_id"));
                    account.setUserId(rs.getInt("user_id"));
                    account.setAccountNumber(rs.getString("account_number"));
                    account.setBalance(rs.getDouble("balance"));
                    return account;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Not found or error
    }
    
    public Account getAccountByAccountNumber(String accountNumber) {
        String query = "SELECT * FROM accounts WHERE account_number = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, accountNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Account account = new Account();
                    account.setAccountId(rs.getInt("account_id"));
                    account.setUserId(rs.getInt("user_id"));
                    account.setAccountNumber(rs.getString("account_number"));
                    account.setBalance(rs.getDouble("balance"));
                    return account;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean transferMoney(int fromAccountId, double amount, String description) {
        String debitQuery = "UPDATE accounts SET balance = balance - ? WHERE account_id = ? AND balance >= ?";
        String insertTxnQuery = "INSERT INTO transactions (account_id, amount, transaction_type, description) VALUES (?, ?, ?, ?)";

        try {
            conn.setAutoCommit(false);

            // 1. Debit sender
            try (PreparedStatement ps = conn.prepareStatement(debitQuery)) {
                ps.setDouble(1, amount);
                ps.setInt(2, fromAccountId);
                ps.setDouble(3, amount);
                if (ps.executeUpdate() == 0) {
                    conn.rollback();
                    return false;
                }
            }

            // 2. Insert DEBIT transaction only
            try (PreparedStatement ps = conn.prepareStatement(insertTxnQuery)) {
                ps.setInt(1, fromAccountId);
                ps.setDouble(2, amount);
                ps.setString(3, "DEBIT");
                ps.setString(4, description);
                ps.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        
        
    }

    @Override
    public List<Transaction> getPassbookHistory(int accountId, String fromDate, String toDate) {
        List<Transaction> list = new ArrayList<>();
        String query = "SELECT * FROM transactions WHERE account_id = ? AND DATE(transaction_date) BETWEEN ? AND ? ORDER BY transaction_date DESC";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, accountId);
            ps.setString(2, fromDate);
            ps.setString(3, toDate);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Transaction t = new Transaction();
                    t.setTransactionId(rs.getInt("transaction_id"));
                    t.setAccountId(rs.getInt("account_id"));
                    t.setRelatedAccountId(rs.getInt("related_account_id"));
                    t.setAmount(rs.getDouble("amount"));
                    t.setTransactionType(rs.getString("transaction_type"));
                    t.setDescription(rs.getString("description"));
                    t.setTransactionDate(rs.getTimestamp("transaction_date"));
                    list.add(t);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
	@Override
	public List<AllTransactionDto> getAllTransactions() {
	    List<AllTransactionDto> list = new ArrayList<>();
	    String query = "SELECT t.*, a.account_number, u.first_name, u.last_name "
	                 + "FROM transactions t "
	                 + "JOIN accounts a ON t.account_id = a.account_id "
	                 + "JOIN users u ON a.user_id = u.user_id "
	                 + "ORDER BY t.transaction_date DESC";
	    try (PreparedStatement ps = conn.prepareStatement(query);
	         ResultSet rs = ps.executeQuery()) {
	        while (rs.next()) {
	        	AllTransactionDto alltransaction = new AllTransactionDto();
	        	alltransaction.setAccountNumber(rs.getString("account_number"));
	        	alltransaction.setRelatedAccountId(rs.getInt("related_account_id"));
	        	alltransaction.setTransactionId(rs.getInt("transaction_id"));
	        	alltransaction.setAmount(rs.getDouble("amount"));
	        	alltransaction.setTransactionType(rs.getString("transaction_type"));
	        	alltransaction.setDescription(rs.getString("description"));
	        	alltransaction.setTransactionDate(rs.getTimestamp("transaction_date"));
	            
	            list.add(alltransaction);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return list;
	}


}
