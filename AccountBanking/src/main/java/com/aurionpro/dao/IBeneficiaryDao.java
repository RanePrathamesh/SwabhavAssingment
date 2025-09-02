package com.aurionpro.dao;

import com.aurionpro.model.Beneficiary;
import java.util.List;

public interface IBeneficiaryDao {
    
    // Add new beneficiary
    boolean addBeneficiary(Beneficiary beneficiary);
    
    // Get all beneficiaries of a user
    List<Beneficiary> getBeneficiariesByUserId(int userId);
    
    // Delete a beneficiary
    boolean deleteBeneficiary(int beneficiaryId, int userId);
    
    // Update nickname or details of beneficiary
    boolean updateBeneficiary(Beneficiary beneficiary);
    
    // Fetch a specific beneficiary (optional, useful for validation)
    Beneficiary getBeneficiaryById(int beneficiaryId, int userId);
}
