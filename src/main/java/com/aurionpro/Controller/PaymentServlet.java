package com.aurionpro.Controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.aurionpro.model.Account;
import com.aurionpro.model.Beneficiary;
import com.aurionpro.model.User;
import com.aurionpro.service.BeneficiaryService;
import com.aurionpro.service.PaymentService;

@WebServlet("/PaymentServlet")
public class PaymentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private PaymentService paymentService;
    private BeneficiaryService beneficiaryService;

    public PaymentServlet() {
        super();
        paymentService = new PaymentService();
        beneficiaryService = new BeneficiaryService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null || session.getAttribute("account") == null) {
            response.sendRedirect("Login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        int accountId = (int) session.getAttribute("account");

        // Get fresh account from DB
        Account account = paymentService.getAccountById(accountId);
        request.setAttribute("account", account);

        // Get user's beneficiaries
        List<Beneficiary> beneficiaries = beneficiaryService.getBeneficiariesByUserId(user.getUserId());
        request.setAttribute("beneficiaries", beneficiaries);

        // Forward to JSP
        request.getRequestDispatcher("payment.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null || session.getAttribute("account") == null) {
            response.sendRedirect("Login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        int accountId = (int) session.getAttribute("account");

        // Get fresh account from DB
        Account senderAccount = paymentService.getAccountById(accountId);
        request.setAttribute("account", senderAccount);  // Set for JSP

        String beneficiaryIdStr = request.getParameter("beneficiaryId");
        String amountStr = request.getParameter("amount");

        double amount = 0;

        try {
            amount = Double.parseDouble(amountStr);
            int beneficiaryId = Integer.parseInt(beneficiaryIdStr);

            // Reload beneficiaries
            List<Beneficiary> beneficiaries = beneficiaryService.getBeneficiariesByUserId(user.getUserId());
            request.setAttribute("beneficiaries", beneficiaries);

            // Find selected beneficiary
            Beneficiary selectedBeneficiary = beneficiaries.stream()
                    .filter(b -> b.getBeneficiaryId() == beneficiaryId)
                    .findFirst()
                    .orElse(null);

            if (selectedBeneficiary == null) {
                request.setAttribute("message", "Beneficiary not found.");
                doGet(request, response);
                return;
            }

            if (amount <= 0) {
                request.setAttribute("message", "Amount must be greater than zero.");
                doGet(request, response);
                return;
            }

            if (amount > senderAccount.getBalance()) {
                request.setAttribute("message", "Insufficient balance.");
                doGet(request, response);
                return;
            }

            String description = "Transfer to " + selectedBeneficiary.getBeneficiaryName();

            // Try to get receiver account by beneficiary's account number
            Account receiverAccount = paymentService.getAccountByAccountNumber(selectedBeneficiary.getAccountNumber());

            if (receiverAccount == null) {
                // External transfer — only debit sender
                boolean success = paymentService.transferToExternal(
                        senderAccount.getAccountId(),
                        amount,
                        description + " (external)"
                );

                if (success) {
                    request.setAttribute("message", "Payment successful! ₹" + amount + " debited to external beneficiary.");
                } else {
                    request.setAttribute("message", "Payment failed. Please try again.");
                }

                Account updatedAccount = paymentService.getAccountById(accountId);
                request.setAttribute("account", updatedAccount);
                request.getRequestDispatcher("payment.jsp").forward(request, response);
                return;
            }

            // Internal transfer (sender to receiver)
            boolean success = paymentService.transfer(
                    senderAccount.getAccountId(),
                    receiverAccount.getAccountId(),
                    amount,
                    description
            );

            if (success) {
                request.setAttribute("message", "Payment successful! ₹" + amount + " transferred.");
            } else {
                request.setAttribute("message", "Payment failed. Please check details and try again.");
            }

            // Reload fresh account to update balance in UI
            Account updatedAccount = paymentService.getAccountById(accountId);
            request.setAttribute("account", updatedAccount);

            // Forward to JSP
            request.getRequestDispatcher("payment.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            request.setAttribute("message", "Invalid input provided.");
            doGet(request, response);
        }
    }
}
