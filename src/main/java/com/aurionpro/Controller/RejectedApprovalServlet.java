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
 * Servlet implementation class RejectedApprovalServlet
 */
@WebServlet("/RejectedApprovalServlet")
public class RejectedApprovalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       private AccountService accountService;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RejectedApprovalServlet() {
        super();
        accountService = new AccountService();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<PendingAccountDto> rejectedAccounts = accountService.getRejectedAccounts();
        request.setAttribute("rejectedAccounts", rejectedAccounts);
        request.getRequestDispatcher("RejectedApproval.jsp").forward(request, response);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
