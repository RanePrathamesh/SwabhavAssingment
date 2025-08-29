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

        User user = service.login(username, password);

        if (user != null && user.isActive()) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            AccountService accountService = new AccountService(); // or inject it
            Account account = accountService.getAccountByUserId(user.getUserId());
            session.setAttribute("account", account);  // 
            

            if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                response.sendRedirect("AdminServlet");   // goes to AdminServlet
            } else {
                response.sendRedirect("customer-dashboard.jsp");
            }
        } else {
            request.setAttribute("errorMsg", "Invalid username or password.");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
        }
    }
}



