package com.aurionpro.Controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aurionpro.model.PendingAccountDto;
import com.aurionpro.model.User;
import com.aurionpro.service.AccountService;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet("/PendingAccountsServlet")
public class PendingAccountsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private AccountService accountService;

    public PendingAccountsServlet() {
        super();
        accountService = new AccountService();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("Login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        if (user == null || !"ADMIN".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect("Login.jsp");
            return;
        }

        // Fetch the list of pending accounts
        List<PendingAccountDto> pendingAccounts = accountService.getAllPendingAccounts();
        request.setAttribute("pendingAccounts", pendingAccounts);

        // Forward the request to the JSP page for rendering
        request.getRequestDispatcher("pending-accounts.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("Login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        if (user == null || !"ADMIN".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect("Login.jsp");
            return;
        }

        String accountIdStr = request.getParameter("accountId");
        String action = request.getParameter("action"); // Get the action param
        String rejectionReason = request.getParameter("rejectionReason"); // Get rejection reason if rejecting

        if (accountIdStr != null && action != null) {
            int accountId = Integer.parseInt(accountIdStr);

            boolean success = false;
            String message = "";

            if ("approve".equalsIgnoreCase(action)) {
                success = accountService.approveAccount(accountId);
                message = success ? "✅ Account approved successfully!" : "❌ Failed to approve account.";
            } else if ("reject".equalsIgnoreCase(action)) {
                if (rejectionReason != null && !rejectionReason.trim().isEmpty()) {
                    success = accountService.rejectAccount(accountId, rejectionReason);
                    message = success ? "✅ Account rejected successfully!" : "❌ Failed to reject account.";
                } else {
                    message = "⚠️ Rejection reason is required!";
                }
            } else {
                message = "⚠️ Invalid action.";
            }

            request.setAttribute("msg", message);
        } else {
            request.setAttribute("msg", "⚠️ Invalid request parameters.");
        }

        // Refresh pending accounts list
        doGet(request, response);  
    }
}
