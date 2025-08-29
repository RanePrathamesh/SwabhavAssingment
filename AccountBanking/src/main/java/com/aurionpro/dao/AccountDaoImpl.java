package com.aurionpro.dao;

import com.aurionpro.model.Account;

import com.aurionpro.model.PendingAccountDto;
import com.aurionpro.utils.DatabaseSource;

import java.security.SecureRandom;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDaoImpl implements IAccountDao {

    private final Connection connection;
    private final SecureRandom rng = new SecureRandom();

    public AccountDaoImpl() {
        this.connection = DatabaseSource.getinstance().getConnection();
    }

    @Override
    public List<PendingAccountDto> getPendingAccounts() {
        String sql = """
            SELECT a.account_id, a.user_id, u.first_name, u.last_name, u.email, a.created_at
            FROM accounts a
            JOIN users u ON u.user_id = a.user_id
            WHERE a.is_approved = FALSE OR a.account_number IS NULL
            ORDER BY a.created_at ASC
        """;
        List<PendingAccountDto> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new PendingAccountDto(
                        rs.getInt("account_id"),
                        rs.getInt("user_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getTimestamp("created_at")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

	
	 @Override
	    public boolean approveAccountAndAssignNumber(int accountId) {
	        // transaction: generate a unique number, set it, approve
	        boolean ok = false;
	        boolean oldAutoCommit = true;
	        try {
	            oldAutoCommit = connection.getAutoCommit();
	            connection.setAutoCommit(false);

	            // 1) generate a unique account number
	            String accountNumber = generateUniqueAccountNumber();

	            // 2) update row atomically (only if not already approved)
	            String updateSql = """
	                UPDATE accounts
	                SET account_number = ?, is_approved = TRUE
	                WHERE account_id = ? AND (is_approved = FALSE OR is_approved IS NULL)
	            """;
	            try (PreparedStatement ps = connection.prepareStatement(updateSql)) {
	                ps.setString(1, accountNumber);
	                ps.setInt(2, accountId);
	                int rows = ps.executeUpdate();
	                ok = rows == 1;
	            }

	            if (ok) connection.commit();
	            else connection.rollback();

	        } catch (SQLIntegrityConstraintViolationException dup) {
	            // If rare duplicate, just rollback and return false (service can retry if desired)
	            try { connection.rollback(); } catch (SQLException ignored) {}
	            dup.printStackTrace();
	            ok = false;
	        } catch (SQLException e) {
	            try { connection.rollback(); } catch (SQLException ignored) {}
	            e.printStackTrace();
	            ok = false;
	        } finally {
	            try { connection.setAutoCommit(oldAutoCommit); } catch (SQLException ignored) {}
	        }
	        return ok;
	    }

	 @Override
	 public Account getAccountByUserId(int userId) {
	     String sql = "SELECT * FROM accounts WHERE user_id = ?";
	     try (PreparedStatement ps = connection.prepareStatement(sql)) {
	         ps.setInt(1, userId);
	         try (ResultSet rs = ps.executeQuery()) {
	             if (rs.next()) {
	                 Account a = new Account();
	                 a.setAccountId(rs.getInt("account_id"));
	                 a.setUserId(rs.getInt("user_id"));
	                 a.setAccountNumber(rs.getString("account_number"));
	                 a.setBalance(rs.getDouble("balance"));
	                 a.setApproved(rs.getBoolean("is_approved"));
	                 a.setCreatedAt(rs.getTimestamp("created_at"));
	                 return a;
	             }
	         }
	     } catch (SQLException e) {
	         e.printStackTrace();
	     }
	     return null;
	 }

	 public double getBalanceByUserId(int userId) {
	     String sql = "SELECT balance FROM accounts WHERE user_id = ?";
	     try (PreparedStatement ps = connection.prepareStatement(sql)) {
	         ps.setInt(1, userId);
	         try (ResultSet rs = ps.executeQuery()) {
	             if (rs.next()) {
	                 return rs.getDouble("balance");
	             }
	         }
	     } catch (SQLException e) {
	         e.printStackTrace();
	     }
	     return 0.0;
	 }

	 @Override
	    public boolean isAccountNumberExists(String accountNumber) {
	        String sql = "SELECT 1 FROM accounts WHERE account_number = ? LIMIT 1";
	        try (PreparedStatement ps = connection.prepareStatement(sql)) {
	            ps.setString(1, accountNumber);
	            try (ResultSet rs = ps.executeQuery()) {
	                return rs.next();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return true; // be conservative
	        }
	    }
	 
	 private String generateUniqueAccountNumber() throws SQLException {
	        // Format: ACC + 12-digit number (total length 15)
	        // retry a few times if collision happens (extremely unlikely)
	        for (int i = 0; i < 5; i++) {
	            String candidate = "ACC" + random12Digits();
	            if (!isAccountNumberExists(candidate)) {
	                return candidate;
	            }
	        }
	        // As a last resort, use a timestamp-based suffix
	        String fallback = "ACC" + (System.currentTimeMillis());
	        return fallback.substring(0, Math.min(15, fallback.length()));
	    }

	    private String random12Digits() {
	        // generate 12 digits as a string
	        StringBuilder sb = new StringBuilder(12);
	        for (int i = 0; i < 12; i++) {
	            sb.append(rng.nextInt(10)); // 0..9
	        }
	        return sb.toString();
	    }
	
}