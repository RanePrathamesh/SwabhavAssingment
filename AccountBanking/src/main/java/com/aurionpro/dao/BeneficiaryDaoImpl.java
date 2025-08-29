package com.aurionpro.dao;

import com.aurionpro.dao.IBeneficiaryDao;
import com.aurionpro.model.Beneficiary;
import com.aurionpro.utils.DatabaseSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BeneficiaryDaoImpl implements IBeneficiaryDao {

    private Connection conn ;

    public BeneficiaryDaoImpl() {
        this.conn = DatabaseSource.getinstance().getConnection();
    }

    @Override
    public boolean addBeneficiary(Beneficiary beneficiary) {
        String sql = "INSERT INTO beneficiaries (user_id, beneficiary_name, account_number, bank_name, ifsc_code, nickname) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, beneficiary.getUserId());
            ps.setString(2, beneficiary.getBeneficiaryName());
            ps.setString(3, beneficiary.getAccountNumber());
            ps.setString(4, beneficiary.getBankName());
            ps.setString(5, beneficiary.getIfscCode());
            ps.setString(6, beneficiary.getNickname());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Beneficiary> getBeneficiariesByUserId(int userId) {
        List<Beneficiary> list = new ArrayList<>();
        String sql = "SELECT * FROM beneficiaries WHERE user_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Beneficiary b = new Beneficiary();
                b.setBeneficiaryId(rs.getInt("beneficiary_id"));
                b.setUserId(rs.getInt("user_id"));
                b.setBeneficiaryName(rs.getString("beneficiary_name"));
                b.setAccountNumber(rs.getString("account_number"));
                b.setBankName(rs.getString("bank_name"));
                b.setIfscCode(rs.getString("ifsc_code"));
                b.setNickname(rs.getString("nickname"));
                b.setCreatedAt(rs.getTimestamp("created_at"));

                list.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean deleteBeneficiary(int beneficiaryId, int userId) {
        String sql = "DELETE FROM beneficiaries WHERE beneficiary_id = ? AND user_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, beneficiaryId);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateBeneficiary(Beneficiary beneficiary) {
        String sql = "UPDATE beneficiaries SET beneficiary_name=?, account_number=?, bank_name=?, ifsc_code=?, nickname=? WHERE beneficiary_id=? AND user_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, beneficiary.getBeneficiaryName());
            ps.setString(2, beneficiary.getAccountNumber());
            ps.setString(3, beneficiary.getBankName());
            ps.setString(4, beneficiary.getIfscCode());
            ps.setString(5, beneficiary.getNickname());
            ps.setInt(6, beneficiary.getBeneficiaryId());
            ps.setInt(7, beneficiary.getUserId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Beneficiary getBeneficiaryById(int beneficiaryId, int userId) {
        String sql = "SELECT * FROM beneficiaries WHERE beneficiary_id=? AND user_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, beneficiaryId);
            ps.setInt(2, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Beneficiary b = new Beneficiary();
                b.setBeneficiaryId(rs.getInt("beneficiary_id"));
                b.setUserId(rs.getInt("user_id"));
                b.setBeneficiaryName(rs.getString("beneficiary_name"));
                b.setAccountNumber(rs.getString("account_number"));
                b.setBankName(rs.getString("bank_name"));
                b.setIfscCode(rs.getString("ifsc_code"));
                b.setNickname(rs.getString("nickname"));
                b.setCreatedAt(rs.getTimestamp("created_at"));
                return b;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Beneficiary findByAccountNumberAndUserId(String accountNumber, int userId, String ifscCode) {
        String sql = "SELECT * FROM beneficiaries WHERE account_number = ? AND user_id = ? AND ifsc_code = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, accountNumber);
            ps.setInt(2, userId);
            ps.setString(3, ifscCode);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Beneficiary b = new Beneficiary();
                b.setBeneficiaryId(rs.getInt("beneficiary_id"));
                b.setUserId(rs.getInt("user_id"));
                b.setBeneficiaryName(rs.getString("beneficiary_name"));
                b.setAccountNumber(rs.getString("account_number"));
                b.setBankName(rs.getString("bank_name"));
                b.setIfscCode(rs.getString("ifsc_code"));
                b.setNickname(rs.getString("nickname"));
                b.setCreatedAt(rs.getTimestamp("created_at"));
                return b;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
