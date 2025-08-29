package com.aurionpro.Controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.aurionpro.model.Account;
import com.aurionpro.model.Beneficiary;
import com.aurionpro.model.User;
import com.aurionpro.service.BeneficiaryService;
import com.aurionpro.service.PaymentService;

@WebServlet("/PaymentServlet") // fixed the typo from PaymentServelt
public class PaymentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private PaymentService paymentService;
    private BeneficiaryService beneficiaryService;

    public PaymentServlet() {
        super();
        paymentService = new PaymentService();
        beneficiaryService = new BeneficiaryService(); // Assuming you have this
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Check if session is valid
        if (session == null || session.getAttribute("user") == null || session.getAttribute("account") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");

        // Fetch fresh beneficiaries from DB for this request
        List<Beneficiary> beneficiaries = beneficiaryService.getBeneficiariesByUserId(user.getUserId());
        request.setAttribute("beneficiaries", beneficiaries);

        // Forward to payment page
        request.getRequestDispatcher("payment.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Check if session is valid
        if (session == null || session.getAttribute("user") == null || session.getAttribute("account") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        Account account = (Account) session.getAttribute("account");

        // Get form data
        String beneficiaryIdStr = request.getParameter("beneficiaryId");
        String amountStr = request.getParameter("amount");

        double amount = 0;
        int beneficiaryId = 0;

        try {
            amount = Double.parseDouble(amountStr);
            beneficiaryId = Integer.parseInt(beneficiaryIdStr);
        } catch (NumberFormatException e) {
            request.setAttribute("message", "Invalid input provided.");
            doGet(request, response); // Refresh page with fresh data
            return;
        }

        // Fetch fresh beneficiaries for dropdown (even on post)
        List<Beneficiary> beneficiaries = beneficiaryService.getBeneficiariesByUserId(user.getUserId());
        request.setAttribute("beneficiaries", beneficiaries);

        // Find selected beneficiary object and account details
        Beneficiary selectedBeneficiary = null;
        for (Beneficiary b : beneficiaries) {
            if (b.getBeneficiaryId() == beneficiaryId) {
                selectedBeneficiary = b;
                break;
            }
        }

        if (selectedBeneficiary == null) {
            request.setAttribute("message", "Beneficiary not found.");
            doGet(request, response);
            return;
        }

        // Perform payment
        boolean success = paymentService.transfer(account.getAccountId(), selectedBeneficiary.getBeneficiaryId(),
                selectedBeneficiary.getAccountNumber(), amount);

        if (success) {
            account.setBalance(account.getBalance() - amount);
            session.setAttribute("account", account); // Update session balance
            request.setAttribute("message", "Payment successful! ₹" + amount + " transferred.");
        } else {
            request.setAttribute("message", "Payment failed. Please check balance or beneficiary details.");
        }

        // Show form again with message
        request.getRequestDispatcher("payment.jsp").forward(request, response);
    }
}
