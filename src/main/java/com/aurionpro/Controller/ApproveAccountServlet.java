package com.aurionpro.Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aurionpro.service.AccountService;

/**
 * Servlet implementation class ApproveAccountServlet
 */
@WebServlet("/ApproveAccountServlet")
public class ApproveAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       private AccountService accountService;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApproveAccountServlet() {
        super();
        accountService = new AccountService();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	 @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        int accountId = Integer.parseInt(request.getParameter("accountId"));
	        boolean isApproved = accountService.approveAccount(accountId);

	        if (isApproved) {
	            response.sendRedirect("RejectedApprovalServlet");
	        } else {
	            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to approve account.");
	        }
	    }

}
