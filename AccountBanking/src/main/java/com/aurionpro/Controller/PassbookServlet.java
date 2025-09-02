package com.aurionpro.Controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aurionpro.model.Account;
import com.aurionpro.model.Transaction;
import com.aurionpro.service.BeneficiaryService;
import com.aurionpro.service.PaymentService;

/**
 * Servlet implementation class PassbookServlet
 */
@WebServlet("/PassbookServlet")
public class PassbookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private PaymentService paymentService;
	

	
	
	public PassbookServlet() {
		super();
		paymentService = new PaymentService();
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

        HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Account account = (Account) session.getAttribute("account");
        int accountId = account.getAccountId();

        List<Transaction> transactions = paymentService.getTransactionsByAccountId(account.getAccountId());
        request.setAttribute("transactions", transactions);
        request.getRequestDispatcher("passbook.jsp").forward(request, response);

    }
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
