package com.aurionpro.service;

import com.aurionpro.dao.BeneficiaryDaoImpl;
import com.aurionpro.model.Beneficiary;
import com.aurionpro.utils.FormatChecker;

import java.util.List;

public class BeneficiaryService {

    private BeneficiaryDaoImpl beneficiaryDao;

    public BeneficiaryService() {
        this.beneficiaryDao = new BeneficiaryDaoImpl();
    }

    // Add a new beneficiary after validation and duplication check
    public boolean addBeneficiary(Beneficiary beneficiary) {
        if (!validateBeneficiary(beneficiary)) {
            System.out.println("Validation failed for beneficiary.");
            return false;
        }

        // Check if the beneficiary already exists
        Beneficiary existing = beneficiaryDao.findByAccountNumberAndUserId(
            beneficiary.getAccountNumber(), 
            beneficiary.getUserId(), 
            beneficiary.getIfscCode()
        );
        
        if (existing != null) {
            System.out.println("Beneficiary already exists for this account and IFSC code.");
            return false;
        }

        return beneficiaryDao.addBeneficiary(beneficiary);
    }


    public boolean updateBeneficiary(Beneficiary beneficiary) {
        if (!validateBeneficiary(beneficiary)) {
            System.out.println("Validation failed for beneficiary update.");
            return false;
        }

        return beneficiaryDao.updateBeneficiary(beneficiary);
    }

    
    public boolean deleteBeneficiary(int beneficiaryId, int userId) {
        return beneficiaryDao.deleteBeneficiary(beneficiaryId, userId);
    }

  
    public List<Beneficiary> getBeneficiariesByUserId(int userId) {
        return beneficiaryDao.getBeneficiariesByUserId(userId);
    }

    
    public Beneficiary getBeneficiaryById(int beneficiaryId, int userId) {
        return beneficiaryDao.getBeneficiaryById(beneficiaryId, userId);
    }

   
    private boolean validateBeneficiary(Beneficiary beneficiary) {
        if (beneficiary == null) {
            System.out.println("Beneficiary object is null.");
            return false;
        }

        boolean valid = true;

        if (!FormatChecker.isValidString(beneficiary.getBeneficiaryName())) {
            System.out.println("Invalid beneficiary name: " + beneficiary.getBeneficiaryName());
            valid = false;
        }

        if (!FormatChecker.isInternationalAccountNumberValid(beneficiary.getAccountNumber())) {
            System.out.println("Invalid account number: " + beneficiary.getAccountNumber());
            valid = false;
        }

        if (!FormatChecker.isIFSCCodeValid(beneficiary.getIfscCode())) {
            System.out.println("Invalid IFSC code: " + beneficiary.getIfscCode());
            valid = false;
        }

        if (!FormatChecker.isValidString(beneficiary.getBankName())) {
            System.out.println("Invalid bank name: " + beneficiary.getBankName());
            valid = false;
        }

        if (beneficiary.getNickname() != null && !beneficiary.getNickname().isEmpty()
                && !FormatChecker.isValidString(beneficiary.getNickname())) {
            System.out.println("Invalid nickname: " + beneficiary.getNickname());
            valid = false;
        }

        return valid;
    }
}
