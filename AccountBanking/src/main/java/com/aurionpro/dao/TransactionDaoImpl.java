package com.aurionpro.dao;

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
    public boolean transferMoney(int fromAccountId, int toAccountId, String toAccountNumber, double amount) {
        // Updated Queries to match the new schema
        String checkBalanceQuery = "SELECT balance FROM accounts WHERE account_id = ?";
        String debitQuery = "UPDATE accounts SET balance = balance - ? WHERE account_id = ? AND balance >= ?";
        String creditQuery = "UPDATE accounts SET balance = balance + ? WHERE account_id = ?";
        String insertTxnQuery = "INSERT INTO transactions (from_account_id, to_account_id, to_account_number, amount, txn_type, status) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            conn.setAutoCommit(false);

            double balance = 0.0;

            // ✅ Step 1: Check balance
            try (PreparedStatement ps = conn.prepareStatement(checkBalanceQuery)) {
                ps.setInt(1, fromAccountId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        balance = rs.getDouble("balance");
                    }
                }
            }

            // If balance is less than the transfer amount, return false
            if (balance < amount) {
                return false;
            }

            // ✅ Step 2: Debit sender's account
            try (PreparedStatement ps = conn.prepareStatement(debitQuery)) {
                ps.setDouble(1, amount);
                ps.setInt(2, fromAccountId);
                ps.setDouble(3, amount);
                int rows = ps.executeUpdate();
                if (rows == 0) {
                    conn.rollback();
                    return false;
                }
            }

            // ✅ Step 3: Credit receiver's account (to_account_id)
            try (PreparedStatement ps = conn.prepareStatement(creditQuery)) {
                ps.setDouble(1, amount);
                ps.setInt(2, toAccountId);  // Use to_account_id for the receiver
                int rows = ps.executeUpdate();
                if (rows == 0) {
                    conn.rollback();
                    return false;
                }
            }

            // ✅ Step 4: Insert transactions for both sender and receiver

            // Debit entry for sender
            try (PreparedStatement ps = conn.prepareStatement(insertTxnQuery)) {
                ps.setInt(1, fromAccountId);
                ps.setInt(2, toAccountId);  // to_account_id is stored for sender's txn
                ps.setString(3, toAccountNumber);  // Receiver's account number
                ps.setDouble(4, amount);
                ps.setString(5, "DEBIT");
                ps.setString(6, "SUCCESS");
                ps.executeUpdate();
            }

            // Credit entry for receiver
            try (PreparedStatement ps = conn.prepareStatement(insertTxnQuery)) {
                ps.setInt(1, fromAccountId);  // Sender's account id for receiver's txn
                ps.setInt(2, toAccountId);    // Same to_account_id for receiver's transaction
                ps.setString(3, toAccountNumber); // Same receiver's account number
                ps.setDouble(4, amount);
                ps.setString(5, "CREDIT");
                ps.setString(6, "SUCCESS");
                ps.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback(); // rollback in case of error
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        }
    }

    @Override
    public List<Transaction> getTransactionsByAccount(int accountId) {
        List<Transaction> list = new ArrayList<>();
        String query = "SELECT * FROM transactions WHERE from_account_id = ? OR to_account_id = ? ORDER BY txn_date DESC";
        
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, accountId);
            ps.setInt(2, accountId);  // Get both sender and receiver transactions
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Transaction t = new Transaction();
                t.setTxnId(rs.getInt("txn_id"));
                t.setFromAccountId(rs.getInt("from_account_id"));
                t.setToAccountId(rs.getInt("to_account_id"));  // Now we have to also get to_account_id
                t.setToAccountNumber(rs.getString("to_account_number"));
                t.setAmount(rs.getDouble("amount"));
                t.setTxnType(rs.getString("txn_type"));
                t.setTxnDate(rs.getTimestamp("txn_date"));
                t.setStatus(rs.getString("status"));
                list.add(t);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
