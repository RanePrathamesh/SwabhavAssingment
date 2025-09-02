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
					    SELECT a.account_id, a.user_id, u.first_name, u.last_name, u.email, a.created_at, u.aadhar_file_path,u.aadhar_number,a.is_approved
					    FROM accounts a
					    JOIN users u ON u.user_id = a.user_id
					    WHERE a.is_approved = FALSE and u.rejection_reason is null
					    ORDER BY a.created_at ASC
				""";

		List<PendingAccountDto> list = new ArrayList<>();
		try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				list.add(new PendingAccountDto(rs.getInt("account_id"), rs.getInt("user_id"),
						rs.getString("first_name"), rs.getString("last_name"), rs.getString("email"),
						rs.getTimestamp("created_at"), rs.getString("aadhar_file_path"), rs.getString("aadhar_number"),
						rs.getBoolean("is_approved")));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean approveAccountAndAssignNumber(int accountId) {
	    boolean ok = false;
	    boolean oldAutoCommit = true;
	    try {
	        oldAutoCommit = connection.getAutoCommit();
	        connection.setAutoCommit(false);

	        
	        String accountNumber = generateUniqueAccountNumber();

	        
	        System.out.println("Generated Account Number: " + accountNumber);

	      
	        String updateSql = """
	                UPDATE accounts a
	                JOIN users u ON a.user_id = u.user_id
	                SET a.is_approved = TRUE, u.rejection_reason = NULL, a.account_number = ?
	                WHERE a.account_id = ? AND a.is_approved = FALSE
	        """;

	        try (PreparedStatement ps = connection.prepareStatement(updateSql)) {
	            ps.setString(1, accountNumber);
	            ps.setInt(2, accountId);
	            int rows = ps.executeUpdate();

	            // Log the number of affected rows
	            System.out.println("Rows affected: " + rows);

	            ok = rows == 2;
	        }

	        if (ok)
	            connection.commit();
	        else
	            connection.rollback();

	    } catch (SQLException e) {
	        System.out.println("Error during approval: " + e.getMessage());
	        e.printStackTrace();  
	        try {
	            connection.rollback();
	        } catch (SQLException ignored) {
	        }
	        ok = false;
	    } finally {
	        try {
	            connection.setAutoCommit(oldAutoCommit);
	        } catch (SQLException ignored) {
	        }
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

	public double getTotalBankBalance() {
		String sql = "SELECT SUM(balance) AS total_balance FROM accounts WHERE is_approved = TRUE";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getDouble("total_balance");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0.0;
	}

	@Override
	public boolean rejectAccount(int accountId, String rejectionReason) {
		// Reject the account by updating the status to 'rejected' and store the reason
		boolean success = false;
		String sql = """
					           UPDATE accounts a
				JOIN users u ON a.user_id = u.user_id
				SET a.is_approved = FALSE,
				    u.rejection_reason = ?
				WHERE a.account_id = ?;
					        """;

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, rejectionReason);
			ps.setInt(2, accountId);

			int rowsAffected = ps.executeUpdate();
			success = rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}

	public List<PendingAccountDto> getRejectedAccounts() {
		String sql = """
				SELECT a.account_id, a.user_id, u.first_name, u.last_name, u.email, a.created_at, u.aadhar_file_path, u.aadhar_number, a.is_approved, u.rejection_reason
				FROM accounts a
				JOIN users u ON u.user_id = a.user_id
				WHERE a.is_approved = FALSE AND u.rejection_reason IS NOT NULL
				ORDER BY a.created_at DESC
				""";

		List<PendingAccountDto> list = new ArrayList<>();
		try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				list.add(new PendingAccountDto(rs.getInt("account_id"), rs.getInt("user_id"),
						rs.getString("first_name"), rs.getString("last_name"), rs.getString("email"),
						rs.getTimestamp("created_at"), rs.getString("aadhar_file_path"), rs.getString("aadhar_number"),
						rs.getBoolean("is_approved"), rs.getString("rejection_reason")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public String getRejectionReason(int accountId) {
		String sql = "SELECT u.rejection_reason FROM accounts a JOIN users u ON a.user_id = u.user_id WHERE a.account_id = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, accountId);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getString("rejection_reason");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}