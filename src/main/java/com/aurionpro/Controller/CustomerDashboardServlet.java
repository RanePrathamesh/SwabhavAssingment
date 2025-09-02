package com.aurionpro.Controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.aurionpro.model.Account;
import com.aurionpro.model.User;
import com.aurionpro.service.AccountService;
import com.aurionpro.service.PaymentService;

@WebServlet("/CustomerDashboardServlet")
public class CustomerDashboardServlet extends HttpServlet {

    private AccountService accountService = new AccountService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve session, if not exists, redirect to login page
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("Login.jsp");
            return;
        }

        // Retrieve user object from session
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("Login.jsp");
            return;
        }

        // Get userId from the user object
        int userId = user.getUserId();

        // Initialize account and rejectionReason variables
        Account account = null;
        String rejectionReason = null;

        // Retrieve account details using the userId
        account = accountService.getAccountByUserId(userId);

        // If account exists and is not approved, fetch the rejection reason
        if (account != null && !account.isApproved()) {
            rejectionReason = accountService.getRejectionReason(userId);
        }

        // Set attributes to be used in the JSP
        request.setAttribute("user", user);
        request.setAttribute("account", account);
        request.setAttribute("rejectionReason", rejectionReason);

        // Forward the request to the customer-dashboard.jsp page
        request.getRequestDispatcher("customer-dashboard.jsp").forward(request, response);
    }

    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

