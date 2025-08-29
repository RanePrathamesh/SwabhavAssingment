package com.aurionpro.Controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aurionpro.model.PendingAccountDto;
import com.aurionpro.service.AccountService;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AccountService accountService;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminServlet() {
        super();
        // TODO Auto-generated constructor stub
        accountService = new AccountService();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<PendingAccountDto> pendingAccounts = accountService.getAllPendingAccounts();
        request.setAttribute("pendingAccounts", pendingAccounts);

        request.getRequestDispatcher("pending-accounts.jsp").forward(request, response);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accountIdStr = request.getParameter("accountId");
        if (accountIdStr != null) {
            int accountId = Integer.parseInt(accountIdStr);

            boolean success = accountService.approveAccount(accountId);
            if (success) {
                request.setAttribute("msg", "✅ Account approved successfully!");
            } else {
                request.setAttribute("msg", "❌ Failed to approve account.");
            }
        } else {
            request.setAttribute("msg", "⚠️ Invalid request.");
        }

        // Refresh pending accounts list
        doGet(request, response);
    }

}
