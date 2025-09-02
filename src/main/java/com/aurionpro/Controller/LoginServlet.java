package com.aurionpro.Controller;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aurionpro.model.Account;
import com.aurionpro.model.User;
import com.aurionpro.service.AccountService;
import com.aurionpro.service.UserService;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserService service;

    public LoginServlet() {
        super();
        service = new UserService();
    }

    // Show login page if accessed directly via GET
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("Login.jsp").forward(request, response);
    }

    // Handle login form submission
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = service.login(username, password);  // Authenticate

        if (user != null) {
        	
        	
        	 if (!user.isActive()) {
        	        request.setAttribute("errorMsg", "Your account has been deactivated. Reason: " +
        	                (user.getDeactivationReason() != null ? user.getDeactivationReason() : "No reason provided."));
        	        request.getRequestDispatcher("Login.jsp").forward(request, response);
        	        return;
        	    }
            if (user.isActive()) {
                // ✅ User is active, proceed with login
                HttpSession session = request.getSession();
                session.setAttribute("user", user);

                AccountService accountService = new AccountService();

                if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                    response.sendRedirect("admin-dashboard.jsp");
                } else {
                    Account account = accountService.getAccountByUserId(user.getUserId());
                    int accountId = account.getAccountId();
                    session.setAttribute("account", accountId);

                    response.sendRedirect("CustomerDashboardServlet");
                }
            } else {
                // ❌ Account is deactivated, fetch deactivation reason (if stored)
                String reason = service.getDeactivationReason(user.getUserId()); // Implement this method
                String message = "Your account is deactivated.";
                if (reason != null && !reason.trim().isEmpty()) {
                    message += " Reason: " + reason;
                } else {
                    message += " Please contact the bank.";
                }

                request.setAttribute("errorMsg", message);
                request.getRequestDispatcher("Login.jsp").forward(request, response);
            }
        } else {
            
            request.setAttribute("errorMsg", "Invalid username or password.");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
        }
    }


}



